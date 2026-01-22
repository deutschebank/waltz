/*
 * Waltz - Enterprise Architecture
 * Copyright (C) 2016, 2017, 2018, 2019 Waltz open source project
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
import org.finos.waltz.model.*;
import org.finos.waltz.model.scenario.Scenario;
import org.finos.waltz.model.scenario.ScenarioAxisItem;
import org.finos.waltz.model.scenario.ScenarioRatingItem;
import org.finos.waltz.model.user.SystemRole;
import org.finos.waltz.service.roadmap.RoadmapService;
import org.finos.waltz.service.scenario.ScenarioAxisItemService;
import org.finos.waltz.service.scenario.ScenarioRatingItemService;
import org.finos.waltz.service.scenario.ScenarioService;
import org.finos.waltz.service.user.UserRoleService;
import org.finos.waltz.web.json.ImmutableFullScenario;
import org.finos.waltz.model.scenario.ImmutableChangeScenarioCommand;
import org.finos.waltz.model.scenario.ImmutableCloneScenarioCommand;
import org.finos.waltz.model.scenario.ScenarioType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.model.EntityReference.mkRef;
import static org.finos.waltz.web.WebUtilities.*;

@RestController
@RequestMapping("/api/scenario")
public class ScenarioEndpointController {

    private final RoadmapService roadmapService;
    private final ScenarioService scenarioService;
    private final ScenarioAxisItemService scenarioAxisItemService;
    private final ScenarioRatingItemService scenarioRatingItemService;
    private final UserRoleService userRoleService;


    @Autowired
    public ScenarioEndpointController(RoadmapService roadmapService,
                            ScenarioService scenarioService,
                            ScenarioAxisItemService scenarioAxisItemService,
                            ScenarioRatingItemService scenarioRatingItemService,
                            UserRoleService userRoleService) {
        checkNotNull(roadmapService, "roadmapService cannot be null");
        checkNotNull(scenarioService, "scenarioService cannot be null");
        checkNotNull(scenarioAxisItemService, "scenarioAxisItemService cannot be null");
        checkNotNull(scenarioRatingItemService, "scenarioRatingItemService cannot be null");
        checkNotNull(userRoleService, "userRoleService cannot be null");

        this.roadmapService = roadmapService;
        this.scenarioService = scenarioService;
        this.scenarioAxisItemService = scenarioAxisItemService;
        this.scenarioRatingItemService = scenarioRatingItemService;
        this.userRoleService = userRoleService;
    }

    @GetMapping("/by-roadmap-id/{roadmapId}")
    public Collection<Scenario> findScenariosForRoadmapId(@PathVariable("roadmapId") long roadmapId) {
        return scenarioService.findForRoadmapId(roadmapId);
    }

    @PostMapping("/by-roadmap-selector")
    public Collection<Scenario> findScenariosByRoadmapSelector(@RequestBody IdSelectionOptions selectionOptions) {
        return scenarioService.findScenariosByRoadmapSelector(selectionOptions);
    }

    @GetMapping("/id/{id}")
    public ImmutableFullScenario getScenarioById(@PathVariable("id") long scenarioId) {
        Scenario scenario = scenarioService.getById(scenarioId);
        return ImmutableFullScenario
                .builder()
                .scenario(scenario)
                .axisDefinitions(scenarioAxisItemService.findForScenarioId(scenarioId))
                .ratings(scenarioRatingItemService.findForScenarioId(scenarioId))
                .roadmap(roadmapService.getById(scenario.roadmapId()))
                .build();
    }

    @PostMapping("/id/{id}/clone")
    public Scenario cloneScenario(@PathVariable("id") long scenarioId,
                              @RequestBody String newName,
                              HttpServletRequest principal) {
        ensureUserHasAdminRights(principal);
        return scenarioService.cloneScenario(ImmutableCloneScenarioCommand
                .builder()
                .newName(newName)
                .scenarioId(scenarioId)
                .userId(getUsernameForSB(principal))
                .build());
    }

    @PostMapping("/remove-rating")
    public boolean removeRating(@RequestBody ImmutableChangeScenarioCommand command,
                            HttpServletRequest principal) {
        ensureUserHasEditRights(principal);
        return scenarioRatingItemService.remove(command, getUsernameForSB(principal));
    }

    @PostMapping("/change-rating")
    public boolean updateRating(@RequestBody ImmutableChangeScenarioCommand command,
                            HttpServletRequest principal) {
        ensureUserHasEditRights(principal);
        return scenarioRatingItemService.updateRating(command, getUsernameForSB(principal));
    }

    @PostMapping("/add-rating")
    public boolean addRating(@RequestBody ImmutableChangeScenarioCommand command,
                         HttpServletRequest principal) {
        ensureUserHasEditRights(principal);
        return scenarioRatingItemService.add(command, getUsernameForSB(principal));
    }

    @PostMapping("/id/{id}/name")
    public boolean updateName(@PathVariable("id") long scenarioId, @RequestBody String newName, HttpServletRequest principal) {
        ensureUserHasEditRights(principal);
        return scenarioService.updateName(scenarioId, newName, getUsernameForSB(principal));
    }

    @PostMapping("/id/{id}/description")
    public boolean updateDescription(@PathVariable("id") long scenarioId, @RequestBody String newDescription, HttpServletRequest principal) {
        ensureUserHasEditRights(principal);
        return scenarioService.updateDescription(scenarioId, newDescription, getUsernameForSB(principal));
    }

    @PostMapping("/id/{id}/effective-date")
    public boolean updateEffectiveDate(@PathVariable("id") long scenarioId, @RequestBody LocalDate newDate, HttpServletRequest principal) {
        ensureUserHasEditRights(principal);
        return scenarioService.updateEffectiveDate(scenarioId, newDate, getUsernameForSB(principal));
    }

    @PostMapping("/id/{id}/scenario-type/{scenarioType}")
    public boolean updateScenarioType(@PathVariable("id") long scenarioId,
                                  @PathVariable("scenarioType") ScenarioType scenarioType,
                                  HttpServletRequest principal) {
        ensureUserHasEditRights(principal);
        return scenarioService.updateScenarioType(scenarioId, scenarioType, getUsernameForSB(principal));
    }

    @PostMapping("/id/{id}/release-status/{releaseStatus}")
    public boolean updateReleaseStatus(@PathVariable("id") long scenarioId,
                                   @PathVariable("releaseStatus") ReleaseLifecycleStatus releaseStatus,
                                   HttpServletRequest principal) {
        ensureUserHasEditRights(principal);
        return scenarioService.updateReleaseStatus(scenarioId, releaseStatus, getUsernameForSB(principal));
    }

    @PostMapping("/id/{id}/entity-lifecycle-status/{lifecycleStatus}")
    public boolean updateEntityLifecycleStatus(@PathVariable("id") long scenarioId,
                                           @PathVariable("lifecycleStatus") EntityLifecycleStatus lifecycleStatus,
                                           HttpServletRequest principal) {
        ensureUserHasAdminRights(principal);
        return scenarioService.updateEntityLifecycleStatus(scenarioId, lifecycleStatus, getUsernameForSB(principal));
    }

    @GetMapping("/id/{id}/axis/{orientation}")
    public Collection<ScenarioAxisItem> loadAxis(@PathVariable("id") long scenarioId,
                                                 @PathVariable("orientation") AxisOrientation orientation) {
        return scenarioAxisItemService.loadAxis(scenarioId, orientation);
    }

    @PostMapping("/id/{id}/axis/{orientation}/reorder")
    public int[] reorderAxis(@PathVariable("id") long scenarioId,
                           @PathVariable("orientation") AxisOrientation orientation,
                           @RequestBody List<Long> domainItemIds,
                           HttpServletRequest principal) {
        ensureUserHasEditRights(principal);
        return scenarioAxisItemService.reorderAxis(scenarioId, orientation, domainItemIds, getUsernameForSB(principal));
    }

    @PostMapping("/id/{id}/axis/{orientation}/{domainItemKind}/{domainItemId}")
    public boolean addAxisItem(@PathVariable("id") long scenarioId,
                           @PathVariable("orientation") AxisOrientation orientation,
                           @PathVariable("domainItemKind") String domainItemKind,
                           @PathVariable("domainItemId") long domainItemId,
                           @RequestBody(required = false) Integer position,
                           HttpServletRequest principal) {
        ensureUserHasEditRights(principal);
        EntityReference domainItem = mkRef(EntityKind.valueOf(domainItemKind), domainItemId);
        return scenarioAxisItemService.addAxisItem(scenarioId, orientation, domainItem, position, getUsernameForSB(principal));
    }

    @DeleteMapping("/id/{id}/axis/{orientation}/{domainItemKind}/{domainItemId}")
    public Boolean removeAxisItem(@PathVariable("id") long scenarioId,
                              @PathVariable("orientation") AxisOrientation orientation,
                              @PathVariable("domainItemKind") String domainItemKind,
                              @PathVariable("domainItemId") long domainItemId,
                              HttpServletRequest principal) {
        ensureUserHasEditRights(principal);
        EntityReference domainItem = mkRef(EntityKind.valueOf(domainItemKind), domainItemId);
        return scenarioAxisItemService.removeAxisItem(scenarioId, orientation, domainItem, getUsernameForSB(principal));
    }

    @DeleteMapping("/id/{id}")
    public boolean removeScenario(@PathVariable("id") long scenarioId, HttpServletRequest principal) {
        ensureUserHasAdminRights(principal);
        return scenarioService.removeScenario(scenarioId, getUsernameForSB(principal));
    }

    // -- helpers --
    private void ensureUserHasAdminRights(HttpServletRequest principal) {
        requireRoleForSB(userRoleService, principal, SystemRole.SCENARIO_ADMIN);
    }

    private void ensureUserHasEditRights(HttpServletRequest principal) {
        requireAnyRoleForSB(userRoleService, principal, SystemRole.SCENARIO_EDITOR, SystemRole.SCENARIO_ADMIN);
    }
}
