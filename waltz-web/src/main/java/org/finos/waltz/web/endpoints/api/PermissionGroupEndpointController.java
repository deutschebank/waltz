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


import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.attestation.UserAttestationPermission;
import org.finos.waltz.model.permission_group.Permission;
import org.finos.waltz.service.permission.PermissionGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import static org.finos.waltz.web.WebUtilities.*;


@RestController
@RequestMapping(PermissionGroupEndpointController.BASE_URL)
public class PermissionGroupEndpointController {

    static final String BASE_URL = "/api/permission-group/";

    private final PermissionGroupService permissionGroupService;

    @Autowired
    public PermissionGroupEndpointController(PermissionGroupService permissionGroupService) {
        this.permissionGroupService = permissionGroupService;
    }

    @GetMapping("entity-ref/{kind}/{id}")
    public Set<Permission> findPermissionsForParentEntityRef(@PathVariable("kind") String kind, @PathVariable("id") long id, Principal principal) {
        return permissionGroupService.findPermissionsForParentReference(EntityReference.mkRef(EntityKind.valueOf(kind), id), principal.getName());
    }
    
    @GetMapping("entity-ref/{kind}/{id}/supported-attestations/measurable-category")
    public Set<UserAttestationPermission> findSupportedMeasurableCategoryAttestations(@PathVariable("kind") String kind, @PathVariable("id") long id, Principal principal) {
        return permissionGroupService.findSupportedMeasurableCategoryAttestations(EntityReference.mkRef(EntityKind.valueOf(kind), id), principal.getName());
    }

}
