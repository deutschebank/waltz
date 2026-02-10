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


import org.finos.waltz.model.attribute_change.AttributeChange;
import org.finos.waltz.service.attribute_change.AttributeChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.web.WebUtilities.mkPath;


@RestController
@RequestMapping("/api/attribute-change/")
public class AttributeChangeEndpointController {

    private final AttributeChangeService service;


    @Autowired
    public AttributeChangeEndpointController(AttributeChangeService service) {
        checkNotNull(service, "service cannot be null");
        this.service = service;
    }


    @GetMapping("id/{id}")
    public AttributeChange getById(@PathVariable("id") long id) {
        return service.getById(id);
    }


    @GetMapping("change-unit/{id}")
    public List<AttributeChange> findByChangeUnitId(@PathVariable("id") long id) {
        return service.findByChangeUnitId(id);
    }
}