/*
 * Waltz - Enterprise Architecture.
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
import org.finos.waltz.model.IdSelectionOptions;
import org.finos.waltz.model.ImmutableEntityReference;
import org.finos.waltz.model.complexity.Complexity;
import org.finos.waltz.model.complexity.ComplexitySummary;
import org.finos.waltz.model.complexity.ComplexityTotal;
import org.finos.waltz.service.complexity.ComplexityService;
import org.finos.waltz.web.WebUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

import static org.finos.waltz.web.WebUtilities.mkPath;


@RestController
@RequestMapping("/api/complexity/")
public class ComplexityEndpointController {

    private final ComplexityService complexityService;


    @Autowired
    public ComplexityEndpointController(ComplexityService complexityService) {
        this.complexityService = complexityService;
    }


    @GetMapping("entity/kind/{kind}/id/{id}")
    public Set<Complexity> findByEntityReference(@PathVariable("kind") EntityKind kind,
                                                 @PathVariable("id") long id) {
        EntityReference ref = ImmutableEntityReference.builder()
                .kind(kind)
                .id(id)
                .build();
        return complexityService.findByEntityReference(ref);
    }


    @PostMapping("complexity/target-kind/{kind}/totals")
    public Set<ComplexityTotal> findTotalsByTargetKindAndSelector(@PathVariable("kind") EntityKind kind,
                                                                   @RequestBody IdSelectionOptions selectionOptions) {
        return complexityService.findTotalsByTargetKindAndSelector(kind, selectionOptions);
    }


    @PostMapping("complexity/target-kind/{kind}")
    public Set<Complexity> findBySelector(@PathVariable("kind") EntityKind kind,
                                           @RequestBody IdSelectionOptions selectionOptions) {
        return complexityService.findBySelector(kind, selectionOptions);
    }

    @PostMapping("complexity/complexity-kind/{id}/target-kind/{kind}")
    public ComplexitySummary getComplexitySummaryForSelector(@PathVariable("id") long complexityKindId,
                                                             @PathVariable("kind") EntityKind targetKind,
                                                             @RequestBody IdSelectionOptions selectionOptions,
                                                             @RequestParam(value = "limit", defaultValue = "15", required = false) Integer limit) {
        return complexityService.getComplexitySummaryForSelector(complexityKindId, targetKind, selectionOptions, limit);
    }
}
