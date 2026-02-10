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
import org.finos.waltz.model.EntityLifecycleStatus;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.IdSelectionOptions;
import org.finos.waltz.model.roadmap.Roadmap;
import org.finos.waltz.model.roadmap.RoadmapAndScenarioOverview;
import org.finos.waltz.model.roadmap.RoadmapCreateCommand;
import org.finos.waltz.model.scenario.Scenario;
import org.finos.waltz.service.roadmap.RoadmapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.common.EnumUtilities.readEnum;

@RestController
@RequestMapping("/api/roadmap")
public class RoadmapEndpointController {

    private final RoadmapService roadmapService;


    @Autowired
    public RoadmapEndpointController(RoadmapService roadmapService) {
        checkNotNull(roadmapService, "roadmapService cannot be null");
        this.roadmapService = roadmapService;
    }

    @PostMapping
    @PreAuthorize("hasRole('SCENARIO_ADMIN')")
    public long createRoadmap(@RequestBody RoadmapCreateCommand createCommand, Principal principal) {
        return roadmapService.createRoadmap(createCommand, principal.getName());
    }

    @GetMapping
    public Collection<RoadmapAndScenarioOverview> findAllRoadmapsAndScenarios() {
        return roadmapService.findAllRoadmapsAndScenarios();
    }

    @GetMapping("/by-formal-relationship/{kind}/{id}")
    public Collection<RoadmapAndScenarioOverview> findRoadmapsAndScenariosByFormalRelationship(@PathVariable("kind") String kind, @PathVariable("id") long id) {
        EntityReference ref = EntityReference.mkRef(EntityKind.valueOf(kind), id);
        return roadmapService.findRoadmapsAndScenariosByFormalRelationship(ref);
    }

    @GetMapping("/by-rated-entity/{kind}/{id}")
    public Collection<RoadmapAndScenarioOverview> findRoadmapsAndScenariosByRatedEntity(@PathVariable("kind") String kind, @PathVariable("id") long id) {
        EntityReference ref = EntityReference.mkRef(EntityKind.valueOf(kind), id);
        return roadmapService.findRoadmapsAndScenariosByRatedEntity(ref);
    }

    @PostMapping("/id/{id}/add-scenario")
    @PreAuthorize("hasRole('SCENARIO_ADMIN')")
    public Scenario addScenario(@PathVariable("id") long roadmapId,
                                @RequestBody String scenarioName,
                                Principal principal) {
        return roadmapService.addScenario(
                roadmapId,
                scenarioName,
                principal.getName());
    }

    @PostMapping("/id/{id}/name")
    @PreAuthorize("hasRole('SCENARIO_ADMIN')")
    public Boolean updateName(@PathVariable("id") long roadmapId,
                          @RequestBody String newName,
                          Principal principal) {
        return roadmapService.updateName(
                roadmapId,
                newName,
                principal.getName());
    }

    @PostMapping("/id/{id}/description")
    @PreAuthorize("hasRole('SCENARIO_ADMIN')")
    public Boolean updateDescription(@PathVariable("id") long roadmapId,
                                 @RequestBody String newDescription,
                                 Principal principal) {
        return roadmapService.updateDescription(
                roadmapId,
                newDescription,
                principal.getName());
    }

    @PostMapping("/id/{id}/lifecycle-status")
    @PreAuthorize("hasRole('SCENARIO_ADMIN')")
    public Boolean updateLifecycleStatus(@PathVariable("id") long roadmapId,
                                     @RequestBody String newStatus,
                                     Principal principal) {
        EntityLifecycleStatus status = readEnum(newStatus, EntityLifecycleStatus.class, s -> null);
        return roadmapService.updateLifecycleStatus(
                roadmapId,
                status,
                principal.getName());
    }

    @PostMapping("/by-selector")
    public Collection<Roadmap> findRoadmapsBySelector(@RequestBody IdSelectionOptions options) {
        return roadmapService.findRoadmapsBySelector(options);
    }

    @GetMapping("/id/{id}")
    public Roadmap getRoadmapById(@PathVariable("id") long id) {
        return roadmapService.getById(id);
    }

}
