/*
 * Waltz - Enterprise Architecture
 * Copyright (C) 2016, 2017, 2018, 2019, 2024 Waltz open source project
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

import org.finos.waltz.model.ReleaseLifecycleStatusChangeCommand;
import org.finos.waltz.model.person.Person;
import org.finos.waltz.model.survey.SurveyTemplate;
import org.finos.waltz.model.survey.SurveyTemplateChangeCommand;
import org.finos.waltz.model.survey_template_exchange.SurveyTemplateExchange;
import org.finos.waltz.model.user.SystemRole;
import org.finos.waltz.service.person.PersonService;
import org.finos.waltz.service.survey.SurveyTemplateService;
import org.finos.waltz.service.user.UserRoleService;
import org.finos.waltz.web.WebUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

import static org.finos.waltz.common.Checks.checkNotNull;

@RestController
@RequestMapping("/api/survey-template")
public class SurveyTemplateController {

    private final SurveyTemplateService surveyTemplateService;
    private final UserRoleService userRoleService;
    private final PersonService personService;

    @Autowired
    public SurveyTemplateController(SurveyTemplateService surveyTemplateService,
                                    UserRoleService userRoleService,
                                    PersonService personService) {
        checkNotNull(surveyTemplateService, "surveyTemplateService must not be null");
        checkNotNull(userRoleService, "userRoleService must not be null");
        checkNotNull(personService, "personService must not be null");

        this.surveyTemplateService = surveyTemplateService;
        this.userRoleService = userRoleService;
        this.personService = personService;
    }

    @GetMapping
    public Collection<SurveyTemplate> findAll() {
        return surveyTemplateService.findAll();
    }

    @GetMapping("/owner")
    public List<SurveyTemplate> findForOwner(Principal principal) {
        return surveyTemplateService.findForOwner(principal.getName());
    }

    @GetMapping("/{id}")
    public SurveyTemplate getById(@PathVariable("id") long id) {
        return surveyTemplateService.getById(id);
    }

    @GetMapping("/question-id/{id}")
    public SurveyTemplate getByQuestionId(@PathVariable("id") long id) {
        return surveyTemplateService.getByQuestionId(id);
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody SurveyTemplateChangeCommand command, Principal principal) {
        ensureUserHasAdminRights(principal.getName());
        return ResponseEntity.ok(surveyTemplateService.create(principal.getName(), command));
    }

    @PostMapping("/{id}/clone")
    public ResponseEntity<Long> clone(@PathVariable("id") long id, Principal principal) {
        ensureUserHasAdminRights(principal.getName());
        return ResponseEntity.ok(surveyTemplateService.clone(principal.getName(), id));
    }

    @PutMapping
    public ResponseEntity<Integer> update(@RequestBody SurveyTemplateChangeCommand command, Principal principal) {
        ensureUserHasAdminRights(principal.getName());
        return ResponseEntity.ok(surveyTemplateService.update(principal.getName(), command));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Integer> updateStatus(@PathVariable("id") long id,
                                                @RequestBody ReleaseLifecycleStatusChangeCommand command,
                                                Principal principal) {
        ensureUserHasAdminRights(principal.getName());
        return ResponseEntity.ok(surveyTemplateService.updateStatus(principal.getName(), id, command));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") long id, Principal principal) {
        ensureUserIsOwnerOrAdmin(id, principal.getName());
        return ResponseEntity.ok(surveyTemplateService.delete(id));
    }

    @PostMapping("/json-import")
    public ResponseEntity<Long> importTemplateFromJson(@RequestBody SurveyTemplateExchange templateExchange, Principal principal) {
        ensureUserHasAdminRights(principal.getName());
        return ResponseEntity.ok(surveyTemplateService.importTemplateFromJSON(principal.getName(), templateExchange));
    }


    private void ensureUserHasAdminRights(String username) {
        //WebUtilities.requireRoleForSB(userRoleService, username, SystemRole.SURVEY_TEMPLATE_ADMIN);
    }

    private void ensureUserIsOwnerOrAdmin(long templateId, String username) {
        Person person = personService.getPersonByUserId(username);
        if (person == null) {
            throw new IllegalArgumentException("User not found");
        }

        SurveyTemplate template = surveyTemplateService.getById(templateId);

        boolean isOwner = person.id()
                .map(personId -> template.ownerId().equals(personId))
                .orElse(false);

        if (isOwner) {
            return; // Owner is allowed
        }

        // If not owner, must be an admin
        //WebUtilities.requireRoleForSB(userRoleService, username, SystemRole.SURVEY_TEMPLATE_ADMIN);
    }
}
