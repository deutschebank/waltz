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

import org.finos.waltz.model.IdSelectionOptions;
import org.finos.waltz.service.server_information.ServerInformationService;
import org.finos.waltz.model.server_information.ServerInformation;
import org.finos.waltz.model.server_information.ServerSummaryBasicStatistics;
import org.finos.waltz.model.server_information.ServerSummaryStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.finos.waltz.common.Checks.checkNotNull;


@RestController
@RequestMapping("/api/server-info")
public class ServerInformationController {

    private final ServerInformationService serverInformationService;


    @Autowired
    public ServerInformationController(ServerInformationService serverInfoService) {
        checkNotNull(serverInfoService, "serverInformationService must not be null");
        this.serverInformationService = serverInfoService;
    }


    @GetMapping("/asset-code/{assetCode}")
    public List<ServerInformation> findByAssetCode(@PathVariable String assetCode) {
        return serverInformationService.findByAssetCode(assetCode);
    }

    @GetMapping("/app-id/{id}")
    public List<ServerInformation> findByAppId(@PathVariable long id) {
        return serverInformationService.findByAppId(id);
    }

    @GetMapping("/{id}")
    public ServerInformation getById(@PathVariable long id) {
        return serverInformationService.getById(id);
    }

    @GetMapping("/external-id/{externalId}")
    public ServerInformation getByExternalId(@PathVariable String externalId) {
        return serverInformationService.getByExternalId(externalId);
    }

    @GetMapping("/hostname/{hostname}")
    public ServerInformation getByHostname(@PathVariable String hostname) {
        return serverInformationService.getByHostname(hostname);
    }

    @PostMapping("/apps/stats")
    public ServerSummaryStatistics calculateStatsForAppSelector(@RequestBody IdSelectionOptions options) {
        return serverInformationService.calculateStatsForAppSelector(options);
    }

    @PostMapping("/apps/stats/basic")
    public ServerSummaryBasicStatistics calculateBasicStatsForAppSelector(@RequestBody IdSelectionOptions options) {
        return serverInformationService.calculateBasicStatsForAppSelector(options);
    }
}
