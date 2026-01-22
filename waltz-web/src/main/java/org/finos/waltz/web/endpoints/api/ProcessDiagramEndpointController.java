/*
 * Waltz - Enterprise Architecture.
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

import org.finos.waltz.model.IdSelectionOptions;
import org.finos.waltz.model.process_diagram.ProcessDiagram;
import org.finos.waltz.model.process_diagram.ProcessDiagramAndEntities;
import org.finos.waltz.service.process_diagram.ProcessDiagramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static org.finos.waltz.web.WebUtilities.*;

@RestController
@RequestMapping(ProcessDiagramEndpointController.BASE_URL)
public class ProcessDiagramEndpointController {

    public static final String BASE_URL = "/api/process-diagram/";

    private final ProcessDiagramService diagramService;


    @Autowired
    public ProcessDiagramEndpointController(ProcessDiagramService diagramService) {
        this.diagramService = diagramService;
    }

    @GetMapping("id/{id}")
    public ProcessDiagramAndEntities getDiagramAndEntitiesById(@PathVariable("id") long id) {
        return diagramService.getDiagramAndEntitiesById(id);
    }

    @GetMapping("external-id/{externalId}")
    public ProcessDiagram getByExternalId(@PathVariable("externalId") String externalId) {
        return diagramService.getByExternalId(externalId);
    }

    @PostMapping("selector")
    public Collection<ProcessDiagram> findForSelector(@RequestBody IdSelectionOptions options) {
        return diagramService.findBySelector(options);
    }
}
