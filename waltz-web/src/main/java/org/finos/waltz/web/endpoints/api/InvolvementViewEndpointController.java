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
import org.finos.waltz.model.involvement.InvolvementDetail;
import org.finos.waltz.model.involvement.InvolvementDetailByDirectionResults;
import org.finos.waltz.model.involvement.InvolvementViewItem;
import org.finos.waltz.service.involvement.InvolvementViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

import static org.finos.waltz.model.EntityReference.mkRef;

@RestController
@RequestMapping("/api/involvement-view")
public class InvolvementViewEndpointController {

    private final InvolvementViewService involvementViewService;


    @Autowired
    public InvolvementViewEndpointController(InvolvementViewService involvementViewService) {
        this.involvementViewService = involvementViewService;
    }

    @GetMapping("/employee/{employeeId}")
    public Set<InvolvementViewItem> findAllByEmployeeId(@PathVariable String employeeId) {
        return involvementViewService.findAllByEmployeeId(employeeId);
    }

    @GetMapping("/entity/kind/{kind}/id/{id}/all-by-direction")
    public InvolvementDetailByDirectionResults findAllInvolvementsForEntity(@PathVariable String kind, @PathVariable long id) {
        EntityReference ref = mkRef(EntityKind.valueOf(kind.toUpperCase()), id);
        return involvementViewService.getAllInvolvements(ref);
    }

    @GetMapping("/entity/kind/{kind}/id/{id}/key")
    public Set<InvolvementDetail> findKeyInvolvementsForEntity(@PathVariable String kind, @PathVariable long id) {
        EntityReference ref = mkRef(EntityKind.valueOf(kind.toUpperCase()), id);
        return involvementViewService.findKeyInvolvementsForEntity(ref);
    }

    @GetMapping("/involvement-kind/{id}/entity-kind/{kind}")
    public Set<InvolvementViewItem> findInvolvementsByKindAndEntityKind(@PathVariable long id, @PathVariable String kind) {
        return involvementViewService.findByKindIdAndEntityKind(id, EntityKind.valueOf(kind.toUpperCase()));
    }
}
