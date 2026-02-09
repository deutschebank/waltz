/*
 * Waltz - Enterprise Architecture
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

import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.IdSelectionOptions;
import org.finos.waltz.model.UpdateDescriptionCommand;
import org.finos.waltz.model.UpdateNameCommand;
import org.finos.waltz.model.flow_diagram.FlowDiagram;
import org.finos.waltz.model.flow_diagram.SaveDiagramCommand;
import org.finos.waltz.model.user.SystemRole;
import org.finos.waltz.service.flow_diagram.FlowDiagramService;
import org.finos.waltz.service.user.UserRoleService;
import org.finos.waltz.web.endpoints.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.model.user.SystemRole.LINEAGE_EDITOR;


@RestController
@RequestMapping("/api/flow-diagram")
public class FlowDiagramEndpointController {

    private final FlowDiagramService flowDiagramService;
    private final UserRoleService userRoleService;

    @Autowired
    public FlowDiagramEndpointController(FlowDiagramService flowDiagramService,
                                         UserRoleService userRoleService) {
        checkNotNull(flowDiagramService, "flowDiagramService cannot be null");
        checkNotNull(userRoleService, "userRoleService cannot be null");

        this.flowDiagramService = flowDiagramService;
        this.userRoleService = userRoleService;
    }

    @GetMapping("/id/{id}")
    public FlowDiagram getById(@PathVariable("id") long id) {
        return flowDiagramService.getById(id);
    }

    @GetMapping("/entity/{kind}/{id}")
    public List<FlowDiagram> findByEntityReference(@PathVariable("kind") String kind, @PathVariable("id") long id) {
        return flowDiagramService.findByEntityReference(EntityReference.mkRef(EntityKind.valueOf(kind), id));
    }

    @PostMapping("/selector")
    public List<FlowDiagram> findForSelector(@RequestBody IdSelectionOptions options) {
        return flowDiagramService.findForSelector(options);
    }

    @PostMapping
    public Long saveDiagram(Principal principal, @RequestBody SaveDiagramCommand command) {
        requireRole(principal, LINEAGE_EDITOR);
        command.diagramId().ifPresent(id -> verifyCanEdit(principal, id));
        return flowDiagramService.save(command, principal.getName());
    }

    @PostMapping("/update-name/{id}")
    public Boolean updateName(Principal principal, @PathVariable("id") long id, @RequestBody UpdateNameCommand command) {
        requireRole(principal, LINEAGE_EDITOR);
        verifyCanEdit(principal, id);
        return flowDiagramService.updateName(id, command, principal.getName());
    }

    @PostMapping("/update-description/{id}")
    public Boolean updateDescription(Principal principal, @PathVariable("id") long id, @RequestBody UpdateDescriptionCommand command) {
        requireRole(principal, LINEAGE_EDITOR);
        verifyCanEdit(principal, id);
        return flowDiagramService.updateDescription(id, command, principal.getName());
    }

    @PostMapping("/id/{id}/clone")
    public Long cloneDiagram(Principal principal, @PathVariable("id") long id, @RequestBody String newName) {
        requireRole(principal, LINEAGE_EDITOR);
        return flowDiagramService.cloneDiagram(id, newName, principal.getName());
    }

    @PostMapping("/entity/{kind}/{id}")
    public Long makeNewDiagram(Principal principal, @PathVariable("kind") String kind, @PathVariable("id") long id, @RequestBody String name) {
        requireRole(principal, LINEAGE_EDITOR);
        EntityReference ref = EntityReference.mkRef(EntityKind.valueOf(kind), id);
        return flowDiagramService.makeNewDiagramForEntity(ref, principal.getName(), name);
    }

    @DeleteMapping("/id/{id}")
    public Boolean deleteById(Principal principal, @PathVariable("id") long id) {
        requireRole(principal, LINEAGE_EDITOR);
        verifyCanEdit(principal, id);
        return flowDiagramService.deleteById(id, principal.getName());
    }


    private void verifyCanEdit(Principal principal, long diagramId) {
        FlowDiagram diagram = flowDiagramService.getById(diagramId);
        diagram.editorRole().ifPresent(role -> requireRole(principal, role));
    }

    private void requireRole(Principal principal, String role) {
        Set<String> userRoles = userRoleService.getUserRoles(principal.getName());
        if (!userRoles.contains(role)) {
            // In a real application, you'd throw a specific, handled exception
            throw new ForbiddenException("User does not have the required role: " + role);
        }
    }
    
    private void requireRole(Principal principal, SystemRole role) {
        requireRole(principal, role.name());
    }

    // A simple exception to represent a 403 Forbidden, you might have a global exception handler for this
    @ResponseStatus(org.springframework.http.HttpStatus.FORBIDDEN)
    private static class ForbiddenException extends RuntimeException {
        public ForbiddenException(String message) {
            super(message);
        }
    }
}
