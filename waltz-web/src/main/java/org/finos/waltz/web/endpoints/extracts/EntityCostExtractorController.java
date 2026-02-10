/*
 * Waltz - Enterprise Architecture
 * Copyright (C) 2016, 2017, 2018, 2019, 2020 Waltz open source project
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

package org.finos.waltz.web.endpoints.extracts;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.finos.waltz.data.GenericSelector;
import org.finos.waltz.data.GenericSelectorFactory;
import org.finos.waltz.data.InlineSelectFieldFactory;
import org.finos.waltz.model.*;
import org.finos.waltz.model.EntityKind;
import org.finos.waltz.service.settings.SettingsService;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.SelectJoinStep;
import org.jooq.SelectSeekStep1;
import org.jooq.SelectSeekStep2;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static java.lang.String.format;
import static org.finos.waltz.common.ListUtilities.newArrayList;
import static org.finos.waltz.schema.Tables.COST;
import static org.finos.waltz.schema.Tables.COST_KIND;
import static org.finos.waltz.web.endpoints.extracts.DirectQueryBasedExtractorUtilities.writeExtract;

@RestController
@RequestMapping("/data-extract/cost")
public class EntityCostExtractorController {

    private final GenericSelectorFactory genericSelectorFactory = new GenericSelectorFactory();
    private final SettingsService settingsService;
    private final DSLContext dsl;


    private static final Logger LOG = LoggerFactory.getLogger(EntityCostExtractor.class);

    private static final Field<String> ENTITY_NAME_FIELD = InlineSelectFieldFactory.mkNameField(
            COST.ENTITY_ID,
            COST.ENTITY_KIND,
            newArrayList(EntityKind.APPLICATION))
            .as("entity_name");

    @Autowired
    public EntityCostExtractorController(DSLContext dsl, SettingsService settingsService) {
        this.dsl = dsl;
        this.settingsService = settingsService;
    }


    @RequestMapping(value = "/kind/{kind}/id/{id}", method = RequestMethod.GET)
    public void getCostsForEntity(HttpServletRequest request,
                                  HttpServletResponse response,
                                  @PathVariable("kind") String kind,
                                  @PathVariable("id") long id) throws IOException {
        ensureCostExportsAreEnabled();
        EntityReference ref = ImmutableEntityReference.builder().kind(EntityKind.valueOf(kind)).id(id).build();
        SelectSeekStep1<Record, Integer> qry = dsl
                .select(ENTITY_NAME_FIELD)
                .select(COST_KIND.NAME.as("kind"))
                .select(COST.YEAR,
                        COST.AMOUNT,
                        COST.PROVENANCE)
                .from(COST)
                .innerJoin(COST_KIND)
                .on(COST.COST_KIND_ID.eq(COST_KIND.ID))
                .where(COST.ENTITY_ID.eq(ref.id())
                        .and(COST.ENTITY_KIND.eq(ref.kind().name())))
                .orderBy(COST.YEAR.desc());
        writeExtract(
                mkFilename(ref),
                qry,
                request,
                response);
    }


    @RequestMapping(value = "/target-kind/{kind}/selector", method = RequestMethod.POST)
    public void getCostsForSelector(HttpServletRequest request,
                                    HttpServletResponse response,
                                    @PathVariable("kind") String kind,
                                    @RequestBody IdSelectionOptions idSelectionOptions) throws IOException {
        ensureCostExportsAreEnabled();
        EntityKind targetKind = EntityKind.valueOf(kind);
        GenericSelector genericSelector = genericSelectorFactory.applyForKind(targetKind, idSelectionOptions);

        SelectJoinStep<Record1<Integer>> latestYear = DSL
                .select(DSL.max(COST.YEAR))
                .from(COST);

        SelectSeekStep2<Record, String, Long> qry = dsl
                .select(COST.ENTITY_ID.as("Entity ID"),
                        COST.ENTITY_KIND.as("Entity Kind"))
                .select(ENTITY_NAME_FIELD.as("Name"))
                .select(COST_KIND.NAME.as("Kind"),
                        COST_KIND.DESCRIPTION.as("Kind Description"))
                .select(COST.YEAR.as("Year"),
                        COST.AMOUNT.as("Amount"),
                        COST.PROVENANCE.as("Provenance"))
                .from(COST)
                .innerJoin(COST_KIND).on(COST.COST_KIND_ID.eq(COST_KIND.ID))
                .where(COST.ENTITY_ID.in(genericSelector.selector())
                        .and(COST.ENTITY_KIND.eq(genericSelector.kind().name())))
                .and(COST.YEAR.eq(latestYear))
                .orderBy(ENTITY_NAME_FIELD.as("Name"), COST.COST_KIND_ID);
        writeExtract(
                mkFilename(idSelectionOptions.entityReference()),
                qry,
                request,
                response);
    }


    private String mkFilename(EntityReference ref) {
        return format("%s-%d-cost-info", ref.kind(), ref.id());
    }

    private void ensureCostExportsAreEnabled() {
        String costExportsAllowedSetting = settingsService
                .getValue(SettingsService.ALLOW_COST_EXPORTS_KEY)
                .orElse("false");
        if (!Boolean.valueOf(costExportsAllowedSetting)) {
            throw new UnsupportedOperationException(format("Cost exports not enabled, setting: %s", SettingsService.ALLOW_COST_EXPORTS_KEY));
        }
    }

}
