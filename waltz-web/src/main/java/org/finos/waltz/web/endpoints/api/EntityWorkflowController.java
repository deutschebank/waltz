/*
 * Waltz - Enterprise Architecture
 * Copyright (C) 2016, 2017, 2018, 2019, 2024 Waltz open source project
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
import org.finos.waltz.model.entity_workflow.EntityWorkflowDefinition;
import org.finos.waltz.model.entity_workflow.EntityWorkflowState;
import org.finos.waltz.model.entity_workflow.EntityWorkflowTransition;
import org.finos.waltz.service.entity_workflow.EntityWorkflowService;
import org.finos.waltz.web.WebUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/entity-workflow/")
public class EntityWorkflowController {

    private final EntityWorkflowService entityWorkflowService;

    @Autowired
    public EntityWorkflowController(EntityWorkflowService entityWorkflowService) {
        this.entityWorkflowService = entityWorkflowService;
    }

    @GetMapping("definition")
    public List<EntityWorkflowDefinition> findAllDefinitions() {
        return entityWorkflowService.findAllDefinitions();
    }

    @GetMapping("entity-ref/{kind}/{id}/{workflowId}/state")
    public EntityWorkflowState getStateForEntityAndWorkflow(
            @PathVariable("kind") EntityKind kind,
            @PathVariable("id") long id,
            @PathVariable("workflowId") long workflowId) {

        return entityWorkflowService.getStateForEntityReferenceAndWorkflowId(
                workflowId,
                EntityReference.mkRef(kind, id));
    }

    @GetMapping("entity-ref/{kind}/{id}/{workflowId}/transition")
    public List<EntityWorkflowTransition> findTransitionsForEntityAndWorkflow(
            @PathVariable("kind") EntityKind kind,
            @PathVariable("id") long id,
            @PathVariable("workflowId") long workflowId) {

        return entityWorkflowService.findTransitionsForEntityReferenceAndWorkflowId(
                workflowId,
                EntityReference.mkRef(kind, id));
    }
}
