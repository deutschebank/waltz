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
import org.finos.waltz.model.change_unit.ChangeUnit;
import org.finos.waltz.model.change_unit.UpdateExecutionStatusCommand;
import org.finos.waltz.model.command.CommandResponse;
import org.finos.waltz.model.user.SystemRole;
import org.finos.waltz.service.change_unit.ChangeUnitService;
import org.finos.waltz.service.user.UserRoleService;
import org.finos.waltz.web.WebUtilities;
import org.finos.waltz.web.endpoints.auth.AuthenticationUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

import static org.finos.waltz.common.Checks.checkNotNull;


@RestController
@RequestMapping("/api/change-unit/")
public class ChangeUnitEndpointController {

    private static final Logger LOG = LoggerFactory.getLogger(ChangeUnitEndpointController.class);
    public static final String BASE_URL = WebUtilities.mkPath("api", "change-unit");

    private final ChangeUnitService service;
    private final UserRoleService userRoleService;


    @Autowired
    public ChangeUnitEndpointController(ChangeUnitService service,
                                        UserRoleService userRoleService) {
        checkNotNull(service, "service cannot be null");
        checkNotNull(userRoleService, "userRoleService cannot be null");

        this.service = service;
        this.userRoleService = userRoleService;
    }

    @GetMapping("/id/{id}")
    public ChangeUnit getById(@PathVariable("id") long id) {
        return service.getById(id);
    }

    @GetMapping("/subject/{kind}/{id}")
    public List<ChangeUnit> findBySubjectRef(@PathVariable("kind") EntityKind kind, @PathVariable("id") long id) {
        return service.findBySubjectRef(EntityReference.mkRef(kind, id));
    }

    @GetMapping("/change-set/{id}")
    public List<ChangeUnit> findByChangeSetId(@PathVariable("id") long id) {
        return service.findByChangeSetId(id);
    }

    @PostMapping("/selector")
    public List<ChangeUnit> findBySelector(@RequestBody IdSelectionOptions options) {
        return service.findBySelector(options);
    }

    @PostMapping("/update/execution-status")
    public CommandResponse<UpdateExecutionStatusCommand> updateExecutionStatus(
            @RequestBody UpdateExecutionStatusCommand command,
            HttpServletRequest httpServletRequest) {
        String username = AuthenticationUtilities.getUsernameForSB(httpServletRequest);
        ensureUserEditRights(httpServletRequest);
        LOG.info("User: {} updating Change Unit Execution Status: {}", username, command);
        return service.updateExecutionStatus(command, username);
    }


    private void ensureUserEditRights(HttpServletRequest httpServletRequest) {
        WebUtilities.requireRoleForSB(userRoleService, httpServletRequest, SystemRole.CHANGE_SET_EDITOR);
    }

}