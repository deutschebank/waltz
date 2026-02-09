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

import org.finos.waltz.service.flow_diagram.FlowDiagramAnnotationService;
import org.finos.waltz.model.flow_diagram.FlowDiagramAnnotation;
import org.finos.waltz.web.WebUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.web.WebUtilities.mkPath;

@RestController
@RequestMapping("/api/flow-diagram-annotation/")
public class FlowDiagramAnnotationController {

    private final FlowDiagramAnnotationService flowDiagramAnnotationService;


    @Autowired
    public FlowDiagramAnnotationController(FlowDiagramAnnotationService flowDiagramAnnotationService) {
        checkNotNull(flowDiagramAnnotationService, "flowDiagramAnnotationService cannot be null");
        this.flowDiagramAnnotationService = flowDiagramAnnotationService;
    }

    @GetMapping("diagram/{id}")
    public Collection<FlowDiagramAnnotation> findByDiagramId(@PathVariable("id") long diagramId) {
        return flowDiagramAnnotationService.findByDiagramId(diagramId);
    }
}
