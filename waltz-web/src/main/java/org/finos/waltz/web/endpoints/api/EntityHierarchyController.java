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

import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.tally.Tally;
import org.finos.waltz.service.entity_hierarchy.EntityHierarchyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static org.finos.waltz.web.WebUtilities.*;
import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.model.user.SystemRole.ADMIN;

@RestController
@RequestMapping("/api/entity-hierarchy/")
public class EntityHierarchyController {

    private static final Logger LOG = LoggerFactory.getLogger(EntityHierarchyEndpoint.class);

    private final EntityHierarchyService entityHierarchyService;


    @Autowired
    public EntityHierarchyController(EntityHierarchyService entityHierarchyService) {
        checkNotNull(entityHierarchyService, "entityHierarchyService cannot be null");
        this.entityHierarchyService = entityHierarchyService;
    }


    @GetMapping("tallies")
    public Collection<Tally<String>> findTallies() {
        return entityHierarchyService.tallyByKind();
    }

    @GetMapping("root-tallies")
    public Collection<Tally<String>> findRootTallies() {
        return entityHierarchyService.getRootTallies();
    }

    @GetMapping("roots/{kind}")
    public Collection<EntityReference> findRoots(@PathVariable("kind") String kind) {
        return entityHierarchyService.getRoots(EntityKind.valueOf(kind));
    }

    @PostMapping("build/{kind}")
    @PreAuthorize("hasRole('ADMIN')")
    public int buildByKind(@PathVariable("kind") String kindStr) {
        EntityKind kind = EntityKind.valueOf(kindStr);
        LOG.info("Building entity hierarchy for kind: {}", kind);
        return entityHierarchyService.buildFor(kind);
    }

}
