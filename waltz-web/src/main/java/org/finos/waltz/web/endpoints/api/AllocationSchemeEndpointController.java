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

import org.finos.waltz.model.allocation_scheme.AllocationScheme;
import org.finos.waltz.service.allocation_schemes.AllocationSchemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.finos.waltz.web.WebUtilities.mkPath;


@RestController
@RequestMapping("/api/allocation-scheme/")
public class AllocationSchemeEndpointController {

    private final AllocationSchemeService allocationSchemesService;


    @Autowired
    public AllocationSchemeEndpointController(AllocationSchemeService allocationSchemesService) {
        this.allocationSchemesService = allocationSchemesService;
    }


    @GetMapping("all")
    public List<AllocationScheme> findAll() {
        return allocationSchemesService.findAll();
    }

    @GetMapping("id/{id}")
    public AllocationScheme getById(@PathVariable("id") long id) {
        return allocationSchemesService.getById(id);
    }

    @GetMapping("category/{id}")
    public List<AllocationScheme> findByCategoryId(@PathVariable("id") long categoryId) {
        return allocationSchemesService.findByCategoryId(categoryId);
    }

}
