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

import org.finos.waltz.model.IdCommandResponse;
import org.finos.waltz.model.ReleaseLifecycleStatusChangeCommand;
import org.finos.waltz.model.aggregate_overlay_diagram.AggregateOverlayDiagram;
import org.finos.waltz.model.aggregate_overlay_diagram.AggregateOverlayDiagramInfo;
import org.finos.waltz.model.aggregate_overlay_diagram.OverlayDiagramSaveCommand;
import org.finos.waltz.model.aggregate_overlay_diagram.OverlayDiagramKind;
import org.finos.waltz.model.aggregate_overlay_diagram.OverlayDiagramPresetCreateCommand;
import org.finos.waltz.model.aggregate_overlay_diagram.overlay.AggregatedEntitiesWidgetData;
import org.finos.waltz.model.aggregate_overlay_diagram.overlay.ApplicationChangeWidgetData;
import org.finos.waltz.model.aggregate_overlay_diagram.overlay.AssessmentRatingsWidgetData;
import org.finos.waltz.model.aggregate_overlay_diagram.overlay.AttestationWidgetData;
import org.finos.waltz.model.aggregate_overlay_diagram.overlay.BackingEntityWidgetData;
import org.finos.waltz.model.aggregate_overlay_diagram.overlay.ComplexityWidgetData;
import org.finos.waltz.model.aggregate_overlay_diagram.overlay.CostWidgetData;
import org.finos.waltz.model.aggregate_overlay_diagram.overlay.CountWidgetData;
import org.finos.waltz.model.aggregate_overlay_diagram.overlay.TargetCostWidgetData;
import org.finos.waltz.model.aggregate_overlay_diagram.overlay.widget_parameters.AggregatedEntitiesWidgetParameters;
import org.finos.waltz.model.aggregate_overlay_diagram.overlay.widget_parameters.AppChangeWidgetParameters;
import org.finos.waltz.model.aggregate_overlay_diagram.overlay.widget_parameters.AppComplexityWidgetParameters;
import org.finos.waltz.model.aggregate_overlay_diagram.overlay.widget_parameters.AppCostWidgetParameters;
import org.finos.waltz.model.aggregate_overlay_diagram.overlay.widget_parameters.AssessmentWidgetParameters;
import org.finos.waltz.model.aggregate_overlay_diagram.overlay.widget_parameters.RatingCostWidgetParameters;
import org.finos.waltz.model.user.SystemRole;
import org.finos.waltz.service.aggregate_overlay_diagram.AggregateOverlayDiagramService;
import org.finos.waltz.service.user.UserRoleService;
import org.finos.waltz.web.json.OverlayDiagramAggregatedEntitiesWidgetInfo;
import org.finos.waltz.web.json.OverlayDiagramAppChangeWidgetInfo;
import org.finos.waltz.web.json.OverlayDiagramAppComplexityWidgetInfo;
import org.finos.waltz.web.json.OverlayDiagramAppCostWidgetInfo;
import org.finos.waltz.web.json.OverlayDiagramAppCountWidgetInfo;
import org.finos.waltz.web.json.OverlayDiagramAssessmentWidgetInfo;
import org.finos.waltz.web.json.OverlayDiagramAttestationWidgetInfo;
import org.finos.waltz.web.json.OverlayDiagramRatingCostWidgetInfo;
import org.finos.waltz.web.json.OverlayDiagramTargetAppCostWidgetInfo;
import org.finos.waltz.web.json.OverlayDiagramWidgetInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.web.WebUtilities.mkPath;

@RestController
@RequestMapping("/api/aggregate-overlay-diagram/")
public class AggregateOverlayDiagramEndpointController {

    private static final Logger LOG = LoggerFactory.getLogger(AggregateOverlayDiagramEndpoint.class);

    private final AggregateOverlayDiagramService aggregateOverlayDiagramService;
    private final UserRoleService userRoleService;


    @Autowired
    public AggregateOverlayDiagramEndpointController(AggregateOverlayDiagramService aggregateOverlayDiagramService,
                                           UserRoleService userRoleService) {
        checkNotNull(aggregateOverlayDiagramService, "aggregateOverlayDiagramService must not be null");
        checkNotNull(userRoleService, "userRoleService must not be null");

        this.aggregateOverlayDiagramService = aggregateOverlayDiagramService;
        this.userRoleService = userRoleService;
    }


    @GetMapping("id/{id}")
    public AggregateOverlayDiagramInfo getById(@PathVariable("id") long id) {
        return aggregateOverlayDiagramService.getById(id);
    }

    @GetMapping("all")
    public Set<AggregateOverlayDiagram> findAll() {
        return aggregateOverlayDiagramService.findAll();
    }

    @GetMapping("diagram-kind/{kind}")
    public Set<AggregateOverlayDiagram> findByKind(@PathVariable("kind") OverlayDiagramKind kind) {
        return aggregateOverlayDiagramService.findByKind(kind);
    }

    @PostMapping("diagram-id/{id}/app-count-widget")
    public CountWidgetData getAppCountWidgetData(@PathVariable("id") long diagramId,
                                                 @RequestBody OverlayDiagramAppCountWidgetInfo widgetInfo) {
        return aggregateOverlayDiagramService.getAppCountWidgetData(
                diagramId,
                widgetInfo.idSelectionOptions(),
                widgetInfo.assessmentBasedSelectionFilters(),
                widgetInfo.overlayParameters());
    }

    @PostMapping("diagram-id/{id}/attestation")
    public AttestationWidgetData getAttestationWidgetData(@PathVariable("id") long diagramId,
                                                          @RequestBody OverlayDiagramAttestationWidgetInfo widgetInfo) {
        return aggregateOverlayDiagramService.getAttestationWidgetData(
                diagramId,
                widgetInfo.assessmentBasedSelectionFilters(),
                widgetInfo.idSelectionOptions(),
                widgetInfo.overlayParameters());
    }

    @PostMapping("diagram-id/{id}/target-app-cost-widget")
    public TargetCostWidgetData findTargetAppCostWidgetData(@PathVariable("id") long diagramId,
                                                            @RequestBody OverlayDiagramTargetAppCostWidgetInfo widgetParameters) {
        return aggregateOverlayDiagramService.getTargetAppCostWidgetData(
                diagramId,
                widgetParameters.idSelectionOptions(),
                widgetParameters.assessmentBasedSelectionFilters(),
                widgetParameters.overlayParameters());
    }

    @PostMapping("diagram-id/{id}/app-cost-widget")
    public CostWidgetData getAppCostWidgetData(@PathVariable("id") long diagramId,
                                               @RequestBody OverlayDiagramAppCostWidgetInfo appCostWidgetParameters) {
        return aggregateOverlayDiagramService.getAppCostWidgetData(
                diagramId,
                appCostWidgetParameters.assessmentBasedSelectionFilters(),
                appCostWidgetParameters.idSelectionOptions(),
                appCostWidgetParameters.overlayParameters());
    }

    @PostMapping("diagram-id/{id}/rating-cost-widget")
    public CostWidgetData getRatingCostWidgetData(@PathVariable("id") long diagramId,
                                                  @RequestBody OverlayDiagramRatingCostWidgetInfo costWidgetParameters) {
        return aggregateOverlayDiagramService.getRatingCostWidgetData(
                diagramId,
                costWidgetParameters.assessmentBasedSelectionFilters(),
                costWidgetParameters.idSelectionOptions(),
                costWidgetParameters.overlayParameters());
    }

    @PostMapping("diagram-id/{id}/complexity-widget")
    public ComplexityWidgetData getComplexityWidgetData(@PathVariable("id") long diagramId,
                                                        @RequestBody OverlayDiagramAppComplexityWidgetInfo appComplexityWidgetParameters) {
        return aggregateOverlayDiagramService.getAppComplexityWidgetData(
                diagramId,
                appComplexityWidgetParameters.assessmentBasedSelectionFilters(),
                appComplexityWidgetParameters.idSelectionOptions(),
                appComplexityWidgetParameters.overlayParameters());
    }

    @PostMapping("diagram-id/{id}/app-assessment-widget")
    public AssessmentRatingsWidgetData getAppAssessmentWidgetData(@PathVariable("id") long diagramId,
                                                                  @RequestBody OverlayDiagramAssessmentWidgetInfo widgetParameters) {
        return aggregateOverlayDiagramService.getAppAssessmentWidgetData(
                diagramId,
                widgetParameters.assessmentBasedSelectionFilters(),
                widgetParameters.idSelectionOptions(),
                widgetParameters.overlayParameters());
    }

    @PostMapping("diagram-id/{id}/aggregated-entities-widget")
    public AggregatedEntitiesWidgetData getAggregatedEntitiesWidgetData(@PathVariable("id") long diagramId,
                                                                        @RequestBody OverlayDiagramAggregatedEntitiesWidgetInfo widgetParameters) {
        return aggregateOverlayDiagramService.getAggregatedEntitiesWidgetData(
                diagramId,
                widgetParameters.assessmentBasedSelectionFilters(),
                widgetParameters.idSelectionOptions());
    }

    @PostMapping("diagram-id/{id}/app-change-widget")
    public ApplicationChangeWidgetData getApplicationChangeWidgetData(@PathVariable("id") long diagramId,
                                                                      @RequestBody OverlayDiagramAppChangeWidgetInfo widgetParameters) {
        return aggregateOverlayDiagramService.getApplicationChangeWidgetData(
                diagramId,
                widgetParameters.idSelectionOptions(),
                widgetParameters.overlayParameters());
    }

    @RequestMapping(value = "diagram-id/{id}/backing-entity-widget", method = {RequestMethod.GET, RequestMethod.POST})
    public BackingEntityWidgetData getBackingEntityWidgetData(@PathVariable("id") long diagramId) {
        return aggregateOverlayDiagramService.getBackingEntityWidgetData(diagramId);
    }

    @PostMapping("create-preset")
    public int createPreset(@RequestBody OverlayDiagramPresetCreateCommand createCommand, Principal principal) {
        return aggregateOverlayDiagramService.createPreset(createCommand, principal.getName());
    }

    @PostMapping("save")
    public Long save(@RequestBody OverlayDiagramSaveCommand saveCommand, Principal principal) {
        ensureUserHasEditRights(principal.getName());
        return aggregateOverlayDiagramService.save(saveCommand, principal.getName());
    }

    @PostMapping("id/{id}/status")
    public boolean updateStatus(@PathVariable("id") long diagramId,
                                @RequestBody ReleaseLifecycleStatusChangeCommand command,
                                Principal principal) {
        ensureUserHasEditRights(principal.getName());
        return aggregateOverlayDiagramService.updateStatus(diagramId, command, principal.getName());
    }

    private void ensureUserHasEditRights(String username) {
        userRoleService.hasRole(username, SystemRole.ADMIN);
    }

}
