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
import org.finos.waltz.model.assessment_definition.AssessmentDefinition;
import org.finos.waltz.model.assessment_definition.AssessmentRipplerJobConfiguration;
import org.finos.waltz.model.assessment_rating.*;
import org.finos.waltz.model.assessment_rating.bulk_upload.AssessmentRatingValidationResult;
import org.finos.waltz.model.assessment_rating.bulk_upload.BulkAssessmentRatingApplyResult;
import org.finos.waltz.model.rating.RatingSchemeItem;
import org.finos.waltz.model.user.SystemRole;
import org.finos.waltz.service.assessment_definition.AssessmentDefinitionService;
import org.finos.waltz.service.assessment_rating.AssessmentRatingService;
import org.finos.waltz.service.assessment_rating.BulkAssessmentRatingService;
import org.finos.waltz.service.rating_scheme.RatingSchemeService;
import org.finos.waltz.service.user.UserRoleService;
import org.finos.waltz.web.NotAuthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.model.EntityReference.mkRef;
import static org.finos.waltz.web.WebUtilities.*;


@RestController
@RequestMapping("/api/assessment-rating/")
public class AssessmentRatingEndpointController {

    public static final String BASE_URL = mkPath("api", "assessment-rating");

    private final AssessmentRatingService assessmentRatingService;
    private final AssessmentDefinitionService assessmentDefinitionService;
    private final UserRoleService userRoleService;
    private final BulkAssessmentRatingService bulkAssessmentRatingService;
    private final RatingSchemeService ratingSchemeService;

    @Autowired
    public AssessmentRatingEndpointController(AssessmentRatingService assessmentRatingService,
                                    AssessmentDefinitionService assessmentDefinitionService,
                                    UserRoleService userRoleService,
                                    BulkAssessmentRatingService bulkAssessmentRatingService,
                                    RatingSchemeService ratingSchemeService) {

        checkNotNull(assessmentRatingService, "assessmentRatingService cannot be null");
        checkNotNull(assessmentDefinitionService, "assessmentDefinitionService cannot be null");
        checkNotNull(userRoleService, "userRoleService cannot be null");
        checkNotNull(bulkAssessmentRatingService, "bulkAssessmentRatingService cannot be null");
        checkNotNull(ratingSchemeService, "ratingSchemeService cannot be null");

        this.assessmentRatingService = assessmentRatingService;
        this.assessmentDefinitionService = assessmentDefinitionService;
        this.userRoleService = userRoleService;
        this.bulkAssessmentRatingService = bulkAssessmentRatingService;
        this.ratingSchemeService = ratingSchemeService;
    }

    @GetMapping("entity/{kind}/{id}")
    public List<AssessmentRating> findForEntity(@PathVariable("kind") String kind, @PathVariable("id") long id) {
        return assessmentRatingService.findForEntity(mkRef(EntityKind.valueOf(kind.toUpperCase()), id));
    }

    @GetMapping("entity-kind/{kind}")
    public List<AssessmentRating> findByEntityKind(@PathVariable("kind") String kind) {
        return assessmentRatingService.findByEntityKind(EntityKind.valueOf(kind.toUpperCase()));
    }

    @GetMapping("definition-id/{assessmentDefinitionId}")
    public List<AssessmentRating> findByDefinitionId(@PathVariable long assessmentDefinitionId) {
        return assessmentRatingService.findByDefinitionId(assessmentDefinitionId);
    }

    @PostMapping("target-kind/{targetKind}/summary-counts")
    public Set<AssessmentRatingSummaryCounts> findSummaryCounts(
            @PathVariable("targetKind") String targetKind,
            @RequestBody SummaryCountRequest summaryCountRequest) {
        return assessmentRatingService.findRatingSummaryCounts(
                EntityKind.valueOf(targetKind.toUpperCase()),
                summaryCountRequest.idSelectionOptions(),
                summaryCountRequest.definitionIds());
    }

    @GetMapping("entity/{kind}/{id}/{assessmentDefinitionId}/permissions")
    public Set<AssessmentRatingOperations> findRatingPermissions(
            @PathVariable("kind") String kind,
            @PathVariable("id") long id,
            @PathVariable long assessmentDefinitionId,
            HttpServletRequest httpServletRequest) {
        return assessmentRatingService
                .getRatingPermissions(
                        mkRef(EntityKind.valueOf(kind.toUpperCase()), id),
                        assessmentDefinitionId,
                        getUsernameForSB(httpServletRequest))
                .ratingOperations();
    }

    @GetMapping("definition-id/{assessmentDefinitionId}/mva-check")
    public boolean hasMultiValuedAssessments(@PathVariable long assessmentDefinitionId) {
        return assessmentRatingService.hasMultiValuedAssessments(assessmentDefinitionId);
    }

    @PostMapping("target-kind/{targetKind}/selector")
    public List<AssessmentRating> findByTargetKindForRelatedSelector(
            @PathVariable("targetKind") String targetKind,
            @RequestBody IdSelectionOptions selectionOptions) {
        return assessmentRatingService.findByTargetKindForRelatedSelector(
                EntityKind.valueOf(targetKind.toUpperCase()),
                selectionOptions);
    }

    @PostMapping("bulk-update/{assessmentDefinitionId}")
    public boolean bulkStore(
            @PathVariable long assessmentDefinitionId,
            @RequestBody BulkAssessmentRatingCommand[] commands,
            HttpServletRequest principal) {
        verifyCanWrite(principal, assessmentDefinitionId);
        return assessmentRatingService.bulkStore(commands, assessmentDefinitionId, getUsernameForSB(principal));
    }

    @PostMapping("bulk-remove/{assessmentDefinitionId}")
    public boolean bulkRemove(
            @PathVariable long assessmentDefinitionId,
            @RequestBody BulkAssessmentRatingCommand[] commands,
            HttpServletRequest httpServletRequest) {
        verifyCanWrite(httpServletRequest, assessmentDefinitionId);
        return assessmentRatingService.bulkDelete(commands, assessmentDefinitionId, getUsernameForSB(httpServletRequest));
    }

    @PostMapping("entity/{kind}/{id}/{assessmentDefinitionId}")
    public boolean store(
            @PathVariable("kind") String kind,
            @PathVariable("id") long id,
            @PathVariable long assessmentDefinitionId,
            @RequestBody SaveAssessmentRatingCommand command,
            HttpServletRequest principal) throws InsufficientPrivelegeException {
        ratingItemUserSelectableOrThrow(command.ratingId());
        SaveAssessmentRatingCommand fullCommand = ImmutableSaveAssessmentRatingCommand
                .copyOf(command)
                .withEntityReference(mkRef(EntityKind.valueOf(kind.toUpperCase()), id))
                .withAssessmentDefinitionId(assessmentDefinitionId);
        return assessmentRatingService.store(fullCommand, getUsernameForSB(principal));
    }

    @PostMapping("id/{id}/update-comment")
    public boolean updateComment(
            @PathVariable("id") long ratingId,
            @RequestBody String comment,
            HttpServletRequest principal) throws InsufficientPrivelegeException {
        return assessmentRatingService.updateComment(ratingId, comment, getUsernameForSB(principal));
    }

    @PostMapping("id/{id}/update-rating")
    public boolean updateRating(
            @PathVariable("id") long assessmentRatingId,
            @RequestBody UpdateRatingCommand command,
            HttpServletRequest request) throws InsufficientPrivelegeException {
        ratingItemUserSelectableOrThrow(command.newRatingId());
        return assessmentRatingService.updateRating(assessmentRatingId, command, getUsernameForSB(request));
    }

    @PutMapping("entity/{kind}/{id}/{assessmentDefinitionId}/{ratingId}/lock")
    public boolean lock(
            @PathVariable("kind") String kind,
            @PathVariable("id") long id,
            @PathVariable long assessmentDefinitionId,
            @PathVariable long ratingId,
            HttpServletRequest httpServletRequest) throws InsufficientPrivelegeException {
        return assessmentRatingService.lock(
                mkRef(EntityKind.valueOf(kind.toUpperCase()), id),
                assessmentDefinitionId,
                ratingId,
                getUsernameForSB(httpServletRequest));
    }

    @PutMapping("entity/{kind}/{id}/{assessmentDefinitionId}/{ratingId}/unlock")
    public boolean unlock(
            @PathVariable("kind") String kind,
            @PathVariable("id") long id,
            @PathVariable long assessmentDefinitionId,
            @PathVariable long ratingId,
            HttpServletRequest httpServletRequest) throws InsufficientPrivelegeException {
        return assessmentRatingService.unlock(
                mkRef(EntityKind.valueOf(kind.toUpperCase()), id),
                assessmentDefinitionId,
                ratingId,
                getUsernameForSB(httpServletRequest));
    }

    @DeleteMapping("entity/{kind}/{id}/{assessmentDefinitionId}/{ratingId}")
    public boolean remove(
            @PathVariable("kind") String kind,
            @PathVariable("id") long id,
            @PathVariable long assessmentDefinitionId,
            @PathVariable long ratingId,
            HttpServletRequest principal) throws InsufficientPrivelegeException {
        RemoveAssessmentRatingCommand command = ImmutableRemoveAssessmentRatingCommand.builder()
                .entityReference(mkRef(EntityKind.valueOf(kind.toUpperCase()), id))
                .assessmentDefinitionId(assessmentDefinitionId)
                .ratingId(ratingId)
                .build();
        return assessmentRatingService.remove(command, getUsernameForSB(principal));
    }

    @PostMapping("ripple/all")
    public Long rippleAll(HttpServletRequest principal) {
        requireRoleForSB(userRoleService, principal, SystemRole.ADMIN);
        return assessmentRatingService.rippleAll();
    }

    @GetMapping("ripple/config")
    public Set<AssessmentRipplerJobConfiguration> rippleConfig() {
        return assessmentRatingService.findRippleConfig();
    }

    @PostMapping("bulk/preview/ASSESSMENT_DEFINITION/{id}")
    public AssessmentRatingValidationResult previewBulkAssessmentRatingChanges(
            @PathVariable("id") long assessmentDefId,
            @RequestBody String body) {
        EntityReference assessmentDefRef = mkRef(EntityKind.ASSESSMENT_DEFINITION, assessmentDefId);
        return bulkAssessmentRatingService.bulkPreview(assessmentDefRef, body);
    }

    @PostMapping("bulk/apply/ASSESSMENT_DEFINITION/{id}")
    public BulkAssessmentRatingApplyResult applyBulkAssessmentRatingChanges(
            @PathVariable("id") long assessmentDefId,
            @RequestBody String body,
            HttpServletRequest httpServletRequest) throws IOException {
        EntityReference assessmentDefRef = mkRef(EntityKind.ASSESSMENT_DEFINITION, assessmentDefId);
        AssessmentRatingValidationResult preview = bulkAssessmentRatingService.bulkPreview(assessmentDefRef, body);
        return bulkAssessmentRatingService.apply(assessmentDefRef, preview, getUsernameForSB(httpServletRequest));
    }

    private void verifyCanWrite(HttpServletRequest httpServletRequest, long defId) {
        AssessmentDefinition def = assessmentDefinitionService.getById(defId);
        def.permittedRole().ifPresent(r -> requireRoleForSB(userRoleService, httpServletRequest, r));
        if (def.isReadOnly()) {
            throw new NotAuthorizedException("Assessment is read-only");
        }
    }

    private void ratingItemUserSelectableOrThrow(Long itemId) {
        RatingSchemeItem ratingSchemeItem = ratingSchemeService.getRatingSchemeItemById(itemId);
        if (!ratingSchemeItem.userSelectable()) {
            throw new IllegalArgumentException("Rating not user selectable");
        }
    }

}
