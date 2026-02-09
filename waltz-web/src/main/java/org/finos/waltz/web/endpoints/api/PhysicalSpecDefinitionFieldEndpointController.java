/*
 * Waltz - Enterprise Architecture
 * Copyright (C) 2016, 2017, 2018, 2019, 2020 Waltz open source project
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
import org.finos.waltz.model.IdSelectionOptions;
import org.finos.waltz.model.UpdateDescriptionCommand;
import org.finos.waltz.model.logical_data_element.LogicalDataElementChangeCommand;
import org.finos.waltz.model.physical_specification_definition.PhysicalSpecDefinitionField;
import org.finos.waltz.model.physical_specification_definition.PhysicalSpecDefinitionFieldChangeCommand;
import org.finos.waltz.model.user.SystemRole;
import org.finos.waltz.service.physical_specification_definition.PhysicalSpecDefinitionFieldService;
import org.finos.waltz.service.user.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import static org.finos.waltz.common.Checks.checkNotNull;
import static java.util.stream.Collectors.toList;
import static org.finos.waltz.web.WebUtilities.*;

@RestController
@RequestMapping(PhysicalSpecDefinitionFieldEndpointController.BASE_URL)
public class PhysicalSpecDefinitionFieldEndpointController {

    static final String BASE_URL = "/api/physical-spec-definition-field/";

    private final PhysicalSpecDefinitionFieldService service;
    private final UserRoleService userRoleService;

    @Autowired
    public PhysicalSpecDefinitionFieldEndpointController(PhysicalSpecDefinitionFieldService service, UserRoleService userRoleService) {
        checkNotNull(service, "service cannot be null");
        checkNotNull(userRoleService, "userRoleService cannot be null");

        this.service = service;
        this.userRoleService = userRoleService;
    }


    @RequestMapping(value = "/spec-definition/{id}", method = RequestMethod.GET)
    public List<PhysicalSpecDefinitionField> findForSpecDefinition(@PathVariable("id") long specId) {
        return service.findForSpecDefinition(specId);
    }


    @PostMapping("/selector")
    public List<PhysicalSpecDefinitionField> findBySelector(@RequestBody IdSelectionOptions options) {
        return service.findBySelector(options);
    }


    @PostMapping("/spec-definition/{id}/fields")
    public List<Long> createFields(HttpServletRequest principal,
                                   @PathVariable("id") long specDefinitionId,
                                   @RequestBody PhysicalSpecDefinitionFieldChangeCommand[] commands) {

        requireRoleForSB(userRoleService, principal, SystemRole.LOGICAL_DATA_FLOW_EDITOR);

        return Arrays.stream(commands)
                .map(c -> service.create(getUsernameForSB(principal), specDefinitionId, c))
                .collect(toList());
    }


    @PutMapping("/{id}/description")
    public int updateDescription(HttpServletRequest principal,
                                 @PathVariable("id") long fieldId,
                                 @RequestBody UpdateDescriptionCommand command) {
        requireRoleForSB(userRoleService, principal, SystemRole.LOGICAL_DATA_FLOW_EDITOR);
        return service.updateDescription(getUsernameForSB(principal), fieldId, command);
    }


    @PutMapping("/{id}/logical-data-element")
    public int updateLogicalDataElement(HttpServletRequest principal,
                                        @PathVariable("id") long fieldId,
                                        @RequestBody LogicalDataElementChangeCommand command) {
        requireRoleForSB(userRoleService, principal, SystemRole.LOGICAL_DATA_FLOW_EDITOR);
        return service.updateLogicalDataElement(getUsernameForSB(principal), fieldId, command);
    }
}
