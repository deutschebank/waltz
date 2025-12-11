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


import org.finos.waltz.common.exception.InsufficientPrivelegeException;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.allocation.Allocation;
import org.finos.waltz.model.allocation.MeasurablePercentageChange;
import org.finos.waltz.service.allocation.AllocationService;
import org.finos.waltz.service.user.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.model.EntityReference.mkRef;
import static org.finos.waltz.web.WebUtilities.*;


@RestController
@RequestMapping("/api/allocation/")
public class AllocationEndpointController {

    public static final String BASE_URL = mkPath("api", "allocation");

    private final AllocationService allocationService;
    private final UserRoleService userRoleService;


    @Autowired
    public AllocationEndpointController(AllocationService allocationService,
                              UserRoleService userRoleService) {
        checkNotNull(allocationService, "allocationService cannot be null");
        checkNotNull(userRoleService, "userRoleService cannot be null");

        this.allocationService = allocationService;
        this.userRoleService = userRoleService;
    }

    @GetMapping("entity-ref/{kind}/{id}")
    public Collection<Allocation> findByEntity(@PathVariable("kind") String kind,
                                               @PathVariable("id") long id) {
        return allocationService.findByEntity(mkRef(EntityKind.valueOf(kind), id));
    }

    @GetMapping("entity-ref/{kind}/{id}/{schemeId}")
    public List<Allocation> findByEntityAndScheme(@PathVariable("kind") String kind,
                                                  @PathVariable("id") long id,
                                                  @PathVariable("schemeId") long schemeId) {
        return allocationService.findByEntityAndScheme(
                mkRef(EntityKind.valueOf(kind), id),
                schemeId);
    }

    @GetMapping("measurable/{measurableId}/{schemeId}")
    public List<Allocation> findByMeasurableAndScheme(@PathVariable("measurableId") long measurableId,
                                                      @PathVariable("schemeId") long schemeId) {
        return allocationService.findByMeasurableAndScheme(measurableId, schemeId);
    }

    @PostMapping("entity-ref/{kind}/{id}/{schemeId}/allocations")
    public boolean updateAllocations(@PathVariable("kind") String kind,
                                     @PathVariable("id") long id,
                                     @PathVariable("schemeId") long schemeId,
                                     @RequestBody MeasurablePercentageChange[] percentages,
                                     Principal principal) throws InsufficientPrivelegeException {

        EntityReference parentRef = mkRef(EntityKind.valueOf(kind), id);
        String username = principal.getName();

        allocationService.checkHasEditPermission(parentRef, schemeId, username);

        return allocationService
                .updateAllocations(
                        parentRef,
                        schemeId,
                        Arrays.asList(percentages),
                        username);
    }

}
