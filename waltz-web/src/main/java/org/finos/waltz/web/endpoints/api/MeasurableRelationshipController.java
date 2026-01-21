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
import org.finos.waltz.model.entity_relationship.EntityRelationship;
import org.finos.waltz.model.entity_relationship.EntityRelationshipKey;
import org.finos.waltz.model.entity_relationship.ImmutableEntityRelationshipKey;
import org.finos.waltz.model.entity_relationship.UpdateEntityRelationshipParams;
import org.finos.waltz.service.measurable_relationship.MeasurableRelationshipService;
import org.finos.waltz.web.WebUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.Map;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.model.EntityReference.mkRef;


@RestController
@RequestMapping("/api/measurable-relationship/")
public class MeasurableRelationshipController {

    private static final Logger LOG = LoggerFactory.getLogger(MeasurableRelationshipController.class);
    private final MeasurableRelationshipService measurableRelationshipService;

    @Autowired
    public MeasurableRelationshipController(MeasurableRelationshipService measurableRelationshipService) {
        checkNotNull(measurableRelationshipService, "measurableRelationshipService cannot be null");
        this.measurableRelationshipService = measurableRelationshipService;
    }

    @GetMapping("/{kind}/{id}")
    public Collection<EntityRelationship> findForEntityReference(
            @PathVariable("kind") EntityKind kind,
            @PathVariable("id") long id) {
        LOG.debug("Finding measurable relationships for entity: {}/{}", kind, id);
        return measurableRelationshipService.findForEntityReference(mkRef(kind, id));
    }

    @GetMapping("/{kind}/{id}/tally")
    public Map<EntityKind, Integer> tallyForEntityReference(
            @PathVariable("kind") EntityKind kind,
            @PathVariable("id") long id) {
        LOG.debug("Tallying measurable relationships for entity: {}/{}", kind, id);
        return measurableRelationshipService.tallyForEntityReference(mkRef(kind, id));
    }

    @PostMapping("/{kindA}/{idA}/{kindB}/{idB}/{relationshipKind}")
    @PreAuthorize("hasRole('CAPABILITY_EDITOR')")
    public boolean createRelationship(
            @PathVariable("kindA") EntityKind kindA,
            @PathVariable("idA") long idA,
            @PathVariable("kindB") EntityKind kindB,
            @PathVariable("idB") long idB,
            @PathVariable("relationshipKind") String relationshipKind,
            @RequestBody String description,
            Principal principal) {

        LOG.info("Creating measurable relationship from {}/{} to {}/{} of kind {}", kindA, idA, kindB, idB, relationshipKind);
        EntityReference entityRefA = mkRef(kindA, idA);
        EntityReference entityRefB = mkRef(kindB, idB);

        return measurableRelationshipService.create(
                principal.getName(),
                entityRefA,
                entityRefB,
                relationshipKind,
                description);
    }

    @PutMapping("/{kindA}/{idA}/{kindB}/{idB}/{relationshipKind}")
    @PreAuthorize("hasRole('CAPABILITY_EDITOR')")
    public boolean updateRelationship(
            @PathVariable("kindA") EntityKind kindA,
            @PathVariable("idA") long idA,
            @PathVariable("kindB") EntityKind kindB,
            @PathVariable("idB") long idB,
            @PathVariable("relationshipKind") String relationshipKind,
            @RequestBody UpdateEntityRelationshipParams params,
            Principal principal) {

        LOG.info("Updating measurable relationship from {}/{} to {}/{} of kind {}", kindA, idA, kindB, idB, relationshipKind);
        EntityRelationshipKey key = ImmutableEntityRelationshipKey.builder()
                .a(mkRef(kindA, idA))
                .b(mkRef(kindB, idB))
                .relationshipKind(relationshipKind)
                .build();

        return measurableRelationshipService.update(key, params, principal.getName());
    }

    @DeleteMapping("/{kindA}/{idA}/{kindB}/{idB}/{relationshipKind}")
    @PreAuthorize("hasRole('CAPABILITY_EDITOR')")
    public boolean removeRelationship(
            @PathVariable("kindA") EntityKind kindA,
            @PathVariable("idA") long idA,
            @PathVariable("kindB") EntityKind kindB,
            @PathVariable("idB") long idB,
            @PathVariable("relationshipKind") String relationshipKind,
            Principal principal) {

        LOG.info("Removing measurable relationship from {}/{} to {}/{} of kind {}", kindA, idA, kindB, idB, relationshipKind);
        EntityRelationshipKey key = ImmutableEntityRelationshipKey.builder()
                .a(mkRef(kindA, idA))
                .b(mkRef(kindB, idB))
                .relationshipKind(relationshipKind)
                .build();

        return measurableRelationshipService.remove(key, principal.getName());
    }
}