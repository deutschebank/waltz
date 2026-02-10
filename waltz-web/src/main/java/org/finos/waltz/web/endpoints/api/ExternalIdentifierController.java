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

import org.finos.waltz.common.exception.InsufficientPrivelegeException;
import org.finos.waltz.model.*;
import org.finos.waltz.model.external_identifier.ExternalIdentifier;
import org.finos.waltz.service.external_identifier.ExternalIdentifierService;
import org.finos.waltz.service.permission.permission_checker.FlowPermissionChecker;
import org.finos.waltz.web.WebUtilities;
import org.finos.waltz.web.endpoints.auth.AuthenticationUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Set;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.model.EntityKind.LOGICAL_DATA_FLOW;


@RestController
@RequestMapping("/api/external-identifier/")
public class ExternalIdentifierController {

    private final ExternalIdentifierService externalIdentifierService;
    private final FlowPermissionChecker flowPermissionChecker;


    @Autowired
    public ExternalIdentifierController(ExternalIdentifierService externalIdentifierService,
                                        FlowPermissionChecker flowPermissionChecker) {
        checkNotNull(externalIdentifierService, "externalIdentifierService cannot be null");
        checkNotNull(flowPermissionChecker, "flowPermissionChecker cannot be null");

        this.externalIdentifierService = externalIdentifierService;
        this.flowPermissionChecker = flowPermissionChecker;
    }
    
    @GetMapping("/entity/{kind}/{id}")
    public Set<ExternalIdentifier> findForEntityReference(@PathVariable("kind") String kind,
                                                          @PathVariable("id") long id) {
        EntityReference ref = ImmutableEntityReference.builder()
                .kind(EntityKind.valueOf(kind))
                .id(id)
                .build();
        return externalIdentifierService.findByEntityReference(ref);
    }

    @DeleteMapping("/entity/{kind}/{id}/{system}/externalId/**")
    public int delete(@PathVariable("kind") String kind,
                      @PathVariable("id") long id,
                      @PathVariable("system") String system,
                      HttpServletRequest request,
                      Principal principal, jakarta.servlet.http.HttpServletRequest httpServletRequest) throws InsufficientPrivelegeException {
        String username = AuthenticationUtilities.getUsernameForSB(httpServletRequest);
        String externalId = null;// this need to check with apicall. WebUtilities.getSplattedTail(request);
        EntityReference ref = ImmutableEntityReference.builder().kind(EntityKind.valueOf(kind)).id(id).build();
        checkHasPermission(ref, username);
        return externalIdentifierService.delete(ref, externalId, system, principal.getName());
    }

    @PostMapping("/entity/{kind}/{id}/externalId/**")
    public int create(@PathVariable("kind") String kind,
                      @PathVariable("id") long id,
                      HttpServletRequest request,
                      Principal principal) throws InsufficientPrivelegeException {
        String externalId = null; //WebUtilities.getSplattedTail(request);
        EntityReference ref = ImmutableEntityReference.builder().kind(EntityKind.valueOf(kind)).id(id).build();
        checkHasPermission(ref, principal.getName());
        return externalIdentifierService.create(ref, externalId, principal.getName());
    }

    private void checkHasPermission(EntityReference ref, String username) throws InsufficientPrivelegeException {
        if (ref.kind().equals(LOGICAL_DATA_FLOW)) {
            Set<Operation> permissions = flowPermissionChecker.findPermissionsForFlow(ref.id(), username);
            flowPermissionChecker.verifyEditPerms(permissions, EntityKind.EXTERNAL_IDENTIFIER, username);
        }
    }
}
