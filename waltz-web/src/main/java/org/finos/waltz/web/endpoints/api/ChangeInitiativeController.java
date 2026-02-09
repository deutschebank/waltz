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

import org.finos.waltz.service.change_initiative.ChangeInitiativeService;
import org.finos.waltz.model.change_initiative.ChangeInitiative;
import org.finos.waltz.model.entity_relationship.EntityRelationship;
import org.finos.waltz.model.entity_relationship.EntityRelationshipChangeCommand;
import org.finos.waltz.model.IdSelectionOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

import static org.finos.waltz.common.Checks.checkNotNull;


@RestController
@RequestMapping("/api/change-initiative")
public class ChangeInitiativeController {

    private final ChangeInitiativeService service;


    @Autowired
    public ChangeInitiativeController(ChangeInitiativeService service) {
        checkNotNull(service, "service cannot be null");
        this.service = service;
    }


    @GetMapping("/id/{id}")
    public ChangeInitiative getById(@PathVariable("id") long id) {
        return service.getById(id);
    }


    @GetMapping("/id/{id}/related")
    public Collection<EntityRelationship> getRelatedEntitiesForId(@PathVariable("id") long id) {
        return service.getRelatedEntitiesForId(id);
    }


    @PostMapping("/selector")
    public Collection<ChangeInitiative> findForSelector(@RequestBody IdSelectionOptions options) {
        return service.findForSelector(options);
    }


    @GetMapping("/external-id/{externalId}")
    public Collection<ChangeInitiative> findByExternalId(@PathVariable("externalId") String externalId) {
        return service.findByExternalId(externalId);
    }


    @PostMapping("/hierarchy/selector")
    public Collection<ChangeInitiative> findHierarchyForSelector(@RequestBody IdSelectionOptions options) {
        return service.findHierarchyForSelector(options);
    }


    @GetMapping("/search/{query}")
    public Collection<ChangeInitiative> search(@PathVariable("query") String query) {
        return service.search(query);
    }


    @GetMapping("/all")
    public Collection<ChangeInitiative> findAll() {
        return service.findAll();
    }


    @PostMapping("/id/{id}/entity-relationship")
    public Boolean changeEntityRelationship(@PathVariable("id") long id,
                                            @RequestBody EntityRelationshipChangeCommand command,
                                            Principal principal) throws IOException {
        switch (command.operation()) {
            case ADD:
                return service.addEntityRelationship(id, command, principal.getName());
            case REMOVE:
                return service.removeEntityRelationship(id, command, principal.getName());
            default:
                throw new UnsupportedOperationException("Command operation: "
                        + command.operation() + " is not supported");
        }
    }
}
