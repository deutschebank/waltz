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

package org.finos.waltz.web.endpoints.extracts;

import org.finos.waltz.schema.tables.Measurable;
import org.finos.waltz.schema.tables.ScenarioAxisItem;
import org.finos.waltz.model.AxisOrientation;
import org.finos.waltz.model.EntityKind;
import org.finos.waltz.web.WebUtilities;
import org.jooq.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.finos.waltz.schema.Tables.*;
import static org.finos.waltz.schema.tables.Application.APPLICATION;
import static org.finos.waltz.web.endpoints.extracts.DirectQueryBasedExtractorUtilities.writeExtract;


@RestController
@RequestMapping("/data-extract/scenario/")
public class ScenarioExtractorController  {

    private static final Logger LOG = LoggerFactory.getLogger(ScenarioExtractor.class);
    private final DSLContext dsl;
    @Autowired
    public ScenarioExtractorController(DSLContext dsl) {
        this.dsl = dsl;
    }


    @GetMapping("{id}")
    public Object extract(HttpServletRequest request,
                          HttpServletResponse response,
                          @PathVariable("id") long scenarioId) throws Exception {

        Measurable rowMeasurable = MEASURABLE.as("rowMeasurable");
        Measurable colMeasurable = MEASURABLE.as("colMeasurable");
        ScenarioAxisItem rowAxisItem = SCENARIO_AXIS_ITEM.as("rowAxisItem");
        ScenarioAxisItem colAxisItem = SCENARIO_AXIS_ITEM.as("colAxisItem");

        Condition rowMeasurableJoin = rowMeasurable.ID.eq(SCENARIO_RATING_ITEM.ROW_ID)
                .and(SCENARIO_RATING_ITEM.ROW_KIND.eq(EntityKind.MEASURABLE.name()));

        Condition colMeasurableJoin = colMeasurable.ID.eq(SCENARIO_RATING_ITEM.COLUMN_ID)
                .and(SCENARIO_RATING_ITEM.COLUMN_KIND.eq(EntityKind.MEASURABLE.name()));

        Condition appJoin = APPLICATION.ID.eq(SCENARIO_RATING_ITEM.DOMAIN_ITEM_ID)
                .and(SCENARIO_RATING_ITEM.DOMAIN_ITEM_KIND.eq(EntityKind.APPLICATION.name()));

        Condition rowAxisItemJoin = rowAxisItem.DOMAIN_ITEM_ID.eq(SCENARIO_RATING_ITEM.ROW_ID)
                .and(rowAxisItem.ORIENTATION.eq(AxisOrientation.ROW.name()))
                .and(rowAxisItem.SCENARIO_ID.eq(SCENARIO_RATING_ITEM.SCENARIO_ID));

        Condition colAxisItemJoin = colAxisItem.DOMAIN_ITEM_ID.eq(SCENARIO_RATING_ITEM.COLUMN_ID)
                .and(colAxisItem.ORIENTATION.eq(AxisOrientation.COLUMN.name()))
                .and(colAxisItem.SCENARIO_ID.eq(SCENARIO_RATING_ITEM.SCENARIO_ID));

        SelectConditionStep<Record6<String, String, String, String, String, String>> qry = dsl
                .select(
                    APPLICATION.NAME.as("Application"),
                    APPLICATION.ASSET_CODE.as("App Asset Code"),
                    colMeasurable.NAME.as("Column"),
                    rowMeasurable.NAME.as("Row"),
                    RATING_SCHEME_ITEM.NAME.as("Rating"),
                    SCENARIO_RATING_ITEM.DESCRIPTION.as("Description"))
                .from(SCENARIO_RATING_ITEM)
                .innerJoin(APPLICATION)
                .on(appJoin)
                .innerJoin(rowMeasurable)
                .on(rowMeasurableJoin)
                .innerJoin(colMeasurable)
                .on(colMeasurableJoin)
                .innerJoin(rowAxisItem)
                .on(rowAxisItemJoin)
                .innerJoin(colAxisItem)
                .on(colAxisItemJoin)
                .innerJoin(SCENARIO)
                .on(SCENARIO.ID.eq(scenarioId))
                .innerJoin(ROADMAP)
                .on(ROADMAP.ID.eq(SCENARIO.ROADMAP_ID))
                .innerJoin(RATING_SCHEME_ITEM)
                .on(RATING_SCHEME_ITEM.SCHEME_ID.eq(ROADMAP.RATING_SCHEME_ID).and(RATING_SCHEME_ITEM.CODE.eq(SCENARIO_RATING_ITEM.RATING)))
                .where(SCENARIO_RATING_ITEM.SCENARIO_ID.eq(scenarioId));


        String scenarioName = dsl
                .select(SCENARIO.NAME)
                .from(SCENARIO)
                .where(SCENARIO.ID.eq(scenarioId))
                .fetchOne(SCENARIO.NAME);

        String suggestedFilename = "scenario-" + scenarioName;

        return writeExtract(suggestedFilename, qry, request, response);
    }

}
