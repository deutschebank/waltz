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
import org.finos.waltz.model.measurable_category.ImmutableMeasurableCategory;
import org.finos.waltz.model.measurable_category.MeasurableCategory;
import org.finos.waltz.model.measurable_category.MeasurableCategoryView;
import org.finos.waltz.service.measurable_category.MeasurableCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

import static org.finos.waltz.common.DateTimeUtilities.nowUtc;
import static org.finos.waltz.web.WebUtilities.getUsernameForSB;

@RestController
@RequestMapping("/api/measurable-category")
public class MeasurableCategoryEndpointController {

    private final MeasurableCategoryService measurableCategoryService;


    @Autowired
    public MeasurableCategoryEndpointController(MeasurableCategoryService measurableCategoryService) {
        this.measurableCategoryService = measurableCategoryService;
    }

    @GetMapping("/all")
    Collection<MeasurableCategory> getAll() {
        return measurableCategoryService.findAll();
    }

    @GetMapping("/id/{id}")
    MeasurableCategory getByIdAll(@PathVariable Long id) {
        return measurableCategoryService.getById(id);
    }

    @GetMapping("/id/org-unit/{id}")
    Collection<MeasurableCategory> getCategoriesByDirectOrgUnit(@PathVariable Long id) {
        return measurableCategoryService.findCategoriesByDirectOrgUnit(id);
    }

    @GetMapping("/entity/{kind}/{id}")
    List<MeasurableCategoryView> findPopulatedCategoriesForRef(@PathVariable EntityKind kind,
                                                               @PathVariable Long id) {
        return measurableCategoryService.findPopulatedCategoriesForRef(EntityReference.mkRef(kind, id));
    }

    @GetMapping("/save")
    Long save(HttpServletRequest request, @RequestBody MeasurableCategory body) {
        String username = getUsernameForSB(request);
        return measurableCategoryService
                .save(
                        ImmutableMeasurableCategory
                                .copyOf(body)
                                .withLastUpdatedAt(nowUtc())
                                .withLastUpdatedBy(username),
                        username);
    }
}
