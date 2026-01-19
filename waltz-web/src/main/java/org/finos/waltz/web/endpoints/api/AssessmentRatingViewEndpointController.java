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

import org.finos.waltz.service.assessment_rating.AssessmentRatingViewService;
import org.finos.waltz.model.assessment_rating.AssessmentGroupedEntities;
import org.finos.waltz.model.assessment_rating.AssessmentRatingDetail;
import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.web.WebUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.web.WebUtilities.mkPath;


@RestController
@RequestMapping("/api/assessment-rating-view")
public class AssessmentRatingViewEndpointController {

    private final AssessmentRatingViewService assessmentRatingViewService;


    @Autowired
    public AssessmentRatingViewEndpointController(AssessmentRatingViewService assessmentRatingViewService) {
        checkNotNull(assessmentRatingViewService, "assessmentRatingViewService cannot be null");
        this.assessmentRatingViewService = assessmentRatingViewService;
    }

    @PostMapping(value = "/kind/{kind}/grouped")
    public Collection<AssessmentGroupedEntities> findGroupedByDefinitionAndOutcome(
            @PathVariable("kind") EntityKind kind,
            @RequestBody List<Long> entityIds) {
        return assessmentRatingViewService.findGroupedByDefinitionAndOutcomes(kind, entityIds);
    }

    @GetMapping("/kind/{kind}/id/{id}")
    public Collection<AssessmentRatingDetail> findFavouriteAssessmentsForEntity(@PathVariable("kind") EntityKind kind, @PathVariable("id") long id, Principal principal) {
        return assessmentRatingViewService.findFavouriteAssessmentsForEntityAndUser(EntityReference.mkRef(kind, id), principal.getName());
    }
}
