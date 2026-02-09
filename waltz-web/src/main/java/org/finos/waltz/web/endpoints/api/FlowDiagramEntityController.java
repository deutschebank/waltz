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

import jakarta.servlet.http.HttpServletRequest;
import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.IdSelectionOptions;
import org.finos.waltz.model.flow_diagram.FlowDiagramEntity;
import org.finos.waltz.model.user.SystemRole;
import org.finos.waltz.service.flow_diagram.FlowDiagramEntityService;
import org.finos.waltz.service.user.UserRoleService;
import org.finos.waltz.web.WebUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.model.EntityReference.mkRef;


@RestController
@RequestMapping("/api/flow-diagram-entity/")
public class FlowDiagramEntityController {

    private final FlowDiagramEntityService flowDiagramEntityService;
    private final UserRoleService userRoleService;


    @Autowired
    public FlowDiagramEntityController(FlowDiagramEntityService flowDiagramEntityService, UserRoleService userRoleService) {
        checkNotNull(flowDiagramEntityService, "flowDiagramEntityService cannot be null");
        checkNotNull(userRoleService, "userRoleService cannot be null");
        this.flowDiagramEntityService = flowDiagramEntityService;
        this.userRoleService = userRoleService;
    }


    @GetMapping("id/{id}")
    public List<FlowDiagramEntity> findByDiagramId(@PathVariable("id") long diagramId) {
        return flowDiagramEntityService.findByDiagramId(diagramId);
    }


    @GetMapping("entity/{kind}/{id}")
    public List<FlowDiagramEntity> findByEntityReference(@PathVariable("kind") String kind,
                                                         @PathVariable("id") long id) {
        return flowDiagramEntityService.findByEntityReference(mkRef(EntityKind.valueOf(kind), id));
    }


    @PostMapping("selector")
    public List<FlowDiagramEntity> findForDiagramSelector(@RequestBody IdSelectionOptions options) {
        return flowDiagramEntityService.findForDiagramSelector(options);
    }


    @PostMapping("id/{id}/{entityKind}/{entityId}")
    public List<FlowDiagramEntity> addRelationship(HttpServletRequest request,
                                                   @PathVariable("id") long diagramId,
                                                   @PathVariable("entityKind") String entityKind,
                                                   @PathVariable("entityId") long entityId) {
        requireRole(request, SystemRole.LINEAGE_EDITOR);
        EntityReference entityReference = mkRef(EntityKind.valueOf(entityKind), entityId);
        flowDiagramEntityService.addRelationship(diagramId, entityReference);
        return flowDiagramEntityService.findByDiagramId(diagramId);
    }


    @DeleteMapping("id/{id}/{entityKind}/{entityId}")
    public List<FlowDiagramEntity> removeRelationship(HttpServletRequest request,
                                                      @PathVariable("id") long diagramId,
                                                      @PathVariable("entityKind") String entityKind,
                                                      @PathVariable("entityId") long entityId) {
        requireRole(request, SystemRole.LINEAGE_EDITOR);
        EntityReference entityReference = mkRef(EntityKind.valueOf(entityKind), entityId);
        flowDiagramEntityService.removeRelationship(diagramId, entityReference);
        return flowDiagramEntityService.findByDiagramId(diagramId);
    }


    private void requireRole(HttpServletRequest request, SystemRole role) {
        WebUtilities.requireRoleForSB(
                userRoleService,
                request,
                role);
    }

}
