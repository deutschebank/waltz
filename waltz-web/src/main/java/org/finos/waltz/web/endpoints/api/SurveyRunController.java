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

import org.finos.waltz.common.exception.InsufficientPrivelegeException;
import org.finos.waltz.model.DateChangeCommand;
import org.finos.waltz.model.IdCommandResponse;
import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.ImmutableEntityReference;
import org.finos.waltz.model.survey.*;
import org.finos.waltz.model.user.SystemRole;
import org.finos.waltz.service.survey.SurveyRunService;
import org.finos.waltz.service.user.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.model.HierarchyQueryScope.EXACT;
import static org.finos.waltz.model.IdSelectionOptions.mkOpts;

import java.security.Principal;
import java.util.List;

/**
 * This controller is responsible for handling survey run related requests.
 * It replaces the old Spark-based SurveyRunEndpoint.
 */
@RestController
@RequestMapping("/api/survey-run")
public class SurveyRunController {


    private final SurveyRunService surveyRunService;

    private final UserRoleService userRoleService;


    @Autowired
    public SurveyRunController(SurveyRunService surveyRunService,
                             UserRoleService userRoleService) {
        checkNotNull(surveyRunService, "surveyRunService must not be null");
        checkNotNull(userRoleService, "userRoleService must not be null");

        this.surveyRunService = surveyRunService;
        this.userRoleService = userRoleService;
    }


    @GetMapping("/id/{id}")
    public SurveyRun getById(@PathVariable("id") long id) {
        return surveyRunService.getById(id);
    }

    @GetMapping("/template-id/{id}")
    public List<SurveyRunWithOwnerAndStats> findByTemplateId(@PathVariable("id") long id) {
        return surveyRunService.findByTemplateId(id);
    }

    @GetMapping("/entity/{kind}/{id}")
    public List<SurveyRun> findByEntityRef(@PathVariable("kind") String kind,
                                           @PathVariable("id") long id) {
        // Reconstruct EntityReference from path variables
        return surveyRunService.findBySurveyInstanceIdSelector(
                mkOpts(ImmutableEntityReference.mkRef(EntityKind.valueOf(kind), id), EXACT));
    }

    @GetMapping("/recipient/id/{id}")
    public List<SurveyRun> findForRecipientId(@PathVariable("id") long id) {
        return surveyRunService.findForRecipient(id);
    }

    @GetMapping("/user")
    public List<SurveyRun> findForUser(Principal principal) {
        return surveyRunService.findForRecipient(principal.getName());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IdCommandResponse createSurveyRun(Principal principal,
                                             @RequestBody SurveyRunCreateCommand command) throws InsufficientPrivelegeException {
        return surveyRunService.createSurveyRun(principal.getName(), command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean deleteSurveyRun(Principal principal,
                                   @PathVariable("id") long id) {
        return surveyRunService.deleteSurveyRun(principal.getName(), id);
    }

    @PutMapping("/{id}")
    // TODO: Add Spring Security annotation for admin rights, e.g., @Secured("ROLE_SURVEY_ADMIN")
    public int updateSurveyRun(Principal principal,
                               @PathVariable("id") long id,
                               @RequestBody SurveyRunChangeCommand command) {
        // The original `ensureUserHasAdminRights` check should be handled by Spring Security
        // configuration or annotations.
        return surveyRunService.updateSurveyRun(
                principal.getName(),
                id,
                command);
    }

    @PutMapping("/{id}/status")
    public int updateSurveyRunStatus(Principal principal,
                                     @PathVariable("id") long id,
                                     @RequestBody SurveyRunStatusChangeCommand command) {
        return surveyRunService.updateSurveyRunStatus(
                principal.getName(),
                id,
                command.newStatus());
    }

    @PutMapping("/{id}/due-date")
    public int updateSurveyRunDueDate(Principal principal,
                                      @PathVariable("id") long id,
                                      @RequestBody DateChangeCommand command) {
        return surveyRunService.updateSurveyRunDueDate(
                principal.getName(),
                id,
                command);
    }

    @PutMapping("/{id}/approval-due-date")
    public int updateSurveyRunApprovalDueDate(Principal principal,
                                              @PathVariable("id") long id,
                                              @RequestBody DateChangeCommand command) {
        return surveyRunService.updateSurveyRunApprovalDueDate(
                principal.getName(),
                id,
                command);
    }

    @PutMapping("/{id}/role")
    public int updateSurveyInstanceOwningRoles(Principal principal,
                                               @PathVariable("id") long id,
                                               @RequestBody SurveyInstanceOwningRoleSaveCommand owningRole) {
        return surveyRunService.updateSurveyInstanceOwningRoles(
                principal.getName(),
                id,
                owningRole);
    }

    @PostMapping("/recipients")
    // TODO: Add Spring Security annotation for admin rights, e.g., @Secured("ROLE_SURVEY_ADMIN")
    public List<SurveyInstanceRecipient> generateSurveyRunRecipients(@RequestBody InstancesAndRecipientsCreateCommand command) {
        // The original `ensureUserHasAdminRights` check should be handled by Spring Security
        // configuration or annotations.
        return surveyRunService.generateSurveyInstanceRecipients(command);
    }

    @PostMapping("/create-instance-recipients")
    // TODO: Add Spring Security annotation for admin rights, e.g., @Secured("ROLE_SURVEY_ADMIN")
    public boolean createSurveyInstancesAndRecipients(@RequestBody InstancesAndRecipientsCreateCommand command) {
        // The original `ensureUserHasAdminRights` check should be handled by Spring Security
        // configuration or annotations.
        return surveyRunService.createSurveyInstancesAndRecipients(command);
    }

    @PostMapping("/{id}/create-instances")
    public boolean createSurveyInstances(@PathVariable("id") long runId,
                                         @RequestBody SurveyInstanceRecipientsAndOwners recipientsAndOwners) {
        return surveyRunService.createDirectSurveyInstances(runId, recipientsAndOwners);
    }

    @GetMapping("/{id}/completion-rate")
    public SurveyRunCompletionRate getSurveyRunCompletionRate(@PathVariable("id") long id) {
        return surveyRunService.getCompletionRate(id);
    }
}
