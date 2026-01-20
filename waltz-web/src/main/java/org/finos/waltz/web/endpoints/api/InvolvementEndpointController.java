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
import org.finos.waltz.model.IdSelectionOptions;
import org.finos.waltz.model.ImmutableEntityReference;
import org.finos.waltz.model.involvement.EntityInvolvementChangeCommand;
import org.finos.waltz.model.involvement.Involvement;
import org.finos.waltz.model.person.Person;
import org.finos.waltz.service.involvement.InvolvementService;
import org.finos.waltz.service.user.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.finos.waltz.web.WebUtilities.*;
import static org.finos.waltz.web.endpoints.EndpointUtilities.*;

@RestController
@RequestMapping("/api/involvement")
public class InvolvementEndpointController {

    private final InvolvementService service;
    private final UserRoleService userRoleService;


    @Autowired
    public InvolvementEndpointController(InvolvementService service, UserRoleService userRoleService) {
        this.service = service;
        this.userRoleService = userRoleService;
    }

    @GetMapping("/employee/{employeeId}")
    public List<Involvement> findByEmployeeId(@PathVariable("employeeId") String employeeId) {
        return service.findByEmployeeId(employeeId);
    }

    @GetMapping("/entity/{kind}/{id}")
    public List<Involvement> findByEntityReference(@PathVariable("kind") String kind, @PathVariable("id") long id) {
        return service.findByEntityReference(EntityReference.mkRef(EntityKind.valueOf(kind.toUpperCase()), id));
    }

    @GetMapping("/entity/{kind}/{id}/people")
    public List<Person> findPeopleByEntityReference(@PathVariable("kind") String kind, @PathVariable("id") long id) {
        return service.findPeopleByEntityReference(ImmutableEntityReference.mkRef(EntityKind.valueOf(kind.toUpperCase()), id));
    }

    @GetMapping("/entity/{kind}/{id}/user")
    public Set<Long> findExistingInvolvementKindIdsForUser(@PathVariable("kind") String kind,
                                                           @PathVariable("id") long id,
                                                           Principal principal) {
        EntityReference entityReference = ImmutableEntityReference.mkRef(EntityKind.valueOf(kind.toUpperCase()), id);
        return service.findExistingInvolvementKindIdsForUser(entityReference, principal.getName());
    }

    @PostMapping("/selector/involvement")
    public Collection<Involvement> findBySelector(@RequestBody IdSelectionOptions selectionOptions) {
        return service.findByGenericEntitySelector(selectionOptions);
    }

    @PostMapping("/selector/people")
    public List<Person> findPeopleBySelector(@RequestBody IdSelectionOptions selectionOptions) {
        return service.findPeopleByGenericEntitySelector(selectionOptions);
    }

    @GetMapping("/entity-kind/{kind}/orphan-count")
    public Integer countOrphanInvolvementsForKind(@PathVariable("kind") String kind) {
        return service.countOrphanInvolvementsForKind(EntityKind.valueOf(kind.toUpperCase()));
    }

    @DeleteMapping("/entity-kind/{kind}/cleanup-orphans")
    public Integer cleanupInvalidInvolvementsForKind(@PathVariable("kind") String kind, Principal principal) {
        return service.cleanupInvolvementsForKind(principal.getName(), EntityKind.valueOf(kind.toUpperCase()));
    }

    @PostMapping("/entity/{kind}/{id}")
    public boolean updateEntityInvolvement(@PathVariable("kind") String kind,
                                           @PathVariable("id") long id,
                                           @RequestBody EntityInvolvementChangeCommand command,
                                           HttpServletRequest principal) throws IOException {
        EntityReference entityReference = ImmutableEntityReference.mkRef(EntityKind.valueOf(kind.toUpperCase()), id);
        String username = getUsernameForSB(principal);

        requireEditRoleForEntityForSB(
                userRoleService,
                principal,
                entityReference.kind(),
                command.operation(),
                EntityKind.INVOLVEMENT);

        switch (command.operation()) {
            case ADD:
                return service.addEntityInvolvement(username, entityReference, command);
            case REMOVE:
                return service.removeEntityInvolvement(username, entityReference, command);
            default:
                throw new UnsupportedOperationException("Command operation: " + command.operation() + " is not supported");
        }
    }
}
