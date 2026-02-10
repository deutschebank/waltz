/*
 * Waltz - Enterprise Architecture Platform
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
import org.finos.waltz.common.exception.InsufficientPrivelegeException;
import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.custom_environment.CustomEnvironmentUsage;
import org.finos.waltz.model.custom_environment.CustomEnvironmentUsageInfo;
import org.finos.waltz.model.custom_environment.ImmutableCustomEnvironmentUsage;
import org.finos.waltz.service.custom_environment.CustomEnvironmentUsageService;
import org.finos.waltz.web.endpoints.auth.AuthenticationUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collection;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.model.EntityReference.mkRef;


@RestController
@RequestMapping("api/custom-environment-usage")
public class CustomEnvironmentUsageController {

    private final CustomEnvironmentUsageService customEnvironmentUsageService;


    @Autowired
    public CustomEnvironmentUsageController(CustomEnvironmentUsageService customEnvironmentUsageService) {
        checkNotNull(customEnvironmentUsageService, "customEnvironmentUsageService cannot be null");
        this.customEnvironmentUsageService = customEnvironmentUsageService;
    }


    @GetMapping("owning-entity/kind/{kind}/id/{id}")
    public Collection<CustomEnvironmentUsageInfo> findUsageInfoByOwningEntity(
            @PathVariable("kind") String kind,
            @PathVariable("id") long id) {
        return customEnvironmentUsageService.findUsageInfoByOwningEntity(mkRef(EntityKind.valueOf(kind), id));
    }
    
    @PostMapping("add")
    public long addAsset(
            @RequestBody CustomEnvironmentUsage usage,
            HttpServletRequest principal) throws InsufficientPrivelegeException {
        String username = AuthenticationUtilities.getUsernameForSB(principal);
        CustomEnvironmentUsage usageWithCreator = ImmutableCustomEnvironmentUsage
                .copyOf(usage)
                .withCreatedBy(username);
        return customEnvironmentUsageService.addAsset(usageWithCreator, username);
    }
    
    @DeleteMapping("remove/id/{id}")
    public boolean remove(@PathVariable("id") long usageId, HttpServletRequest principal) throws InsufficientPrivelegeException {
        String username = AuthenticationUtilities.getUsernameForSB(principal);
        return customEnvironmentUsageService.remove(usageId, username);
    }

}
