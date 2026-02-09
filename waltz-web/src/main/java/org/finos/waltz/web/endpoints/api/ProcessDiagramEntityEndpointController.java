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

import org.finos.waltz.model.process_diagram.ProcessDiagramEntityApplicationAlignment;
import org.finos.waltz.service.process_diagram.ProcessDiagramEntityService;
import org.finos.waltz.web.WebUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

import static org.finos.waltz.web.WebUtilities.mkPath;

@RestController
@RequestMapping("/api/process-diagram-entity")
public class ProcessDiagramEntityEndpointController {

    private final ProcessDiagramEntityService processDiagramEntityService;


    @Autowired
    public ProcessDiagramEntityEndpointController(ProcessDiagramEntityService processDiagramEntityService) {
        this.processDiagramEntityService = processDiagramEntityService;
    }

    @GetMapping("diagram-id/{id}/app-alignments")
    public Set<ProcessDiagramEntityApplicationAlignment> findApplicationAlignmentsByDiagramId(@PathVariable("id") long diagramId) {
        return processDiagramEntityService.findApplicationAlignmentsByDiagramId(diagramId);
    }
}
