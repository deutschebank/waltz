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
import org.finos.waltz.model.command.CommandResponse;
import org.finos.waltz.model.involvement_kind.InvolvementKind;
import org.finos.waltz.model.involvement_kind.InvolvementKindChangeCommand;
import org.finos.waltz.model.involvement_kind.InvolvementKindCreateCommand;
import org.finos.waltz.model.involvement_kind.InvolvementKindUsageStat;
import org.finos.waltz.service.involvement_kind.InvolvementKindService;
import org.finos.waltz.service.user.UserRoleService;
import org.finos.waltz.web.WebUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.web.WebUtilities.getUsernameForSB;

@RestController
@RequestMapping("/api/involvement-kind")
public class InvolvementKindEndpointController {
    private static final Logger LOG = LoggerFactory.getLogger(InvolvementKindEndpointController.class);
    private static final String BASE_URL = WebUtilities.mkPath("api", "involvement-kind");

    private final InvolvementKindService service;
    private final UserRoleService userRoleService;


    @Autowired
    public InvolvementKindEndpointController(InvolvementKindService service, UserRoleService userRoleService) {
        checkNotNull(service, "service must not be null");
        checkNotNull(userRoleService, "userRoleService cannot be null");

        this.service = service;
        this.userRoleService = userRoleService;
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    List<InvolvementKind> getAll() {
        return service.findAll();
    }

    @GetMapping("/key-involvement-kinds/{kind}")
    List<InvolvementKind> findKeyInvolvementKindByEntityKind(@PathVariable EntityKind kind) {
        return service.findKeyInvolvementKindsByEntityKind(kind);
    }

    @GetMapping("/usage-stats")
    Set<InvolvementKindUsageStat> loadUsageStats() {
        return service.loadUsageStats();
    }

    @GetMapping("/usage-stats/{kind}/{id}")
    InvolvementKindUsageStat loadUsageStatsForKind(@PathVariable EntityKind kind, @PathVariable Long id) {
        return service.loadUsageStatsForKind(id);
    }

    @GetMapping("/id/{id}")
    InvolvementKind getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/external-id/{externalId}")
    InvolvementKind getByExternalId(@PathVariable String externalId) {
        return service.getByExternalId(externalId);
    }

    @PostMapping("/update")
    Long createInvolvementKind(HttpServletRequest request,
                               @RequestBody InvolvementKindCreateCommand command) {
        //TODO ensureUserHasAdminRights(request);
        String username = getUsernameForSB(request);
        LOG.info("User: {} creating Involvement Kind: {}", username, command);

        return service.create(command, username);
    }

    @PutMapping("/update")
    CommandResponse<InvolvementKindChangeCommand> updateInvolvementKind(HttpServletRequest request,
                                                                        @RequestBody InvolvementKindChangeCommand command) {
        //TODO ensureUserHasAdminRights(request);
        String username = getUsernameForSB(request);
        LOG.info("User: {} updating Involvement Kind: {}", username, command);
        return service.update(command, username);
    }

    @GetMapping("/{id}")
    Boolean deleteInvolvementKindById(HttpServletRequest request, @PathVariable Long id) {
        //TODO ensureUserHasAdminRights(request);

        String username = getUsernameForSB(request);

        LOG.info("User: {} removing Involvement Kind: {}", username, id);

        return service.delete(id);
    }

}
