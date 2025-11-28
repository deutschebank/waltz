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

import org.finos.waltz.model.staticpanel.StaticPanel;
import org.finos.waltz.model.user.SystemRole;
import org.finos.waltz.service.static_panel.StaticPanelService;
import org.finos.waltz.service.user.UserRoleService;
import org.finos.waltz.web.WebUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.List;

import static org.finos.waltz.common.Checks.checkNotNull;

@RestController
@RequestMapping("/api/static-panel")
public class StaticPanelEndpointController {


    private final StaticPanelService staticPanelService;
    private final UserRoleService userRoleService;


    @Autowired
    public StaticPanelEndpointController(StaticPanelService staticPanelService, UserRoleService userRoleService) {
        checkNotNull(staticPanelService, "staticPanelService cannot be null");
        checkNotNull(userRoleService, "userRoleService cannot be null");
        this.staticPanelService = staticPanelService;
        this.userRoleService = userRoleService;
    }

    @GetMapping()
    List<StaticPanel> findAll() {
        return staticPanelService.findAll();
    }

    @GetMapping("/group")
    List<StaticPanel> findByGroups(@RequestParam List<String> group) {
        return staticPanelService.findByGroups(group.toArray(String[]::new));
    }

    @PostMapping()
    Boolean save(@RequestBody StaticPanel panel) {
        //TODO: WebUtilities.requireRole(userRoleService, request, SystemRole.ADMIN);
        return staticPanelService.save(panel);
    }

    private boolean saveRoute(Request request, Response response) throws IOException {
        WebUtilities.requireRole(userRoleService, request, SystemRole.ADMIN);
        StaticPanel panel = WebUtilities.readBody(request, StaticPanel.class);
        return staticPanelService.save(panel);
    }
}
