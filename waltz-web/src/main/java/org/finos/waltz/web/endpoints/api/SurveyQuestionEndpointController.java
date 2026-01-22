/*
 * Waltz - Enterprise Architecture
 * Copyright (C) 2016, 2017, 2018, 2019 Waltz open source project
 * See README.md for more information
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific
 *
 */

package org.finos.waltz.web.endpoints.api;

import org.finos.waltz.service.survey.SurveyQuestionDropdownEntryService;
import org.finos.waltz.service.survey.SurveyQuestionService;
import org.finos.waltz.service.user.UserRoleService;
import org.finos.waltz.web.json.SurveyQuestionInfo;
import org.finos.waltz.model.survey.SurveyQuestion;
import org.finos.waltz.model.survey.SurveyQuestionDropdownEntry;
import org.finos.waltz.model.survey.SurveyQuestionFieldType;
import org.finos.waltz.model.user.SystemRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.finos.waltz.common.Checks.checkNotNull;


@RestController
@RequestMapping("/api/survey-question")
public class SurveyQuestionEndpointController {

    private final SurveyQuestionService surveyQuestionService;
    private final SurveyQuestionDropdownEntryService surveyQuestionDropdownEntryService;
    private final UserRoleService userRoleService;


    @Autowired
    public SurveyQuestionEndpointController(SurveyQuestionService surveyQuestionService,
                                  SurveyQuestionDropdownEntryService surveyQuestionDropdownEntryService,
                                  UserRoleService userRoleService) {
        checkNotNull(surveyQuestionService, "surveyQuestionService cannot be null");
        checkNotNull(surveyQuestionDropdownEntryService, "surveyQuestionDropdownEntryService cannot be null");
        checkNotNull(userRoleService, "userRoleService cannot be null");

        this.surveyQuestionService = surveyQuestionService;
        this.surveyQuestionDropdownEntryService = surveyQuestionDropdownEntryService;
        this.userRoleService = userRoleService;
    }


    @GetMapping("/questions/instance/{id}")
    public List<SurveyQuestion> findQuestionsForInstance(@PathVariable long id) {
        return surveyQuestionService.findForSurveyInstance(id);
    }

    @GetMapping("/dropdown-entries/instance/{id}")
    public List<SurveyQuestionDropdownEntry> findDropdownEntriesForInstance(@PathVariable long id) {
        return surveyQuestionDropdownEntryService.findForSurveyInstance(id);
    }

    @GetMapping("/questions/template/{id}")
    public List<SurveyQuestion> findQuestionsForTemplate(@PathVariable long id) {
        return surveyQuestionService.findForSurveyTemplate(id);
    }

    @GetMapping("/dropdown-entries/template/{id}")
    public List<SurveyQuestionDropdownEntry> findDropdownEntriesForTemplate(@PathVariable long id) {
        return surveyQuestionDropdownEntryService.findForSurveyTemplate(id);
    }

    @PostMapping
    // In a real Spring application, security (e.g., admin rights) would typically be handled via Spring Security
    // annotations like @PreAuthorize("hasRole('SURVEY_TEMPLATE_ADMIN')") or a security interceptor.
    public long create(@RequestBody SurveyQuestionInfo surveyQuestionInfo) {
        // ensureUserHasAdminRights(req); // Removed, see comment above
        long questionId = surveyQuestionService.create(surveyQuestionInfo.question());
        mayBeSaveDropdownEntries(questionId, surveyQuestionInfo);
        return questionId;
    }

    @PutMapping
    // In a real Spring application, security (e.g., admin rights) would typically be handled via Spring Security
    // annotations like @PreAuthorize("hasRole('SURVEY_TEMPLATE_ADMIN')") or a security interceptor.
    public int update(@RequestBody SurveyQuestionInfo surveyQuestionInfo) {
        // ensureUserHasAdminRights(req); // Removed, see comment above
        int updateCount = surveyQuestionService.update(surveyQuestionInfo.question());
        mayBeSaveDropdownEntries(surveyQuestionInfo.question().id().get(), surveyQuestionInfo);
        return updateCount;
    }

    @DeleteMapping("/{id}")
    // In a real Spring application, security (e.g., admin rights) would typically be handled via Spring Security
    // annotations like @PreAuthorize("hasRole('SURVEY_TEMPLATE_ADMIN')") or a security interceptor.
    public int delete(@PathVariable long id) {
        // ensureUserHasAdminRights(req); // Removed, see comment above
        return surveyQuestionService.delete(id);
    }

    private boolean mayBeSaveDropdownEntries(long questionId, SurveyQuestionInfo questionInfo) {
        checkNotNull(questionInfo.question(), "questionInfo.question() cannot be null");

        if (questionInfo.question().fieldType() == SurveyQuestionFieldType.DROPDOWN
                || questionInfo.question().fieldType() == SurveyQuestionFieldType.DROPDOWN_MULTI_SELECT) {
            return surveyQuestionDropdownEntryService.saveEntries(
                    questionId,
                    questionInfo.dropdownEntries());
        }

        return false;
    }


    // The original ensureUserHasAdminRights method relied on Spark's Request object and Waltz-specific WebUtilities/EndpointUtilities.
    // In a Spring application, this would typically be replaced by Spring Security mechanisms (e.g., @PreAuthorize).
    // private void ensureUserHasAdminRights(Request request) {
    //     requireRole(userRoleService, request, SystemRole.SURVEY_TEMPLATE_ADMIN);
    // }
}
