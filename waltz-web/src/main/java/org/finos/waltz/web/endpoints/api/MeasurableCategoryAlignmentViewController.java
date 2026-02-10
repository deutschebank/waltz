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

import org.finos.waltz.service.measurable_category.MeasurableCategoryAlignmentViewService;
import org.finos.waltz.model.IdSelectionOptions;
import org.finos.waltz.model.measurable.MeasurableCategoryAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

import static org.finos.waltz.web.WebUtilities.mkPath;


@RestController
@RequestMapping(MeasurableCategoryAlignmentViewController.BASE_URL)
public class MeasurableCategoryAlignmentViewController {

    public static final String BASE_URL = "/api/measurable-category-alignment-view/";

    private final MeasurableCategoryAlignmentViewService measurableCategoryAlignmentViewService;


    @Autowired
    public MeasurableCategoryAlignmentViewController(MeasurableCategoryAlignmentViewService measurableCategoryAlignmentViewService) {
        this.measurableCategoryAlignmentViewService = measurableCategoryAlignmentViewService;
    }


    @PostMapping("selector")
    public Set<MeasurableCategoryAlignment> findAlignmentsByAppSelector(
            @RequestBody IdSelectionOptions idSelectionOptions) {
        return measurableCategoryAlignmentViewService.findAlignmentsByAppSelector(idSelectionOptions);
    }

}
