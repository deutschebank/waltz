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
import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.Operation;
import org.finos.waltz.model.measurable_rating_replacement.MeasurableRatingReplacement;
import org.finos.waltz.service.measurable_rating_replacement.MeasurableRatingReplacementService;
import org.finos.waltz.service.permission.permission_checker.MeasurableRatingPermissionChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.common.SetUtilities.asSet;
import static org.finos.waltz.model.EntityReference.mkRef;

@RestController
@RequestMapping("/api/measurable-rating-replacement")
public class MeasurableRatingReplacementController {

    private final MeasurableRatingReplacementService measurableRatingReplacementService;
    private final MeasurableRatingPermissionChecker measurableRatingPermissionChecker;


    @Autowired
    public MeasurableRatingReplacementController(MeasurableRatingReplacementService measurableRatingReplacementService,
                                                 MeasurableRatingPermissionChecker measurableRatingPermissionChecker) {
        checkNotNull(measurableRatingReplacementService, "measurableRatingReplacementService cannot be null");
        checkNotNull(measurableRatingPermissionChecker, "measurableRatingPermissionChecker cannot be null");

        this.measurableRatingReplacementService = measurableRatingReplacementService;
        this.measurableRatingPermissionChecker = measurableRatingPermissionChecker;
    }


    @GetMapping("/entity/{kind}/{id}")
    public Collection<MeasurableRatingReplacement> findForEntity(
            @PathVariable("kind") EntityKind kind,
            @PathVariable("id") long id) {
        return measurableRatingReplacementService.findForEntityRef(mkRef(kind, id));
    }


    @PostMapping("/decomm-id/{decommId}/entity/{kind}/{id}")
    public Collection<MeasurableRatingReplacement> save(
            @PathVariable("decommId") long decommId,
            @PathVariable("kind") EntityKind kind,
            @PathVariable("id") long id,
            @RequestBody Date commissionDate,
            Principal principal) throws InsufficientPrivelegeException {

        checkHasPermissionForThisOperation(decommId, asSet(Operation.ADD, Operation.UPDATE), principal.getName());
        EntityReference entityReference = mkRef(kind, id);
        return measurableRatingReplacementService.save(decommId, entityReference, commissionDate, principal.getName());
    }


    @DeleteMapping("/decomm-id/{decommId}/replacement-id/{replacementId}")
    public Collection<MeasurableRatingReplacement> remove(
            @PathVariable("decommId") long decommId,
            @PathVariable("replacementId") long replacementId,
            Principal principal) throws InsufficientPrivelegeException {

        checkHasPermissionForThisOperation(decommId, asSet(Operation.REMOVE), principal.getName());
        return measurableRatingReplacementService.remove(decommId, replacementId, principal.getName());
    }


    private void checkHasPermissionForThisOperation(long decommId,
                                                    Set<Operation> operations,
                                                    String username) throws InsufficientPrivelegeException {

        Set<Operation> perms = measurableRatingPermissionChecker.findMeasurableRatingReplacementPermissions(decommId, username);
        measurableRatingPermissionChecker.verifyAnyPerms(operations, perms, EntityKind.MEASURABLE_RATING_REPLACEMENT, username);
    }
}