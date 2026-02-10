/*
 * Waltz - Enterprise Architecture
 * Copyright (C) 2016, 2017, 2018, 2019, 2020 Waltz open source project
 * See README.md for more information
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.∫
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
import org.finos.waltz.common.exception.DuplicateKeyException;
import org.finos.waltz.model.IdSelectionOptions;
import org.finos.waltz.model.ReleaseLifecycleStatusChangeCommand;
import org.finos.waltz.model.physical_specification_definition.PhysicalSpecDefinition;
import org.finos.waltz.model.physical_specification_definition.PhysicalSpecDefinitionChangeCommand;
import org.finos.waltz.model.user.SystemRole;
import org.finos.waltz.service.physical_specification_definition.PhysicalSpecDefinitionService;
import org.finos.waltz.service.user.UserRoleService;
import org.finos.waltz.web.WebUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.Set;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.common.SetUtilities.map;
import static org.finos.waltz.web.WebUtilities.*;


@RestController
@RequestMapping("/api/physical-spec-definition/")
public class PhysicalSpecDefinitionController {

    private static final String BASE_URL = WebUtilities.mkPath("api", "physical-spec-definition");

    private final UserRoleService userRoleService;

    private final PhysicalSpecDefinitionService specDefinitionService;


    @Autowired
    public PhysicalSpecDefinitionController(UserRoleService userRoleService,
                                            PhysicalSpecDefinitionService specDefinitionService) {
        checkNotNull(userRoleService, "userRoleService cannot be null");
        checkNotNull(specDefinitionService, "specDefinitionService cannot be null");

        this.userRoleService = userRoleService;
        this.specDefinitionService = specDefinitionService;
    }
    
    @GetMapping("specification/{id}")
    public Collection<PhysicalSpecDefinition> findForSpecification(@PathVariable("id") long specId) {
        return specDefinitionService.findForSpecification(specId);
    }
    
    @PostMapping("selector")
    public Collection<PhysicalSpecDefinition> findBySelector(@RequestBody IdSelectionOptions options) {
        return specDefinitionService.findBySelector(options);
    }
    
    @PostMapping("specification/{id}")
    public long create(@PathVariable("id") long physicalSpecificationId,
                       @RequestBody PhysicalSpecDefinitionChangeCommand command,
                       HttpServletRequest principal) {
        
        requireRoleForSB(userRoleService, principal, SystemRole.LOGICAL_DATA_FLOW_EDITOR);
        
        Set<String> existingVersions = map(
                specDefinitionService.findForSpecification(physicalSpecificationId),
                PhysicalSpecDefinition::version);
        
        if (existingVersions.contains(command.version())) {
            throw new DuplicateKeyException("Cannot create physical specification definition with version that already exists");
        }
        
        return specDefinitionService.create(
                getUsernameForSB(principal),
                physicalSpecificationId,
                command);
    }
    
    @PutMapping("specification/{id}/status")
    public boolean updateStatus(@PathVariable("id") long specDefinitionId,
                                @RequestBody ReleaseLifecycleStatusChangeCommand command,
                                HttpServletRequest principal) {
        
        requireRoleForSB(userRoleService, principal, SystemRole.LOGICAL_DATA_FLOW_EDITOR);
        
        return specDefinitionService.updateStatus(
                getUsernameForSB(principal),
                specDefinitionId,
                command);
    }
    
    @DeleteMapping("specification/{id}")
    public int delete(@PathVariable("id") long specDefinitionId, HttpServletRequest principal) {
        requireRoleForSB(userRoleService, principal, SystemRole.LOGICAL_DATA_FLOW_EDITOR);
        
        return specDefinitionService.delete(
                getUsernameForSB(principal),
                specDefinitionId);
    }
}
