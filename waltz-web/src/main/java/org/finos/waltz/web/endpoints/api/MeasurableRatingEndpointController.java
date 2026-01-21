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
import org.finos.waltz.model.IdSelectionOptions;
import org.finos.waltz.model.Operation;
import org.finos.waltz.model.UserTimestamp;
import org.finos.waltz.model.application.MeasurableRatingsView;
import org.finos.waltz.model.measurable_rating.ImmutableRemoveMeasurableRatingCommand;
import org.finos.waltz.model.measurable_rating.MeasurableRating;
import org.finos.waltz.model.measurable_rating.MeasurableRatingCategoryView;
import org.finos.waltz.model.measurable_rating.MeasurableRatingStatParams;
import org.finos.waltz.model.measurable_rating.MeasurableRatingView;
import org.finos.waltz.model.measurable_rating.RemoveMeasurableRatingCommand;
import org.finos.waltz.model.tally.MeasurableRatingTally;
import org.finos.waltz.model.tally.Tally;
import org.finos.waltz.service.measurable_rating.BulkMeasurableItemParser.InputFormat;
import org.finos.waltz.service.measurable_rating.MeasurableRatingService;
import org.finos.waltz.service.measurable_rating.MeasurableRatingViewService;
import org.finos.waltz.service.permission.permission_checker.MeasurableRatingPermissionChecker;
import org.finos.waltz.service.user.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collection;
import java.util.Set;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.common.FunctionUtilities.time;
import static org.finos.waltz.common.SetUtilities.asSet;
import static org.finos.waltz.model.EntityReference.mkRef;
import static org.finos.waltz.web.WebUtilities.*;

@RestController
@RequestMapping("/api/measurable-rating")
public class MeasurableRatingEndpointController {

    private final MeasurableRatingService measurableRatingService;
    private final MeasurableRatingViewService measurableRatingViewService;
    private final MeasurableRatingPermissionChecker measurableRatingPermissionChecker;
    private final UserRoleService userRoleService;


    @Autowired
    public MeasurableRatingEndpointController(MeasurableRatingService measurableRatingService,
                                              MeasurableRatingViewService measurableRatingViewService,
                                              MeasurableRatingPermissionChecker measurableRatingPermissionChecker,
                                              UserRoleService userRoleService) {

        checkNotNull(measurableRatingService, "measurableRatingService cannot be null");
        checkNotNull(userRoleService, "userRoleService cannot be null");
        checkNotNull(measurableRatingViewService, "measurableRatingViewService cannot be null");
        checkNotNull(measurableRatingPermissionChecker, "measurableRatingPermissionChecker cannot be null");

        this.measurableRatingService = measurableRatingService;
        this.measurableRatingPermissionChecker = measurableRatingPermissionChecker;
        this.measurableRatingViewService = measurableRatingViewService;
        this.userRoleService = userRoleService;
    }


    @GetMapping("/id/{id}")
    public MeasurableRating getById(@PathVariable("id") long id) {
        return measurableRatingService.getById(id);
    }

    @GetMapping("/id/{id}/view")
    public MeasurableRatingView getViewById(@PathVariable("id") long id) {
        return measurableRatingViewService.getViewById(id);
    }

    @GetMapping("/entity/{kind}/{id}")
    public Collection<MeasurableRating> findForEntity(@PathVariable("kind") String kind, @PathVariable("id") long id) {
        return measurableRatingService.findForEntity(mkRef(EntityKind.valueOf(kind), id));
    }

    @PostMapping("/measurable-selector")
    public Collection<MeasurableRating> findByMeasurableSelector(@RequestBody IdSelectionOptions options) {
        return measurableRatingService.findByMeasurableIdSelector(options);
    }

    @PostMapping("/app-selector")
    public Collection<MeasurableRating> findByAppSelector(@RequestBody IdSelectionOptions options) {
        return measurableRatingService.findByAppIdSelector(options);
    }

    @PostMapping("/category/{id}/view")
    public MeasurableRatingCategoryView getViewForCategoryAndAppSelector(@PathVariable("id") long categoryId,
                                                                         @RequestBody IdSelectionOptions options) {
        return time("viewForCatAndSelector", () -> measurableRatingViewService.getViewForCategoryAndSelector(options, categoryId));
    }

    @PostMapping("/primary-ratings/view")
    public MeasurableRatingsView getPrimaryRatingsViewForAppSelector(@RequestBody IdSelectionOptions options) {
        return measurableRatingViewService.getPrimaryRatingsView(options);
    }

    @GetMapping("/category/{id}")
    public Collection<MeasurableRating> findByCategory(@PathVariable("id") long id) {
        return measurableRatingService.findByCategory(id);
    }

    @GetMapping("/count-by/measurable/category/{id}")
    public Collection<Tally<Long>> countByMeasurableCategory(@PathVariable("id") long id) {
        return measurableRatingService.tallyByMeasurableCategoryId(id);
    }

    @PostMapping("/stats-by/app-selector")
    public Collection<MeasurableRatingTally> statsByAppSelector(@RequestBody MeasurableRatingStatParams params) {
        return measurableRatingService.statsByAppSelector(params);
    }

    @PostMapping("/has-measurable-ratings")
    public boolean hasMeasurableRatings(@RequestBody IdSelectionOptions options) {
        return measurableRatingService.hasMeasurableRatings(options);
    }

    @PostMapping("/entity/{kind}/{id}/measurable/{measurableId}/rating")
    public Collection<MeasurableRating> saveRatingItem(@PathVariable("kind") String kind,
                                                       @PathVariable("id") long id,
                                                       @PathVariable("measurableId") long measurableId,
                                                       @RequestBody String ratingCode,
                                                       HttpServletRequest principal) throws InsufficientPrivelegeException {
        EntityReference entityRef = mkRef(EntityKind.valueOf(kind), id);
        String username = getUsernameForSB(principal);
        checkHasPermissionForThisOperation(entityRef, measurableId, asSet(Operation.UPDATE), username);
        measurableRatingService.saveRatingItem(entityRef, measurableId, ratingCode, username);
        return measurableRatingService.findForEntity(entityRef);
    }

    @PostMapping("/entity/{kind}/{id}/measurable/{measurableId}/description")
    public Collection<MeasurableRating> saveRatingDescription(@PathVariable("kind") String kind,
                                                              @PathVariable("id") long id,
                                                              @PathVariable("measurableId") long measurableId,
                                                              @RequestBody String description,
                                                              HttpServletRequest principal) throws InsufficientPrivelegeException {
        EntityReference entityRef = mkRef(EntityKind.valueOf(kind), id);
        String username = getUsernameForSB(principal);
        checkHasPermissionForThisOperation(entityRef, measurableId, asSet(Operation.UPDATE), username);
        measurableRatingService.saveRatingDescription(entityRef, measurableId, description, username);
        return measurableRatingService.findForEntity(entityRef);
    }

    @PostMapping("/entity/{kind}/{id}/measurable/{measurableId}/is-primary")
    public Collection<MeasurableRating> saveRatingIsPrimary(@PathVariable("kind") String kind,
                                                            @PathVariable("id") long id,
                                                            @PathVariable("measurableId") long measurableId,
                                                            @RequestBody boolean isPrimary,
                                                            HttpServletRequest principal) throws InsufficientPrivelegeException {
        EntityReference entityRef = mkRef(EntityKind.valueOf(kind), id);
        String username = getUsernameForSB(principal);
        checkHasPermissionForThisOperation(entityRef, measurableId, asSet(Operation.UPDATE), username);
        measurableRatingService.saveRatingIsPrimary(entityRef, measurableId, isPrimary, username);
        return measurableRatingService.findForEntity(entityRef);
    }

    @DeleteMapping("/entity/{kind}/{id}/category/{categoryId}")
    public Collection<MeasurableRating> removeCategory(@PathVariable("kind") String kind,
                                                       @PathVariable("id") long id,
                                                       @PathVariable("categoryId") long categoryId,
                                                       HttpServletRequest principal) {
        EntityReference ref = mkRef(EntityKind.valueOf(kind), id);
        // requireRole(userRoleService, request, measurableRatingService.getRequiredRatingEditRole(mkRef(EntityKind.MEASURABLE_CATEGORY, categoryId)));
        // Above line needs HttpServletRequest, for now assuming role check is handled elsewhere or to be added
        return measurableRatingService.removeForCategory(ref, categoryId, getUsernameForSB(principal));
    }

    @DeleteMapping("/entity/{kind}/{id}/measurable/{measurableId}")
    public Collection<MeasurableRating> remove(@PathVariable("kind") String kind,
                                               @PathVariable("id") long id,
                                               @PathVariable("measurableId") long measurableId,
                                               HttpServletRequest principal) throws InsufficientPrivelegeException {
        String username = getUsernameForSB(principal);
        EntityReference parentReference = mkRef(EntityKind.valueOf(kind), id);
        checkHasPermissionForThisOperation(parentReference, measurableId, asSet(Operation.REMOVE), username);
        RemoveMeasurableRatingCommand command = ImmutableRemoveMeasurableRatingCommand.builder()
                .entityReference(parentReference)
                .measurableId(measurableId)
                .lastUpdate(UserTimestamp.mkForUser(username))
                .build();
        return measurableRatingService.remove(command);
    }

    private void checkHasPermissionForThisOperation(EntityReference parentRef,
                                                    Long measurableId,
                                                    Set<Operation> operations,
                                                    String username) throws InsufficientPrivelegeException {
        Set<Operation> perms = measurableRatingPermissionChecker.findMeasurableRatingPermissions(parentRef, measurableId, username);
        measurableRatingPermissionChecker.verifyAnyPerms(operations, perms, EntityKind.MEASURABLE_RATING, username);
    }

}
