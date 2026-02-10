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

import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.entity_search.EntitySearchOptions;
import org.finos.waltz.model.entity_search.ImmutableEntitySearchOptions;
import org.finos.waltz.service.entity_search.EntitySearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

import static org.finos.waltz.common.Checks.checkNotNull;

@RestController
@RequestMapping("/api/entity-search")
public class EntitySearchEndpointController {

    private final EntitySearchService entitySearchService;

    @Autowired
    public EntitySearchEndpointController(EntitySearchService entitySearchService) {
        checkNotNull(entitySearchService, "entitySearchService cannot be null");
        this.entitySearchService = entitySearchService;
    }

    @PostMapping
    public List<EntityReference> search(@RequestBody EntitySearchOptions entitySearchOptions, Principal principal) {
        return entitySearchService.search(ImmutableEntitySearchOptions.copyOf(entitySearchOptions).withUserId(principal.getName()));
    }
}