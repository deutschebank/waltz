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

import org.finos.waltz.common.SetUtilities;
import org.finos.waltz.model.aggregate_overlay_diagram.AggregateOverlayDiagramInstance;
import org.finos.waltz.model.aggregate_overlay_diagram.OverlayDiagramInstanceCreateCommand;
import org.finos.waltz.model.user.SystemRole;
import org.finos.waltz.service.aggregate_overlay_diagram.AggregateOverlayDiagramInstanceService;
import org.finos.waltz.service.user.UserRoleService;
import org.finos.waltz.web.WebUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.common.SetUtilities.asSet;
import static org.finos.waltz.web.WebUtilities.*;


@RestController
@RequestMapping("/api/aggregate-overlay-diagram-instance/")
public class AggregateOverlayDiagramInstanceEndpointController {

    private static final Logger LOG = LoggerFactory.getLogger(AggregateOverlayDiagramInstanceEndpoint.class);

    private final AggregateOverlayDiagramInstanceService aggregateOverlayDiagramInstanceService;
    private final UserRoleService userRoleService;


    @Autowired
    public AggregateOverlayDiagramInstanceEndpointController(AggregateOverlayDiagramInstanceService aggregateOverlayDiagramInstanceService,
                                                   UserRoleService userRoleService) {
        checkNotNull(aggregateOverlayDiagramInstanceService, "aggregateOverlayDiagramInstanceService must not be null");
        checkNotNull(userRoleService, "userRoleService must not be null");

        this.aggregateOverlayDiagramInstanceService = aggregateOverlayDiagramInstanceService;
        this.userRoleService = userRoleService;
    }


    @GetMapping("diagram-id/{id}")
    public Set<AggregateOverlayDiagramInstance> findByDiagramId(@PathVariable("id") long diagramId) {
        return aggregateOverlayDiagramInstanceService.findByDiagramId(diagramId);
    }

    @GetMapping("all")
    public Set<AggregateOverlayDiagramInstance> findAll() {
        return aggregateOverlayDiagramInstanceService.findAll();
    }

    @GetMapping("id/{id}")
    public AggregateOverlayDiagramInstance getById(@PathVariable("id") long id) {
        return aggregateOverlayDiagramInstanceService.getById(id);
    }

    @PostMapping("create")
    public int createInstance(@RequestBody OverlayDiagramInstanceCreateCommand createCmd, Principal principal) {
        String username = principal.getName();
        ensureUserHasEditRights(username);
        return aggregateOverlayDiagramInstanceService.createInstance(createCmd, username);
    }

    private void ensureUserHasEditRights(String username) {
        WebUtilities.requireRoleForSB(userRoleService, username, SetUtilities.map(asSet(SystemRole.AGGREGATE_OVERLAY_DIAGRAM_EDITOR), Enum::name));

    }
}
