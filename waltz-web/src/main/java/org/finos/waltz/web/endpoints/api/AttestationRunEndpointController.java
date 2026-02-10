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
import org.finos.waltz.model.IdCommandResponse;
import org.finos.waltz.model.IdSelectionOptions;
import org.finos.waltz.model.attestation.*;
import org.finos.waltz.model.user.SystemRole;
import org.finos.waltz.service.attestation.AttestationRunService;
import org.finos.waltz.service.user.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.web.WebUtilities.mkPath;
import static org.finos.waltz.web.WebUtilities.requireRoleForSB;
import static org.finos.waltz.web.endpoints.auth.AuthenticationUtilities.getUsername;
import static org.finos.waltz.web.endpoints.auth.AuthenticationUtilities.getUsernameForSB;

@RestController
@RequestMapping("/api/attestation-run/")
public class AttestationRunEndpointController {

    private final AttestationRunService attestationRunService;
    private final UserRoleService userRoleService;


    @Autowired
    public AttestationRunEndpointController(AttestationRunService attestationRunService,
                                    UserRoleService userRoleService) {
        checkNotNull(attestationRunService, "attestationRunService must not be null");
        checkNotNull(userRoleService, "userRoleService must not be null");

        this.attestationRunService = attestationRunService;
        this.userRoleService = userRoleService;
    }


    @GetMapping("id/{id}")
    public AttestationRun getById(@PathVariable("id") long id) {
        return attestationRunService.getById(id);
    }


    @GetMapping()
    public List<AttestationRun> findAll() {
        return attestationRunService.findAll();
    }


    @GetMapping("entity/{kind}/{id}")
    public List<AttestationRun> findByEntityReference(@PathVariable("kind") EntityKind kind,
                                                      @PathVariable("id") long id) {
        return attestationRunService.findByEntityReference(EntityReference.mkRef(kind, id));
    }


    @GetMapping("user")
    public List<AttestationRun> findByRecipient(Principal principal) {
        return attestationRunService.findByRecipient(principal.getName());
    }


    @PostMapping("selector")
    public Collection<AttestationRun> findBySelector(@RequestBody IdSelectionOptions options) throws IOException {
        return attestationRunService.findByIdSelector(options);
    }


    @GetMapping("summary/response")
    public List<AttestationRunResponseSummary> findResponseSummaries() {
        return attestationRunService.findResponseSummaries();
    }


    @PostMapping("create-summary")
    public AttestationCreateSummary getCreateSummary(@RequestBody AttestationRunCreateCommand command) {
        return attestationRunService.getCreateSummary(command);
    }


    @PostMapping()
    public IdCommandResponse create(@RequestBody AttestationRunCreateCommand command, HttpServletRequest httpServletRequest) {
        String userName = getUsernameForSB(httpServletRequest);
        if (!command.selectionOptions().entityReference().kind().equals(EntityKind.APPLICATION)) {
            ensureUserHasAttestationAdminRights(httpServletRequest);
        }
        return attestationRunService.create(
                userName,
                command);
    }


    @GetMapping("id/{id}/recipients")
    public Collection<AttestationRunRecipient> findRunRecipients(@PathVariable("id") long id) {
        return attestationRunService.findRunRecipients(id);
    }


    // -- HELPERS ---

    private void ensureUserHasAttestationAdminRights(HttpServletRequest httpServletRequest) {
        requireRoleForSB(userRoleService, httpServletRequest, SystemRole.ATTESTATION_ADMIN);
    }
}