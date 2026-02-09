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
import org.finos.waltz.model.rel.RelationshipKind;
import org.finos.waltz.model.rel.UpdateRelationshipKindCommand;
import org.finos.waltz.model.user.SystemRole;
import org.finos.waltz.service.relationship_kind.RelationshipKindService;
import org.finos.waltz.service.user.UserRoleService;
import org.finos.waltz.web.WebUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.web.WebUtilities.mkPath;

@RestController
@RequestMapping("/api/relationship-kind/")
public class RelationshipKindEndpointController {

    private final RelationshipKindService relationshipKindService;
    private final UserRoleService userRoleService;

    @Autowired
    public RelationshipKindEndpointController(RelationshipKindService relationshipKindService,
                                              UserRoleService userRoleService) {
        checkNotNull(relationshipKindService, "relationshipKindService cannot be null");
        checkNotNull(userRoleService, "userRoleService cannot be null");

        this.relationshipKindService = relationshipKindService;
        this.userRoleService = userRoleService;
    }

    @GetMapping()
    public Collection<RelationshipKind> findAll() {
        return relationshipKindService.findAll();
    }

    @GetMapping("/entities/{aKind}/{aId}/{bKind}/{bId}")
    public Set<RelationshipKind> findRelationshipKindsBetweenEntities(
            @PathVariable("aKind") EntityKind aKind,
            @PathVariable("aId") long aId,
            @PathVariable("bKind") EntityKind bKind,
            @PathVariable("bId") long bId) {
        EntityReference aRef = EntityReference.mkRef(aKind, aId);
        EntityReference bRef = EntityReference.mkRef(bKind, bId);
        return relationshipKindService.findRelationshipKindsBetweenEntites(aRef, bRef);
    }

    @PostMapping("/create")
    public boolean create(@RequestBody RelationshipKind relationshipKind, HttpServletRequest request) {
        ensureUserHasAdminRights(request);
        return relationshipKindService.create(relationshipKind);
    }

    @PostMapping("/id/{id}")
    public boolean update(@PathVariable("id") long relKindId,
                          @RequestBody UpdateRelationshipKindCommand updateCommand,
                          HttpServletRequest request) {
        ensureUserHasAdminRights(request);
        return relationshipKindService.update(relKindId, updateCommand);
    }

    @DeleteMapping("/id/{id}")
    public boolean remove(@PathVariable("id") long relationshipKindId, HttpServletRequest request) {
        ensureUserHasAdminRights(request);
        return relationshipKindService.remove(relationshipKindId);
    }

    private void ensureUserHasAdminRights(HttpServletRequest request) {
        WebUtilities.requireRoleForSB(userRoleService, request, SystemRole.ADMIN);
    }
}
