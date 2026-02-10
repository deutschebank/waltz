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
import org.finos.waltz.model.EntityWithOperations;
import org.finos.waltz.model.entity_named_note.EntityNamedNodeType;
import org.finos.waltz.model.entity_named_note.EntityNamedNoteTypeChangeCommand;
import org.finos.waltz.service.entity_named_note.EntityNamedNoteTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.model.EntityReference.mkRef;
import static org.finos.waltz.web.WebUtilities.getEntityReference;


@RestController
@RequestMapping("/api/entity-named-note-type")
public class EntityNamedNoteTypeEndpointController {

    private final EntityNamedNoteTypeService entityNamedNoteTypeService;

    @Autowired
    public EntityNamedNoteTypeEndpointController(EntityNamedNoteTypeService entityNamedNoteTypeService) {
        checkNotNull(entityNamedNoteTypeService, "entityNamedNoteTypeService cannot be null");
        this.entityNamedNoteTypeService = entityNamedNoteTypeService;
    }

    @GetMapping
    public List<EntityNamedNodeType> findAll() {
        return entityNamedNoteTypeService.findAll();
    }

    @GetMapping("/external-id/{externalId}")
    public EntityNamedNodeType getByExternalId(@PathVariable("externalId") String externalId) {
        return entityNamedNoteTypeService.getByExternalId(externalId);
    }

    @GetMapping("/by-ref/{kind}/{id}")
    public Set<EntityWithOperations<EntityNamedNodeType>> findForRefAndUser(
            @PathVariable("kind") String kind,
            @PathVariable("id") long id,
            Principal principal) {
        EntityReference ref = mkRef(EntityKind.valueOf(kind), id);
        return entityNamedNoteTypeService.findForRefAndUser(ref, principal.getName());
    }

    @PostMapping
    // @PreAuthorize("hasRole('ADMIN')")
    public Long create(@RequestBody EntityNamedNoteTypeChangeCommand command, Principal principal) {
        return entityNamedNoteTypeService.create(command, principal.getName());
    }

    @PutMapping("/{id}")
    // @PreAuthorize("hasRole('ADMIN')")
    public Boolean update(
            @PathVariable("id") long id,
            @RequestBody EntityNamedNoteTypeChangeCommand command,
            Principal principal) {
        return entityNamedNoteTypeService.update(id, command, principal.getName());
    }

    @DeleteMapping("/{id}")
    // @PreAuthorize("hasRole('ADMIN')")
    public Boolean removeById(@PathVariable("id") long id, Principal principal) {
        return entityNamedNoteTypeService.removeById(id, principal.getName());
    }
}