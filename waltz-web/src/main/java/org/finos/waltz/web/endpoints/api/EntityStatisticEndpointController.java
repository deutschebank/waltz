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

import org.finos.waltz.common.EnumUtilities;
import org.finos.waltz.model.Duration;
import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.IdSelectionOptions;
import org.finos.waltz.model.application.Application;
import org.finos.waltz.model.entity_statistic.*;
import org.finos.waltz.model.immediate_hierarchy.ImmediateHierarchy;
import org.finos.waltz.model.tally.TallyPack;
import org.finos.waltz.service.entity_statistic.EntityStatisticService;
import org.finos.waltz.web.json.EntityStatisticQueryOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.model.EntityReference.mkRef;
import static org.finos.waltz.web.WebUtilities.getEntityReference;


@RestController
@RequestMapping("/api/entity-statistic")
public class EntityStatisticEndpointController {

    private final EntityStatisticService entityStatisticService;

    @Autowired
    public EntityStatisticEndpointController(EntityStatisticService entityStatisticService) {
        checkNotNull(entityStatisticService, "entityStatisticService cannot be null");
        this.entityStatisticService = entityStatisticService;
    }

    @GetMapping("/definition")
    public List<EntityStatisticDefinition> findAllActiveDefinitions() {
        return entityStatisticService.findAllActiveDefinitions(true);
    }

    @GetMapping("/definition/{id}")
    public EntityStatisticDefinition findDefinition(@PathVariable("id") long id) {
        return entityStatisticService.getDefinitionById(id);
    }

    @GetMapping("/definition/{statId}/related")
    public ImmediateHierarchy<EntityStatisticDefinition> getRelatedStatDefinitions(@PathVariable("statId") long statId) {
        return entityStatisticService.getRelatedStatDefinitions(statId, true);
    }

    @GetMapping("/{kind}/{id}")
    public List<EntityStatistic> findStatsForEntity(@PathVariable("kind") String kind, @PathVariable("id") long id) {
        EntityReference ref = mkRef(EntityKind.valueOf(kind), id);
        return entityStatisticService.findStatisticsForEntity(ref, true);
    }

    @PostMapping("/value/{statId}")
    public List<EntityStatisticValue> findStatValuesBySelector(@PathVariable("statId") long statId,
                                                               @RequestBody IdSelectionOptions idSelectionOptions) {
        return entityStatisticService.getStatisticValuesForAppIdSelector(statId, idSelectionOptions);
    }

    @PostMapping("/app/{statId}")
    public List<Application> findStatAppsBySelector(@PathVariable("statId") long statId,
                                                    @RequestBody IdSelectionOptions idSelectionOptions) {
        return entityStatisticService.getStatisticAppsForAppIdSelector(statId, idSelectionOptions);
    }

    @PostMapping("/tally")
    public List<TallyPack<String>> findStatTallies(@RequestBody EntityStatisticQueryOptions options) {
        return entityStatisticService.findStatTallies(options.statisticIds(), options.selector());
    }

    @PostMapping("/tally/{id}/{rollupKind}")
    public TallyPack<String> calculateStatTally(@PathVariable("id") long statisticId,
                                                @PathVariable("rollupKind") String rollupKindStr,
                                                @RequestBody IdSelectionOptions idSelectionOptions) {
        RollupKind rollupKind = parseRollupKind(rollupKindStr);
        return entityStatisticService.calculateStatTally(statisticId, rollupKind, idSelectionOptions);
    }

    @PostMapping("/tally/historic/{id}/{rollupKind}")
    public List<TallyPack<String>> calculateHistoricStatTally(@PathVariable("id") long statisticId,
                                                              @PathVariable("rollupKind") String rollupKindStr,
                                                              @RequestParam(value = "duration", defaultValue = "MONTH") String durationStr,
                                                              @RequestBody IdSelectionOptions idSelectionOptions) {
        RollupKind rollupKind = parseRollupKind(rollupKindStr);
        Duration duration = EnumUtilities.readEnum(durationStr, Duration.class, s -> Duration.MONTH);
        return entityStatisticService.calculateHistoricStatTally(statisticId, rollupKind, idSelectionOptions, duration);
    }

    private RollupKind parseRollupKind(String rollupKindStr) {
        return EnumUtilities.readEnum(
                rollupKindStr,
                RollupKind.class,
                (s) -> {
                    String msg = String.format("rollupKind cannot be [%s]", s);
                    throw new UnsupportedOperationException(msg);
                });
    }
}