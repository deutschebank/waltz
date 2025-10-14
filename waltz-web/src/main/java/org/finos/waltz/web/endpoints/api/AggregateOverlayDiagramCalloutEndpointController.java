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
import org.finos.waltz.model.aggregate_overlay_diagram.AggregateOverlayDiagramCallout;
import org.finos.waltz.model.aggregate_overlay_diagram.DiagramCalloutCreateCommand;
import org.finos.waltz.model.user.SystemRole;
import org.finos.waltz.service.aggregate_overlay_diagram.AggregateOverlayDiagramCalloutService;
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
@RequestMapping("/api/aggregate-overlay-diagram-callout/")
public class AggregateOverlayDiagramCalloutEndpointController {

    private static final Logger LOG = LoggerFactory.getLogger(AggregateOverlayDiagramCalloutEndpointController.class);

    private final AggregateOverlayDiagramCalloutService aggregateOverlayDiagramCalloutService;
    private final UserRoleService userRoleService;


    @Autowired
    public AggregateOverlayDiagramCalloutEndpointController(AggregateOverlayDiagramCalloutService aggregateOverlayDiagramCalloutService,
                                                  UserRoleService userRoleService) {
        checkNotNull(aggregateOverlayDiagramCalloutService, "aggregateOverlayDiagramCalloutService must not be null");
        checkNotNull(userRoleService, "userRoleService must not be null");

        this.aggregateOverlayDiagramCalloutService = aggregateOverlayDiagramCalloutService;
        this.userRoleService = userRoleService;
    }


    @GetMapping("diagram-instance-id/{id}")
    public Set<AggregateOverlayDiagramCallout> findByDiagramInstanceId(@PathVariable("id") Long diagramInstanceId) {
        return aggregateOverlayDiagramCalloutService.findByDiagramInstanceId(diagramInstanceId);
    }

    @PostMapping("create")
    public int create(@RequestBody DiagramCalloutCreateCommand createCommand, Principal principal) {
        ensureUserHasEditRights(principal.getName());
        return aggregateOverlayDiagramCalloutService.create(createCommand);
    }

    @PostMapping("update")
    public int update(@RequestBody AggregateOverlayDiagramCallout callout, Principal principal) {
        ensureUserHasEditRights(principal.getName());
        return aggregateOverlayDiagramCalloutService.update(callout);
    }

    @DeleteMapping("remove/id/{id}")
    public int delete(@PathVariable("id") long calloutId, Principal principal) {
        ensureUserHasEditRights(principal.getName());
        return aggregateOverlayDiagramCalloutService.delete(calloutId);
    }

    /**
     * A better approach for authorization in Spring Boot is to use method-level security
     * with an annotation like `@PreAuthorize("hasRole('AGGREGATE_OVERLAY_DIAGRAM_EDITOR')")`.
     *
     * This would require:
     * 1. Enabling global method security in a configuration class: `@EnableGlobalMethodSecurity(prePostEnabled = true)`
     * 2. Ensuring your Spring Security configuration correctly loads user roles.
     *
     * For now, we will keep the existing manual check.
     *
     * @param username the user to check
     */
    private void ensureUserHasEditRights(String username) {
        WebUtilities.requireRoleForSB(userRoleService, username, SetUtilities.map(asSet(SystemRole.ACTOR_ADMIN), Enum::name));
    }

}
