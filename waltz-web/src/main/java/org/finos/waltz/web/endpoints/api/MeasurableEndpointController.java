/*
 * Waltz - Enterprise Architecture
 * Copyright (C) 2016, 2017, 2018, 2019, 2020 Waltz open source project
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
import org.finos.waltz.model.measurable.Measurable;
import org.finos.waltz.service.measurable.MeasurableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static org.finos.waltz.web.WebUtilities.*;


@RestController
@RequestMapping(MeasurableEndpointController.BASE_URL)
public class MeasurableEndpointController {

    public static final String BASE_URL = "/api/measurable/";

    private final MeasurableService measurableService;


    @Autowired
    public MeasurableEndpointController(MeasurableService measurableService) {
        this.measurableService = measurableService;
    }


    @GetMapping("all")
    public Collection<Measurable> findAll() {
        return measurableService.findAll();
    }

    @GetMapping("id/{id}")
    public Measurable getById(@PathVariable("id") long id) {
        return measurableService.getById(id);
    }

    @PostMapping("measurable-selector")
    public Collection<Measurable> findByMeasurableIdSelector(@RequestBody IdSelectionOptions options) {
        return measurableService.findByMeasurableIdSelector(options);
    }

    @PostMapping("rating-selector")
    public Collection<Measurable> findByRatingIdSelector(@RequestBody IdSelectionOptions options) {
        return measurableService.findByRatingIdSelector(options);
    }

    @GetMapping("search/{query}")
    public Collection<Measurable> search(@PathVariable("query") String query) {
        return measurableService.search(query);
    }

    @GetMapping("external-id/{extId}")
    public Collection<Measurable> findByExternalId(@PathVariable("extId") String extId) {
        return measurableService.findByExternalId(extId);
    }

    @GetMapping("org-unit/id/{id}")
    public Collection<Measurable> findByOrgUnitId(@PathVariable("id") long id) {
        return measurableService.findByOrgUnitId(id);
    }

    @GetMapping("parent-id/{id}")
    public Collection<Measurable> findByParentId(@PathVariable("id") long id) {
        return measurableService.findByParentId(id);
    }

}
