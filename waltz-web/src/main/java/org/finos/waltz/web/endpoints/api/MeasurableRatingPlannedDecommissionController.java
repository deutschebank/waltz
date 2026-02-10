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

import jakarta.servlet.http.HttpServletRequest;
import org.finos.waltz.common.exception.InsufficientPrivelegeException;
import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.Operation;
import org.finos.waltz.model.command.DateFieldChange;
import org.finos.waltz.model.measurable_rating_planned_decommission.MeasurableRatingPlannedDecommission;
import org.finos.waltz.model.measurable_rating_planned_decommission.MeasurableRatingPlannedDecommissionInfo;
import org.finos.waltz.service.measurable_rating_planned_decommission.MeasurableRatingPlannedDecommissionService;
import org.finos.waltz.service.permission.permission_checker.MeasurableRatingPermissionChecker;
import org.finos.waltz.web.DatumRoute;
import org.finos.waltz.web.endpoints.auth.AuthenticationUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collection;
import java.util.Set;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.common.SetUtilities.asSet;
import static org.finos.waltz.model.EntityKind.MEASURABLE_RATING_PLANNED_DECOMMISSION;
import static org.finos.waltz.model.EntityReference.mkRef;

@RestController
@RequestMapping("api/measurable-rating-planned-decommission")
public class MeasurableRatingPlannedDecommissionController {

    private final MeasurableRatingPlannedDecommissionService measurableRatingPlannedDecommissionService;
    private final MeasurableRatingPermissionChecker measurableRatingPermissionChecker;

    @Autowired
    public MeasurableRatingPlannedDecommissionController(MeasurableRatingPlannedDecommissionService measurableRatingPlannedDecommissionService,
                                                       MeasurableRatingPermissionChecker measurableRatingPermissionChecker) {
        checkNotNull(measurableRatingPlannedDecommissionService, "measurableRatingPlannedDecommissionService cannot be null");
        checkNotNull(measurableRatingPermissionChecker, "measurableRatingPermissionChecker cannot be null");

        this.measurableRatingPlannedDecommissionService = measurableRatingPlannedDecommissionService;
        this.measurableRatingPermissionChecker = measurableRatingPermissionChecker;
    }

    @GetMapping("/entity/{kind}/{id}")
    public Collection<MeasurableRatingPlannedDecommission> findForEntity(
            @PathVariable String kind,
            @PathVariable long id) {
        EntityReference entityReference = mkRef(EntityKind.valueOf(kind), id);
        return measurableRatingPlannedDecommissionService.findForEntityRef(entityReference);
    }

    @GetMapping("/replacing-entity/{kind}/{id}")
    public Collection<MeasurableRatingPlannedDecommissionInfo> findForReplacingEntity(
            @PathVariable String kind,
            @PathVariable long id) {
        return measurableRatingPlannedDecommissionService.findForReplacingEntityRef(mkRef(EntityKind.valueOf(kind), id));
    }

    @PostMapping("/measurable-rating/{id}")
    public MeasurableRatingPlannedDecommission save(
            @PathVariable long id,
            @RequestBody DateFieldChange change,
            HttpServletRequest principal) throws InsufficientPrivelegeException {
        String username = AuthenticationUtilities.getUsernameForSB(principal);
        checkHasPermissionForThisOperation(id, asSet(Operation.ADD, Operation.UPDATE), username);
        return measurableRatingPlannedDecommissionService.save(id, change, username);
    }

    @DeleteMapping("/id/{id}")
    public Boolean remove(
            @PathVariable long id,
            HttpServletRequest principal) throws InsufficientPrivelegeException {
        String username = AuthenticationUtilities.getUsernameForSB(principal);
        MeasurableRatingPlannedDecommission decomm = measurableRatingPlannedDecommissionService.getById(id);
        checkHasPermissionForThisOperation(decomm.measurableRatingId(), asSet(Operation.REMOVE), username);
        return measurableRatingPlannedDecommissionService.remove(id, username);
    }

    private void checkHasPermissionForThisOperation(
            Long ratingId, Set<Operation> operations, String username) throws InsufficientPrivelegeException {

        Set<Operation> perms = measurableRatingPermissionChecker.findMeasurableRatingDecommPermissions(ratingId, username);
        measurableRatingPermissionChecker.verifyAnyPerms(operations, perms, MEASURABLE_RATING_PLANNED_DECOMMISSION, username);
    }
}
