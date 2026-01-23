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

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.servlet.http.HttpServletRequest;
import org.finos.waltz.model.role.Role;
import org.finos.waltz.model.role.RoleView.RoleView;
import org.finos.waltz.model.user.SystemRole;
import org.finos.waltz.service.role.RoleService;
import org.finos.waltz.service.user.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;

import static org.finos.waltz.web.WebUtilities.*;


@RestController
@RequestMapping(RoleEndpointController.BASE_URL)
public class RoleEndpointController {

    protected static final String BASE_URL = "/api/role/";

    private final RoleService roleService;
    private final UserRoleService userRoleService;


    @Autowired
    public RoleEndpointController(RoleService roleService,
                                  UserRoleService userRoleService) {
        this.roleService = roleService;
        this.userRoleService = userRoleService;
    }

    @GetMapping
    public Collection<Role> findAllRoles() {
        return roleService.findAllRoles();
    }

    @GetMapping("view/{id}")
    public RoleView getRoleView(@PathVariable("id") long id) {
        return roleService.getRoleView(id);
    }

    @PostMapping
    public Long createCustomRole(HttpServletRequest request) {
        // TODO: Implement security check, for example using Spring Security's @PreAuthorize
        // requireAnyRole(userRoleService, request, SystemRole.USER_ADMIN, SystemRole.ADMIN);
        requireAnyRoleForSB(userRoleService, request, SystemRole.USER_ADMIN, SystemRole.ADMIN);
        try {
            Map<String, String> body = (Map<String, String>) readBodyForSB(request, Map.class);
            String key = body.get("key");
            String roleName = body.get("name");
            String description = body.get("description");
            return roleService.create(key, roleName, description);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * A simple DTO (Data Transfer Object) to represent the request body for creating a role.
     * Using a record provides immutability and conciseness.
     */
    /*public record RoleCreateCommand(
            @JsonProperty("key") String key,
            @JsonProperty("name") String name,
            @JsonProperty("description") String description
    ) {
    }*/


}
