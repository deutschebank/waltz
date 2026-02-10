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
import org.finos.waltz.model.logical_data_element.LogicalDataElement;
import org.finos.waltz.service.logical_data_element.LogicalDataElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.finos.waltz.common.Checks.checkNotNull;

@RestController
@RequestMapping("/api/logical-data-element")
public class LogicalDataElementEndpointController {

    private final LogicalDataElementService service;

    @Autowired
    public LogicalDataElementEndpointController(LogicalDataElementService service) {
        checkNotNull(service, "service cannot be null");
        this.service = service;
    }

    @GetMapping("/id/{id}")
    public LogicalDataElement getById(@PathVariable("id") long id) {
        return service.getById(id);
    }

    @GetMapping("/external-id/{extId}")
    public LogicalDataElement getByExternalId(@PathVariable("extId") String extId) {
        return service.getByExternalId(extId);
    }

    @GetMapping("/all")
    public List<LogicalDataElement> findAll() {
        return service.findAll();
    }

    @PostMapping("/selector")
    public List<LogicalDataElement> findBySelector(@RequestBody IdSelectionOptions idSelectionOptions) {
        return service.findBySelector(idSelectionOptions);
    }
}