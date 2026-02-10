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
import org.finos.waltz.service.database_information.DatabaseInformationService;
import org.finos.waltz.web.json.ApplicationDatabases;
import org.finos.waltz.web.json.ImmutableApplicationDatabases;
import org.finos.waltz.model.database_information.DatabaseInformation;
import org.finos.waltz.model.database_information.DatabaseSummaryStatistics;
import org.finos.waltz.web.WebUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/database/")
public class DatabaseInformationEndpointController {

    private final DatabaseInformationService databaseInformationService;

    @Autowired
    public DatabaseInformationEndpointController(DatabaseInformationService databaseInformationService) {
        this.databaseInformationService = databaseInformationService;
    }

    @GetMapping("app/{id}")
    public List<DatabaseInformation> findForApp(@PathVariable("id") long id) {
        return databaseInformationService.findByApplicationId(id);
    }

    @GetMapping("{id}")
    public DatabaseInformation getById(@PathVariable("id") long id) {
        return databaseInformationService.getById(id);
    }

    @GetMapping("external-id/{externalId}")
    public DatabaseInformation getByExternalId(@PathVariable("externalId") String externalId) {
        return databaseInformationService.getByExternalId(externalId);
    }

    @PostMapping
    public List<ApplicationDatabases> findForAppSelector(@RequestBody IdSelectionOptions options) {
        return databaseInformationService.findByApplicationSelector(options)
                .entrySet()
                .stream()
                .map(e -> ImmutableApplicationDatabases.builder()
                        .applicationId(e.getKey())
                        .databases(e.getValue())
                        .build())
                .collect(Collectors.toList());
    }

    @PostMapping("stats")
    public DatabaseSummaryStatistics calculateStatsForAppIdSelector(@RequestBody IdSelectionOptions options) {
        return databaseInformationService.calculateStatsForAppIdSelector(options);
    }
}
