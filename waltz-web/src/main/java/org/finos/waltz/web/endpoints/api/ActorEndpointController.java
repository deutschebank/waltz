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
import org.finos.waltz.service.actor.ActorService;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.actor.Actor;
import org.finos.waltz.model.actor.ActorChangeCommand;
import org.finos.waltz.model.actor.ActorCreateCommand;
import org.finos.waltz.model.command.CommandResponse;
import org.finos.waltz.model.user.SystemRole;
import org.finos.waltz.service.user.UserRoleService;
import org.finos.waltz.web.WebUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static org.finos.waltz.common.SetUtilities.asSet;
import static org.finos.waltz.web.WebUtilities.*;
import static org.finos.waltz.common.Checks.checkNotNull;

@RestController
@RequestMapping("/api/actor/")
public class ActorEndpointController {

    private static final Logger LOG = LoggerFactory.getLogger(ActorEndpointController.class);

    private final ActorService actorService;
    private final UserRoleService userRoleService;

    @Autowired
    public ActorEndpointController(ActorService actorService, UserRoleService userRoleService) {
        checkNotNull(actorService, "actorService must not be null");
        checkNotNull(userRoleService, "userRoleService cannot be null");

        this.actorService = actorService;
        this.userRoleService = userRoleService;
    }


    @GetMapping("search/{query}")
    public List<EntityReference> search(@PathVariable("query") String query) {
        return actorService.search(query);
    }

    @GetMapping
    public List<Actor> findAll() {
        return actorService.findAll();
    }

    @GetMapping("id/{id}")
    public Actor getById(@PathVariable("id") long id) {
        return actorService.getById(id);
    }

    @PostMapping("update")
    public Long create(@RequestBody ActorCreateCommand command, Principal principal) {
        String username = principal.getName();
        ensureUserHasAdminRights(username);
        LOG.info("User: {} creating Actor: {}", username, command);
        return actorService.create(command, username);
    }

    @PutMapping("update")
    public CommandResponse<ActorChangeCommand> update(@RequestBody ActorChangeCommand command, Principal principal) {
        String username = principal.getName();
        ensureUserHasAdminRights(username);
        LOG.info("User: {} updating Actor: {}", username, command);
        return actorService.update(command, username);
    }

    @DeleteMapping("{id}")
    public boolean delete(@PathVariable("id") long id, Principal principal) {
        String username = principal.getName();
        ensureUserHasAdminRights(username);
        LOG.info("User: {} removing Actor: {}", username, id);
        return actorService.delete(id);
    }

    /**
     * A better approach for authorization in Spring Boot is to use method-level security
     * with an annotation like `@PreAuthorize("hasRole('ACTOR_ADMIN')")`.
     *
     * This would require:
     * 1. Enabling global method security in a configuration class: `@EnableGlobalMethodSecurity(prePostEnabled = true)`
     * 2. Ensuring your Spring Security configuration correctly loads user roles.
     *
     * For now, we will keep the existing manual check.
     *
     * @param username the user to check
     */
    private void ensureUserHasAdminRights(String username) {
        WebUtilities.requireRoleForSB(userRoleService, username, SetUtilities.map(asSet(SystemRole.ACTOR_ADMIN), Enum::name));
    }

}
