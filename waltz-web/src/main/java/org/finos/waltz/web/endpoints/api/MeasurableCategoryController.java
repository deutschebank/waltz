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

import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.measurable_category.ImmutableMeasurableCategory;
import org.finos.waltz.model.measurable_category.MeasurableCategory;
import org.finos.waltz.model.measurable_category.MeasurableCategoryView;
import org.finos.waltz.service.measurable_category.MeasurableCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

import static org.finos.waltz.common.DateTimeUtilities.nowUtc;
import static org.finos.waltz.web.WebUtilities.mkPath;


@RestController
@RequestMapping(MeasurableCategoryController.BASE_URL)
public class MeasurableCategoryController {

    static final String BASE_URL = "/api/measurable-category/";

    private final MeasurableCategoryService measurableCategoryService;


    @Autowired
    public MeasurableCategoryController(MeasurableCategoryService measurableCategoryService) {
        this.measurableCategoryService = measurableCategoryService;
    }


    @GetMapping("all")
    public Collection<MeasurableCategory> findAll() {
        return measurableCategoryService.findAll();
    }

    @GetMapping("id/{id}")
    public MeasurableCategory getById(@PathVariable("id") long id) {
        return measurableCategoryService.getById(id);
    }

    @GetMapping("direct/org-unit/{id}")
    public Collection<MeasurableCategory> findCategoriesByDirectOrgUnit(@PathVariable("id") long id) {
        return measurableCategoryService.findCategoriesByDirectOrgUnit(id);
    }

    @GetMapping("entity/{kind}/{id}")
    public List<MeasurableCategoryView> findPopulatedCategoriesForRef(@PathVariable("kind") String kind,
                                                                      @PathVariable("id") long id) {
        return measurableCategoryService.findPopulatedCategoriesForRef(EntityReference.mkRef(EntityKind.valueOf(kind), id));
    }

    @PostMapping("save")
    public long save(@RequestBody MeasurableCategory category, Principal principal) {
        String username = principal.getName();
        MeasurableCategory categoryToSave = ImmutableMeasurableCategory
                .copyOf(category)
                .withLastUpdatedAt(nowUtc())
                .withLastUpdatedBy(username);
        return measurableCategoryService.save(categoryToSave, username);
    }
}
