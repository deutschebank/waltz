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

import org.finos.waltz.service.physical_specification_definition.PhysicalSpecDefinitionSampleFileService;
import org.finos.waltz.model.physical_specification_definition.PhysicalSpecDefinitionSampleFile;
import org.finos.waltz.model.physical_specification_definition.PhysicalSpecDefinitionSampleFileCreateCommand;
import org.finos.waltz.web.endpoints.EndpointUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

import static org.finos.waltz.web.WebUtilities.*;
import static org.finos.waltz.common.Checks.checkNotNull;

@RestController
@RequestMapping(PhysicalSpecDefinitionSampleFileController.BASE_URL)
public class PhysicalSpecDefinitionSampleFileController {

    static final String BASE_URL = "/api/physical-spec-definition-sample-file/";

    private final PhysicalSpecDefinitionSampleFileService specDefinitionSampleFileService;


    @Autowired
    public PhysicalSpecDefinitionSampleFileController(PhysicalSpecDefinitionSampleFileService specDefinitionSampleFileService) {
        checkNotNull(specDefinitionSampleFileService, "specDefinitionSampleFileService cannot be null");
        this.specDefinitionSampleFileService = specDefinitionSampleFileService;
    }


    @RequestMapping(method = RequestMethod.GET, path = "spec-definition/{id}")
    public PhysicalSpecDefinitionSampleFile findForSpecDefinition(@PathVariable("id") long specId) {
        return specDefinitionSampleFileService.findForSpecDefinition(specId).orElse(null);
    }


    @RequestMapping(method = RequestMethod.POST, path = "spec-definition/{id}")
    public long create(@PathVariable("id") long specId,
                       @RequestBody PhysicalSpecDefinitionSampleFileCreateCommand command) throws IOException {
        // requireRole(userRoleService, req, SystemRole.LOGICAL_DATA_FLOW_EDITOR); // Role check needs to be implemented via Spring Security
        return specDefinitionSampleFileService.create(specId, command);
    }
}
