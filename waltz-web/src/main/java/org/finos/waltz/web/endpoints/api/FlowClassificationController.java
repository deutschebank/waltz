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


import org.finos.waltz.service.flow_classification_rule.FlowClassificationService;
import org.finos.waltz.model.flow_classification.FlowClassification;
import org.finos.waltz.web.WebUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

import static org.finos.waltz.common.Checks.checkNotNull;


@RestController
@RequestMapping("/api/flow-classification/")
public class FlowClassificationController {

    private final FlowClassificationService flowClassificationService;

    @Autowired
    public FlowClassificationController(FlowClassificationService flowClassificationService) {
        checkNotNull(flowClassificationService, "flowClassificationService must not be null");
        this.flowClassificationService = flowClassificationService;
    }

    @GetMapping
    public Set<FlowClassification> findAll() {
        return flowClassificationService.findAll();
    }
    @GetMapping("/id/{id}")
    public FlowClassification getById(@PathVariable long id) {
        return flowClassificationService.getById(id);
    }
}
