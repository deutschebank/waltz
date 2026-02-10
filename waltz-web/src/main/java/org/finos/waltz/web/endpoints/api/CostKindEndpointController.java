/*
 * Waltz - Enterprise Architecture
 * Copyright (C) 2016, 2017, 2018, 2019 Waltz open source project
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
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.finos.waltz.web.endpoints.api;

import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.IdSelectionOptions;
import org.finos.waltz.model.cost.CostKindWithYears;
import org.finos.waltz.service.cost_kind.CostKindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api/cost-kind")
public class CostKindEndpointController {

    private final CostKindService costKindService;


    @Autowired
    public CostKindEndpointController(CostKindService costKindService) {
        this.costKindService = costKindService;
    }


    @GetMapping("")
    public Set<CostKindWithYears> findAll() {
        return costKindService.findAll();
    }

    @GetMapping("/subject-kind/{kind}")
    public Set<CostKindWithYears> findBySubjectKind(@PathVariable("kind") EntityKind kind) {
        return costKindService.findCostKindsBySubjectKind(kind);
    }

    @PostMapping("/target-kind/{kind}/selector")
    public Set<CostKindWithYears> findCostKindsBySelector(@PathVariable("kind") EntityKind targetKind,
                                                           @RequestBody IdSelectionOptions selectionOptions) {
        return costKindService.findCostKindsSelectorRoute(targetKind, selectionOptions);
    }
}
