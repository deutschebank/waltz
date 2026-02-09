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
import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.IdSelectionOptions;
import org.finos.waltz.model.attestation.*;
import org.finos.waltz.model.person.Person;
import org.finos.waltz.model.user.SystemRole;
import org.finos.waltz.service.attestation.AttestationInstanceService;
import org.finos.waltz.service.user.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Set;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.web.WebUtilities.*;


@RestController
@RequestMapping("/api/attestation-instance/")
public class AttestationInstanceEndpointontroller {
    private static final Logger LOG = LoggerFactory.getLogger(AttestationInstanceEndpoint.class);

    private final AttestationInstanceService attestationInstanceService;
    private final UserRoleService userRoleService;


    @Autowired
    public AttestationInstanceEndpointontroller (AttestationInstanceService attestationInstanceService,
                                        UserRoleService userRoleService) {
        checkNotNull(attestationInstanceService, "attestationInstanceService cannot be null");
        checkNotNull(userRoleService, "userRoleService cannot be null");

        this.attestationInstanceService = attestationInstanceService;
        this.userRoleService = userRoleService;
    }

    @PostMapping("attest/{id}")
    public boolean attestInstance(@PathVariable("id") long id, Principal principal) {
        return attestationInstanceService.attestInstance(id, principal.getName());
    }

    @PostMapping("attest-entity")
    public boolean attestEntityForUser(Principal principal,
                                       @RequestBody AttestEntityCommand command) {
        return attestationInstanceService.attestForEntity(principal.getName(), command);
    }

    @GetMapping("entity/{kind}/{id}")
    public List<AttestationInstance> findByEntityRef(@PathVariable("kind") EntityKind kind,
                                                     @PathVariable("id") long id) {
        return attestationInstanceService.findByEntityReference(EntityReference.mkRef(kind, id));
    }

    @GetMapping("unattested/user")
    public List<AttestationInstance> findUnattestedByRecipient(Principal principal) {
        return attestationInstanceService.findByRecipient(principal.getName(), true);
    }

    @GetMapping("all/user")
    public List<AttestationInstance> findAllByRecipient(Principal principal) {
        return attestationInstanceService.findByRecipient(principal.getName(), false);
    }

    @GetMapping("historical/user")
    public List<AttestationInstance> findHistoricalForPendingByRecipient(Principal principal) {
        return attestationInstanceService.findHistoricalForPendingByUserId(principal.getName());
    }

    @GetMapping("run/{id}")
    public List<AttestationInstance> findByRunId(@PathVariable("id") long id) {
        return attestationInstanceService.findByRunId(id);
    }

    @GetMapping("{id}/person")
    public List<Person> findPersonsByInstance(@PathVariable("id") long id) {
        return attestationInstanceService.findPersonsByInstanceId(id);
    }

    @PostMapping("selector")
    public List<AttestationInstance> findBySelector(@RequestBody IdSelectionOptions options) throws IOException {
        return attestationInstanceService.findByIdSelector(options);
    }

    @GetMapping("latest/measurable-category/entity/{kind}/{id}")
    public Set<LatestMeasurableAttestationInfo> findLatestMeasurableAttestations(@PathVariable("kind") EntityKind kind,
                                                                                 @PathVariable("id") long id) {
        return attestationInstanceService.findLatestMeasurableAttestations(EntityReference.mkRef(kind, id));
    }

    @PostMapping("applications/attested-entity/{kind}/{id}")
    public Set<ApplicationAttestationInstanceSummary> findApplicationAttestationInstancesForKindAndSelector(
            @PathVariable("kind") EntityKind attestedKind,
            @PathVariable("id") Long attestedId,
            @RequestBody ApplicationAttestationInstanceInfo appAttestationInstanceInfo) {
        return attestationInstanceService.findApplicationAttestationInstancesForKindAndSelector(
                attestedKind,
                attestedId,
                appAttestationInstanceInfo);
    }

    @PostMapping("app-summary")
    public Set<ApplicationAttestationSummaryCounts> findApplicationAttestationSummaryForSelector(
            @RequestBody ApplicationAttestationInstanceInfo appAttestationInstanceInfo) {
        return attestationInstanceService.findAttestationInstanceSummaryForSelector(appAttestationInstanceInfo);
    }

    @GetMapping("cleanup-orphans")
    public int cleanupOrphans(HttpServletRequest httpServletRequest) {
        requireRoleForSB(userRoleService, httpServletRequest, SystemRole.ADMIN);
        String username = getUsernameForSB(httpServletRequest);
        LOG.info("User: {}, requested orphan attestation cleanup", username);
        return attestationInstanceService.cleanupOrphans();
    }

    @PostMapping("reassign-recipients")
    public SyncRecipientsResponse reassignRecipients(HttpServletRequest httpServletRequest) {
        requireRoleForSB(userRoleService, httpServletRequest, SystemRole.ADMIN);
        String username = getUsernameForSB(httpServletRequest);
        LOG.info("User: {}, requested reassign recipients for attestations", username);
        return attestationInstanceService.reassignRecipients();
    }

    @GetMapping("reassign-counts")
    public SyncRecipientsResponse getCountsOfRecipientsToReassign() {
        return attestationInstanceService.getCountsOfRecipientsToReassign();
    }
}
