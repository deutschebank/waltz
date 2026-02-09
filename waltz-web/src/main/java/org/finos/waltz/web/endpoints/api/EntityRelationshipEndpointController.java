/*
 * Waltz - Enterprise Architecture
 * Copyright (C) 2016, 2017, 2018, 2019 Waltz open source project
 * See README.md for more information
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may not use this file except in compliance with the License.
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

import org.finos.waltz.common.DateTimeUtilities;
import org.finos.waltz.common.EnumUtilities;
import org.finos.waltz.common.StreamUtilities;
import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.bulk_upload.entity_relationship.BulkUploadRelationshipApplyResult;
import org.finos.waltz.model.bulk_upload.entity_relationship.BulkUploadRelationshipValidationResult;
import org.finos.waltz.model.entity_relationship.*;
import org.finos.waltz.service.entity_relationship.BulkUploadRelationshipService;
import org.finos.waltz.service.entity_relationship.EntityRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.model.EntityReference.mkRef;

@RestController
@RequestMapping("/api/entity-relationship")
public class EntityRelationshipEndpointController {

    private final EntityRelationshipService entityRelationshipService;
    private final BulkUploadRelationshipService bulkUploadRelationshipService;

    @Autowired
    public EntityRelationshipEndpointController(EntityRelationshipService entityRelationshipService,
                                                BulkUploadRelationshipService bulkUploadRelationshipService) {
        checkNotNull(entityRelationshipService, "entityRelationshipService cannot be null");
        checkNotNull(bulkUploadRelationshipService, "bulkUploadRelationshipService cannot be null");

        this.entityRelationshipService = entityRelationshipService;
        this.bulkUploadRelationshipService = bulkUploadRelationshipService;
    }

    @GetMapping("/id/{id}")
    public EntityRelationship getById(@PathVariable("id") long id) {
        return entityRelationshipService.getById(id);
    }

    @GetMapping("/entity/{kind}/{id}")
    public Collection<EntityRelationship> findForEntity(
            @PathVariable("kind") String kind,
            @PathVariable("id") long id,
            @RequestParam(value = "directionality", required = false, defaultValue = "ANY") String directionality,
            @RequestParam(value = "relationshipKind", required = false) String[] relationshipKinds) {

        EntityReference ref = mkRef(EntityKind.valueOf(kind), id);
        Directionality dir = EnumUtilities.readEnum(directionality, Directionality.class, (s) -> Directionality.ANY);
        List<RelationshipKind> relKinds = parseRelationshipKindParams(relationshipKinds);

        return entityRelationshipService.findForEntity(ref, dir, relKinds);
    }

    @DeleteMapping("/relationship/{aKind}/{aId}/{bKind}/{bId}/{relationshipKind}")
    public boolean removeRelationship(
            @PathVariable("aKind") String aKind,
            @PathVariable("aId") long aId,
            @PathVariable("bKind") String bKind,
            @PathVariable("bId") long bId,
            @PathVariable("relationshipKind") String relationshipKind) {

        EntityRelationshipKey entityRelationshipKey = ImmutableEntityRelationshipKey
                .builder()
                .a(mkRef(EntityKind.valueOf(aKind), aId))
                .b(mkRef(EntityKind.valueOf(bKind), bId))
                .relationshipKind(relationshipKind)
                .build();

        return entityRelationshipService.removeRelationship(entityRelationshipKey);
    }

    @PostMapping("/relationship/{aKind}/{aId}/{bKind}/{bId}/{relationshipKind}")
    public boolean createRelationship(
            @PathVariable("aKind") String aKind,
            @PathVariable("aId") long aId,
            @PathVariable("bKind") String bKind,
            @PathVariable("bId") long bId,
            @PathVariable("relationshipKind") String relationshipKind,
            Principal principal) {

        EntityRelationship entityRelationship = ImmutableEntityRelationship
                .builder()
                .a(mkRef(EntityKind.valueOf(aKind), aId))
                .b(mkRef(EntityKind.valueOf(bKind), bId))
                .relationship(relationshipKind)
                .lastUpdatedBy(principal.getName())
                .lastUpdatedAt(DateTimeUtilities.nowUtc())
                .provenance("waltz")
                .build();

        return entityRelationshipService.createRelationship(entityRelationship);
    }

    @PostMapping("/bulk/preview/{id}")
    public BulkUploadRelationshipValidationResult bulkPreview(
            @PathVariable("id") long relationshipKindId,
            @RequestBody String body) {
        return bulkUploadRelationshipService.bulkPreview(body, relationshipKindId);
    }

    @PostMapping("/bulk/apply/{id}")
    public BulkUploadRelationshipApplyResult bulkApply(
            @PathVariable("id") long relationshipKindId,
            @RequestBody String body,
            Principal principal) {
        BulkUploadRelationshipValidationResult previewResult = bulkUploadRelationshipService.bulkPreview(body, relationshipKindId);
        return bulkUploadRelationshipService.bulkApply(previewResult, relationshipKindId, principal.getName());
    }


    private List<RelationshipKind> parseRelationshipKindParams(String[] relationshipKinds) {
        if (relationshipKinds == null) {
            return null;
        }
        return Arrays.stream(relationshipKinds)
                .map(p -> EnumUtilities.readEnum(p, RelationshipKind.class, (s) -> null))
                .filter(rk -> rk != null)
                .collect(Collectors.toList());
    }
}