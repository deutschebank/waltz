/*
 * Waltz - Enterprise Architecture
 * Copyright (C) 2016, 2017, 2018, 2019, 2024 Waltz open source project
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

import org.finos.waltz.service.entity_svg_diagram.EntitySvgDiagramService;
import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.entity_svg_diagram.EntitySvgDiagram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static org.finos.waltz.common.Checks.checkNotNull;


@RestController
@RequestMapping("/api/entity-svg-diagram")
public class EntitySvgDiagramController {

    private final EntitySvgDiagramService entitySvgDiagramService;


    @Autowired
    public EntitySvgDiagramController(EntitySvgDiagramService entitySvgDiagramService) {
        checkNotNull(entitySvgDiagramService, "entitySvgDiagramService cannot be null");
        this.entitySvgDiagramService = entitySvgDiagramService;
    }


    @GetMapping("/entity-ref/{kind}/{id}")
    public Collection<EntitySvgDiagram> findByEntity(
            @PathVariable("kind") String kind,
            @PathVariable("id") long id) {
        EntityReference ref = EntityReference.mkRef(EntityKind.valueOf(kind.toUpperCase()), id);
        return entitySvgDiagramService.findForEntityReference(ref);
    }
}
