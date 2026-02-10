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
import org.finos.waltz.service.flow_diagram.FlowDiagramOverlayGroupService;
import org.finos.waltz.model.flow_diagram.FlowDiagramOverlayGroup;
import org.finos.waltz.model.flow_diagram.FlowDiagramOverlayGroupEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.web.WebUtilities.getUsername;
import static org.finos.waltz.web.WebUtilities.getUsernameForSB;


@RestController
@RequestMapping(FlowDiagramOverlayGroupController.BASE_URL)
public class FlowDiagramOverlayGroupController {

    public static final String BASE_URL = "/api/flow-diagram-overlay-group";

    private final FlowDiagramOverlayGroupService flowDiagramOverlayGroupService;


    @Autowired
    public FlowDiagramOverlayGroupController(FlowDiagramOverlayGroupService flowDiagramOverlayGroupService) {
        checkNotNull(flowDiagramOverlayGroupService, "flowDiagramOverlayGroupService cannot be null");
        this.flowDiagramOverlayGroupService = flowDiagramOverlayGroupService;
    }


    @GetMapping("/diagram-id/{id}")
    public Set<FlowDiagramOverlayGroup> findByDiagramId(@PathVariable("id") long diagramId) {
        return flowDiagramOverlayGroupService.findByDiagramId(diagramId);
    }

    @GetMapping("/overlays/diagram-id/{id}")
    public Set<FlowDiagramOverlayGroupEntry> findOverlaysByDiagramId(@PathVariable("id") long diagramId) {
        return flowDiagramOverlayGroupService.findOverlaysByDiagramId(diagramId);
    }

    @PostMapping("/create")
    public Long createGroup(@RequestBody FlowDiagramOverlayGroup group, HttpServletRequest principal) {
        return flowDiagramOverlayGroupService.create(group, getUsernameForSB(principal));
    }

    @DeleteMapping("/id/{id}")
    public Boolean deleteGroup(@PathVariable("id") long groupId, HttpServletRequest principal) {
        return flowDiagramOverlayGroupService.delete(groupId, getUsernameForSB(principal));
    }

    @PostMapping("/clone/diagram-id/{diagramId}/id/{id}")
    public Long cloneGroup(@PathVariable("diagramId") long diagramId,
                           @PathVariable("id") long groupId,
                           HttpServletRequest principal) {
        return flowDiagramOverlayGroupService.clone(diagramId, groupId, getUsernameForSB(principal));
    }

}
