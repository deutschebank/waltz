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
import org.finos.waltz.model.IdSelectionOptions;
import org.finos.waltz.model.ImmutableEntityReference;
import org.finos.waltz.model.cost.EntityCost;
import org.finos.waltz.model.cost.EntityCostsSummary;
import org.finos.waltz.service.cost.CostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static org.finos.waltz.web.WebUtilities.mkPath;
import static org.finos.waltz.web.endpoints.EndpointUtilities.getForList;


@RestController
@RequestMapping("/api/cost/")
public class CostEndpointController {


    private final CostService costService;


    @Autowired
    public CostEndpointController(CostService costService) {
        this.costService = costService;
    }


    @RequestMapping("entity/kind/{kind}/id/{id}")
    public Collection<EntityCost> findByEntityReference(@PathVariable("kind") EntityKind kind,
                                                        @PathVariable("id") long id) {
        return costService.findByEntityReference(ImmutableEntityReference.builder()
                .kind(kind)
                .id(id)
                .build());
    }

    @PostMapping("target-kind/{kind}/year/{year}")
    public Collection<EntityCost> findBySelector(@RequestBody IdSelectionOptions idSelectionOptions,
                                                 @PathVariable("kind") EntityKind targetKind,
                                                 @PathVariable("year") int year) {
        return costService.findBySelector(idSelectionOptions, targetKind, year);
    }

    @PostMapping("cost-kind/{id}/target-kind/{kind}/summary/year/{year}")
    public EntityCostsSummary summariseByCostKindAndSelector(@RequestBody IdSelectionOptions idSelectionOptions,
                                                             @PathVariable("id") long costKindId,
                                                             @PathVariable("year") int year,
                                                             @PathVariable("kind") EntityKind targetKind,
                                                             @RequestParam(value = "limit", defaultValue = "15", required = false) int limit) {
        return costService.summariseByCostKindAndSelector(costKindId, idSelectionOptions, targetKind, year, limit);
    }
}
