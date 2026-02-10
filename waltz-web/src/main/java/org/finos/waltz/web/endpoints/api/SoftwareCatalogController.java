/*
 * Waltz - Enterprise Architecture_
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
import org.finos.waltz.model.software_catalog.SoftwareCatalog;
import org.finos.waltz.model.software_catalog.SoftwareSummaryStatistics;
import org.finos.waltz.service.software_catalog.SoftwareCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

import static org.finos.waltz.web.WebUtilities.mkPath;

@RestController
@RequestMapping("/api/software-catalog/")
public class SoftwareCatalogController {

    private final SoftwareCatalogService service;

    @Autowired
    public SoftwareCatalogController(SoftwareCatalogService service) {
        this.service = service;
    }

    @GetMapping("/package-id/{id}")
    public SoftwareCatalog getByPackageId(@PathVariable("id") long id) {
        return service.getByPackageId(id);
    }

    @GetMapping("/version-id/{id}")
    public SoftwareCatalog getByVersionId(@PathVariable("id") long id) {
        return service.getByVersionId(id);
    }

    @GetMapping("/licence-id/{id}")
    public SoftwareCatalog getByLicenceId(@PathVariable("id") long id) {
        return service.getByLicenceId(id);
    }

    @PostMapping("/selector")
    public SoftwareCatalog getBySelector(@RequestBody IdSelectionOptions options) {
        return service.getBySelector(options);
    }

    @PostMapping("/apps")
    public SoftwareCatalog makeCatalogForAppIds(@RequestBody List<Long> appIds) {
        return service.makeCatalogForAppIds(appIds);
    }

    @PostMapping("/stats")
    public SoftwareSummaryStatistics calculateStatsForAppIdSelector(@RequestBody IdSelectionOptions options) {
        return service.calculateStatisticsForAppIdSelector(options);
    }

}
