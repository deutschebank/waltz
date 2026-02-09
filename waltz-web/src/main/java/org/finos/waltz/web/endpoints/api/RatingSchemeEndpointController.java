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

import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.rating.RatingScheme;
import org.finos.waltz.model.rating.RatingSchemeItem;
import org.finos.waltz.model.rating.RatingSchemeItemUsageCount;
import org.finos.waltz.model.user.SystemRole;
import org.finos.waltz.service.rating_scheme.RatingSchemeService;
import org.finos.waltz.service.user.UserRoleService;
import org.finos.waltz.web.WebUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.web.WebUtilities.mkPath;
import static org.finos.waltz.web.WebUtilities.requireAnyRole;


@RestController
@RequestMapping(RatingSchemeEndpointController.BASE_URL)
public class RatingSchemeEndpointController {

    public static final String BASE_URL = "/api/rating-scheme/";

    private final RatingSchemeService ratingSchemeService;
    private final UserRoleService userRoleService;


    @Autowired
    public RatingSchemeEndpointController(RatingSchemeService ratingSchemeService,
                                        UserRoleService userRoleService) {
        checkNotNull(ratingSchemeService, "ratingSchemeService cannot be null");
        checkNotNull(userRoleService, "userRoleService cannot be null");
        this.ratingSchemeService = ratingSchemeService;
        this.userRoleService = userRoleService;
    }

    @GetMapping()
    public Collection<RatingScheme> findAll() {
        return ratingSchemeService.findAll();
    }

    @GetMapping("/id/{id}")
    public RatingScheme getById(@PathVariable("id") long id) {
        return ratingSchemeService.getById(id);
    }

    @GetMapping("/items")
    public Collection<RatingSchemeItem> findAllRatingSchemeItems() {
        return ratingSchemeService.findAllRatingSchemeItems();
    }

    @GetMapping("/items/kind/{kind}/id/{id}/category-id/{categoryId}")
    public Collection<RatingSchemeItem> findRatingSchemeItemsForEntityAndCategory(
            @PathVariable("kind") String kind,
            @PathVariable("id") long id,
            @PathVariable("categoryId") long categoryId) {
        EntityReference ref = EntityReference.mkRef(EntityKind.valueOf(kind), id);
        return ratingSchemeService.findRatingSchemeItemsForEntityAndCategory(ref, categoryId);
    }

    @GetMapping("/items/assessment-definition-id/{id}")
    public Collection<RatingSchemeItem> findRatingSchemeItemsByAssessmentDefinition(@PathVariable("id") long id) {
        return ratingSchemeService.findRatingSchemeItemsByAssessmentDefinition(id);
    }

    @GetMapping("/items/usage")
    public List<RatingSchemeItemUsageCount> calcRatingUsageStats() {
        return ratingSchemeService.calcRatingUsageStats();
    }

    @PutMapping()
    public boolean saveScheme(@RequestBody RatingScheme scheme) {
        return ratingSchemeService.save(scheme);
    }

    @PutMapping("/id/{id}/rating-item")
    public long saveRatingItem(@PathVariable("id") long schemeId, @RequestBody RatingSchemeItem item) {
        return ratingSchemeService.saveRatingItem(schemeId, item);
    }

    @DeleteMapping("/id/{id}")
    public boolean removeRatingScheme(@PathVariable("id") long id) {
        return ratingSchemeService.removeRatingScheme(id);
    }

    @DeleteMapping("/items/id/{id}")
    public boolean removeRatingItem(@PathVariable("id") long id) {
        return ratingSchemeService.removeRatingItem(id);
    }



}
