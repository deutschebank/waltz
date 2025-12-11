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

import jakarta.servlet.http.HttpServletRequest;
import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.EntityWithOperations;
import org.finos.waltz.model.entity_named_note.EntityNamedNodeType;
import org.finos.waltz.model.entity_named_note.EntityNamedNoteTypeChangeCommand;
import org.finos.waltz.service.entity_named_note.EntityNamedNoteTypeService;
import org.finos.waltz.service.user.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.web.WebUtilities.getUsernameForSB;
import static org.finos.waltz.web.WebUtilities.mkPath;

@RestController
@RequestMapping("/api/entity-named-note-type")
public class EntityNamedNoteTypeEndpointController {
    private static final String BASE = mkPath("api", "entity-named-note-type");


    private final EntityNamedNoteTypeService entityNamedNoteTypeService;
    private final UserRoleService userRoleService;


    @Autowired
    public EntityNamedNoteTypeEndpointController
            (EntityNamedNoteTypeService entityNamedNoteTypeService,
             UserRoleService userRoleService) {
        checkNotNull(entityNamedNoteTypeService, "entityNamedNoteTypeService cannot be null");
        checkNotNull(userRoleService, "userRoleService cannot be null");
        this.entityNamedNoteTypeService = entityNamedNoteTypeService;
        this.userRoleService = userRoleService;
    }

    @GetMapping()
    List<EntityNamedNodeType> findAll() {
        return entityNamedNoteTypeService.findAll();
    }

    @GetMapping("/external-id/{externalId}")
    EntityNamedNodeType getByExternalId(@PathVariable String externalId) {
        return entityNamedNoteTypeService.getByExternalId(externalId);
    }

    @DeleteMapping("/{id}")
    Boolean remove(HttpServletRequest request, @PathVariable Long id) {
        //TODO: ensureUserHasAdminRights(req);
        return entityNamedNoteTypeService.removeById(id, getUsernameForSB(request));
    }

    @PutMapping("/{id}")
    Boolean update(HttpServletRequest request, @PathVariable Long id,
                   @RequestBody EntityNamedNoteTypeChangeCommand command) {
        //TODO ensureUserHasAdminRights(req);
        return entityNamedNoteTypeService.update(id, command, getUsernameForSB(request));
    }

    @PostMapping()
    Long create(HttpServletRequest request,
                @RequestBody EntityNamedNoteTypeChangeCommand command) {
        //TODO ensureUserHasAdminRights(req);
        return entityNamedNoteTypeService.create(command, getUsernameForSB(request));
    }

    @GetMapping("/by-ref/{kind}/{id}")
    Set<EntityWithOperations<EntityNamedNodeType>> findForRefAndUser(HttpServletRequest request,
                                                                     @PathVariable EntityKind kind, @PathVariable Long id) {
        return entityNamedNoteTypeService.findForRefAndUser(EntityReference.mkRef(kind, id), getUsernameForSB(request));
    }


}
