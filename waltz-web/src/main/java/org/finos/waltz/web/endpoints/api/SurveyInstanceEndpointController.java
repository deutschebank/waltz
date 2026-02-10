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


import jakarta.servlet.http.HttpServletRequest;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.attestation.SyncRecipientsResponse;
import org.finos.waltz.model.person.Person;
import org.finos.waltz.model.survey.*;
import org.finos.waltz.model.user.SystemRole;
import org.finos.waltz.service.survey.SurveyInstanceService;
import org.finos.waltz.service.user.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.model.HierarchyQueryScope.EXACT;
import static org.finos.waltz.model.IdSelectionOptions.mkOpts;
import static org.finos.waltz.web.WebUtilities.*;

@RestController
@RequestMapping(SurveyInstanceEndpointController.BASE_URL)
public class SurveyInstanceEndpointController {

    private static final Logger LOG = LoggerFactory.getLogger(SurveyInstanceEndpoint.class);
    public static final String BASE_URL = "/api/survey-instance/";

    private final SurveyInstanceService surveyInstanceService;
    private final UserRoleService userRoleService;


    @Autowired
    public SurveyInstanceEndpointController(SurveyInstanceService surveyInstanceService,
                                            UserRoleService userRoleService) {
        checkNotNull(surveyInstanceService, "surveyInstanceService cannot be null");
        checkNotNull(userRoleService, "userRoleService cannot be null");
        this.surveyInstanceService = surveyInstanceService;
        this.userRoleService = userRoleService;
    }

    @GetMapping("id/{id}")
    public SurveyInstance getById(@PathVariable("id") long id) {
        return surveyInstanceService.getById(id);
    }

    @GetMapping("{id}/permissions")
    public SurveyInstancePermissions getPermissions(@PathVariable("id") long instanceId, Principal principal) {
        return surveyInstanceService.getPermissions(principal.getName(), instanceId);
    }

    @PostMapping("reassign-recipients")
    public SyncRecipientsResponse reassignRecipients(HttpServletRequest principal) {
        requireRoleForSB(userRoleService, principal, SystemRole.ADMIN);
        LOG.info("User: {}, requested reassign recipients for surveys", getUsernameForSB(principal));
        return surveyInstanceService.reassignRecipients();
    }

    @GetMapping("reassign-recipients-counts")
    public SyncRecipientsResponse getReassignRecipientsCounts() {
        return surveyInstanceService.getReassignRecipientsCounts();
    }

    @PostMapping("reassign-owners")
    public SyncRecipientsResponse reassignOwners(HttpServletRequest principal) {
        requireRoleForSB(userRoleService, principal, SystemRole.ADMIN);
        LOG.info("User: {}, requested reassign owners for surveys", getUsernameForSB(principal));
        return surveyInstanceService.reassignOwners();
    }

    @GetMapping("reassign-owners-counts")
    public SyncRecipientsResponse getReassignOwnersCounts() {
        return surveyInstanceService.getReassignOwnersCounts();
    }

    @GetMapping("entity/{kind}/{id}")
    public List<SurveyInstance> findByEntityRef(@PathVariable("kind") String kind, @PathVariable("id") long id) {
        return surveyInstanceService.findBySurveyInstanceIdSelector(mkOpts(mkRef(kind, id), EXACT));
    }

    @GetMapping("recipient/id/{id}")
    public Set<SurveyInstance> findForRecipientId(@PathVariable("id") long id) {
        return surveyInstanceService.findForRecipient(id);
    }

    @GetMapping("run/{id}")
    public Set<SurveyInstance> findForSurveyRun(@PathVariable("id") long id) {
        return surveyInstanceService.findForSurveyRun(id);
    }

    @GetMapping("id/{id}/previous-versions")
    public List<SurveyInstance> findPreviousVersions(@PathVariable("id") long id) {
        return surveyInstanceService.findPreviousVersionsForInstance(id);
    }

    @GetMapping("id/{id}/versions")
    public List<SurveyInstance> findVersions(@PathVariable("id") long id) {
        return surveyInstanceService.findVersionsForInstance(id);
    }

    @GetMapping("{id}/recipients")
    public List<Person> findRecipients(@PathVariable("id") long id) {
        return surveyInstanceService.findRecipients(id);
    }

    @GetMapping("{id}/group-approvers")
    public Set<Person> findGroupApprovers(@PathVariable("id") long id) {
        return surveyInstanceService.findGroupApprovers(id);
    }

    @GetMapping("{id}/owners")
    public List<Person> findOwners(@PathVariable("id") long id) {
        return surveyInstanceService.findOwners(id);
    }

    @GetMapping("{id}/responses")
    public List<SurveyInstanceQuestionResponse> findResponses(@PathVariable("id") long id) {
        return surveyInstanceService.findResponses(id);
    }

    @GetMapping("{id}/actions")
    public List<SurveyInstanceAction> findPossibleActions(@PathVariable("id") long id, Principal principal) {
        return surveyInstanceService.findPossibleActionsForInstance(principal.getName(), id);
    }

    @PutMapping("{id}/response")
    public boolean saveResponse(@PathVariable("id") long instanceId,
                                @RequestBody SurveyQuestionResponse questionResponse,
                                Principal principal) {
        String userName = principal.getName();
        boolean result = surveyInstanceService.saveResponse(userName, instanceId, questionResponse);

        surveyInstanceService.updateStatus(
                Optional.empty(),
                userName,
                instanceId,
                ImmutableSurveyInstanceStatusChangeCommand.builder()
                        .action(SurveyInstanceAction.SAVING)
                        .build());

        return result;
    }

    @PutMapping("{id}/status")
    public SurveyInstanceStatus updateStatus(@PathVariable("id") long id,
                                             @RequestBody SurveyInstanceStatusChangeCommand command,
                                             Principal principal) {
        return surveyInstanceService.updateStatus(
                Optional.empty(),
                principal.getName(),
                id,
                command);
    }

    @PutMapping("{id}/submission-due-date")
    public int updateSubmissionDueDate(@PathVariable("id") long id,
                                       @RequestBody LocalDate newDate,
                                       Principal principal) {
        return surveyInstanceService.updateSubmissionDueDate(principal.getName(), id, newDate);
    }

    @PutMapping("{id}/approval-due-date")
    public int updateApprovalDueDate(@PathVariable("id") long id,
                                     @RequestBody LocalDate newDate,
                                     Principal principal) {
        return surveyInstanceService.updateApprovalDueDate(principal.getName(), id, newDate);
    }

    @PostMapping("{id}/recipient")
    public long addRecipient(@PathVariable("id") long instanceId,
                             @RequestBody long personId,
                             Principal principal) {
        SurveyInstanceRecipientCreateCommand command = ImmutableSurveyInstanceRecipientCreateCommand.builder()
                .surveyInstanceId(instanceId)
                .personId(personId)
                .build();
        return surveyInstanceService.addRecipient(principal.getName(), command);
    }

    @DeleteMapping("{id}/recipient/{personId}")
    public boolean deleteRecipient(@PathVariable("id") long instanceId,
                                   @PathVariable("personId") long personId,
                                   Principal principal) {
        return surveyInstanceService.deleteRecipient(principal.getName(), instanceId, personId);
    }

    @PostMapping("{id}/owner")
    public long addOwner(@PathVariable("id") long instanceId,
                         @RequestBody long personId,
                         Principal principal) {
        SurveyInstanceOwnerCreateCommand command = ImmutableSurveyInstanceOwnerCreateCommand.builder()
                .surveyInstanceId(instanceId)
                .personId(personId)
                .build();
        return surveyInstanceService.addOwner(principal.getName(), command);
    }

    @DeleteMapping("{id}/owner/{personId}")
    public boolean deleteOwner(@PathVariable("id") long instanceId,
                               @PathVariable("personId") long personId,
                               Principal principal) {
        return surveyInstanceService.deleteOwner(principal.getName(), instanceId, personId);
    }

    @PostMapping("{id}/response/{questionId}/problem")
    public boolean reportProblemWithQuestionResponse(@PathVariable("id") long instanceId,
                                                     @PathVariable("questionId") long questionId,
                                                     @RequestBody String message,
                                                     Principal principal) {
        return surveyInstanceService.reportProblemWithQuestionResponse(
                instanceId,
                questionId,
                message,
                principal.getName());
    }

    @PostMapping("{id}/copy-responses")
    public int copyResponses(@PathVariable("id") long id,
                             @RequestBody CopySurveyResponsesCommand command,
                             Principal principal) {
        return surveyInstanceService.copyResponses(id, command, principal.getName());
    }

    @PostMapping("run/{id}/withdraw-open")
    public int withdrawOpenSurveysForRun(@PathVariable("id") long runId, Principal principal) {
        return surveyInstanceService.withdrawOpenSurveysForRun(runId, principal.getName());
    }

    @PostMapping("template/{id}/withdraw-open")
    public int withdrawOpenSurveysForTemplate(@PathVariable("id") long templateId, Principal principal) {
        return surveyInstanceService.withdrawOpenSurveysForTemplate(templateId, principal.getName());
    }

    /*private void requireRole(UserRoleService userRoleService, Principal principal, SystemRole role) throws Exception {
        String username = principal.getName();
        if (!userRoleService.hasRole(username, role)) {
            throw new Exception("User " + username + " does not have role " + role);
        }
    }*/

    private EntityReference mkRef(String kind, long id) {
        return EntityReference.mkRef(
                org.finos.waltz.model.EntityKind.valueOf(kind.toUpperCase()),
                id);
    }
}
