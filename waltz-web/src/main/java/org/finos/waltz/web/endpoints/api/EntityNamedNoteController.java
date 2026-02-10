/*
 * Waltz - Enterprise Architecture_
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

import org.finos.waltz.common.exception.InsufficientPrivelegeException;
import org.finos.waltz.model.*;
import org.finos.waltz.model.entity_named_note.EntityNamedNote;
import org.finos.waltz.service.entity_named_note.EntityNamedNoteService;
import org.finos.waltz.service.entity_named_note.EntityNamedNoteTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.Optional;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.common.SetUtilities.asSet;
import static org.finos.waltz.common.SetUtilities.intersection;
import static org.finos.waltz.web.WebUtilities.getEntityReference;
import static org.finos.waltz.web.WebUtilities.mkPath;

@RestController
@RequestMapping("/api/entity-named-note/")
public class EntityNamedNoteController {

    static final String BASE = mkPath("api", "entity-named-note");

    private final EntityNamedNoteService entityNamedNoteService;
    private final EntityNamedNoteTypeService entityNamedNoteTypeService;


    @Autowired
    public EntityNamedNoteController(EntityNamedNoteService entityNamedNoteService, EntityNamedNoteTypeService entityNamedNoteTypeService) {
        checkNotNull(entityNamedNoteService, "entityNamedNoteService cannot be null");
        checkNotNull(entityNamedNoteTypeService, "entityNamedNoteTypeService cannot be null");
        this.entityNamedNoteService = entityNamedNoteService;
        this.entityNamedNoteTypeService = entityNamedNoteTypeService;
    }


    @GetMapping("entity-ref/{kind}/{id}")
    public Collection<EntityNamedNote> findByEntityReference(@PathVariable("kind") String kind, @PathVariable("id") long id) {
        return entityNamedNoteService.findByEntityReference(EntityReference.mkRef(EntityKind.valueOf(kind), id));
    }


    @PutMapping("entity-ref/{kind}/{id}/{noteTypeId}")
    public boolean save(@PathVariable("kind") String kind,
                        @PathVariable("id") long id,
                        @PathVariable("noteTypeId") long noteTypeId,
                        @RequestBody StringChangeCommand command,
                        Principal principal) throws InsufficientPrivelegeException {
        EntityReference entityReference = EntityReference.mkRef(EntityKind.valueOf(kind), id);
        ensureHasPermission(entityReference, noteTypeId, principal.getName(), Operation.ADD, Operation.UPDATE);
        return entityNamedNoteService.save(
                entityReference,
                noteTypeId,
                command.newStringVal().orElse(null),
                principal.getName());
    }


    @DeleteMapping("entity-ref/{kind}/{id}/{noteTypeId}")
    public boolean remove(@PathVariable("kind") String kind,
                          @PathVariable("id") long id,
                          @PathVariable("noteTypeId") long noteTypeId,
                          Principal principal) throws InsufficientPrivelegeException {
        EntityReference ref = EntityReference.mkRef(EntityKind.valueOf(kind), id);
        ensureHasPermission(ref, noteTypeId, principal.getName(), Operation.REMOVE);
        return entityNamedNoteService.remove(
                ref,
                noteTypeId,
                principal.getName());
    }


    private void ensureHasPermission(EntityReference ref, long noteTypeId, String username, Operation... ops) throws InsufficientPrivelegeException {
        Boolean hasPerm = entityNamedNoteTypeService
                .findForRefAndUser(
                        ref,
                        username)
                .stream()
                .filter(d -> d.entity().id().equals(Optional.of(noteTypeId)))
                .map(EntityWithOperations::operations)
                .map(availableOps -> intersection(availableOps, asSet(ops)))
                .map(s -> !s.isEmpty())
                .findFirst()
                .orElse(false);

        if (! hasPerm) {
            throw new InsufficientPrivelegeException("User cannot modify note");
        }
    }
}
