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

package com.khartec.waltz.web.endpoints.extracts;

import com.khartec.waltz.common.ListUtilities;
import com.khartec.waltz.data.application.ApplicationIdSelectorFactory;
import com.khartec.waltz.data.data_type.DataTypeIdSelectorFactory;
import com.khartec.waltz.model.EntityKind;
import com.khartec.waltz.model.IdSelectionOptions;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.lambda.tuple.Tuple3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.khartec.waltz.common.ListUtilities.isEmpty;
import static com.khartec.waltz.common.ListUtilities.newArrayList;
import static com.khartec.waltz.data.InlineSelectFieldFactory.mkNameField;
import static com.khartec.waltz.model.EntityLifecycleStatus.REMOVED;
import static com.khartec.waltz.schema.Tables.*;
import static com.khartec.waltz.schema.tables.Application.APPLICATION;
import static com.khartec.waltz.schema.tables.LogicalFlow.LOGICAL_FLOW;
import static com.khartec.waltz.schema.tables.OrganisationalUnit.ORGANISATIONAL_UNIT;
import static com.khartec.waltz.web.WebUtilities.mkPath;
import static com.khartec.waltz.web.WebUtilities.readIdSelectionOptionsFromBody;
import static java.util.stream.Collectors.toList;
import static spark.Spark.post;


@Service
public class LogicalFlowExtractor extends CustomDataExtractor {

    private static final Field<String> SOURCE_NAME_FIELD = mkNameField(
            LOGICAL_FLOW.SOURCE_ENTITY_ID,
            LOGICAL_FLOW.SOURCE_ENTITY_KIND,
            newArrayList(EntityKind.APPLICATION, EntityKind.ACTOR));

    private static final Field<String> TARGET_NAME_FIELD = mkNameField(
            LOGICAL_FLOW.TARGET_ENTITY_ID,
            LOGICAL_FLOW.TARGET_ENTITY_KIND,
            newArrayList(EntityKind.APPLICATION, EntityKind.ACTOR));

    private static List<String> staticHeaders = newArrayList(
            "Source",
            "Source Asset Code",
            "Source Org Unit",
            "Target",
            "Target Asset Code",
            "Target Org Unit",
            "Data Type",
            "Authoritativeness");

    private final ApplicationIdSelectorFactory applicationIdSelectorFactory = new ApplicationIdSelectorFactory();
    private final DataTypeIdSelectorFactory dataTypeIdSelectorFactory = new DataTypeIdSelectorFactory();

    private final DSLContext dsl;

    @Autowired
    public LogicalFlowExtractor(DSLContext dsl) {
        this.dsl = dsl;
    }


    @Override
    public void register() {
        post(mkPath("data-extract", "logical-flows"), (request, response) -> {
            IdSelectionOptions options = readIdSelectionOptionsFromBody(request);

            return writeReportResults(
                    response,
                    prepareFlows(
                            prepareQuery(dsl, options),
                            parseExtractFormat(request),
                            "logical-flows",
                            getTagsMap()));
        });
    }


    private SelectConditionStep<Record> prepareQuery(DSLContext dsl, IdSelectionOptions options) {

        Select<Record1<Long>> appIdSelector = applicationIdSelectorFactory.apply(options);

        Condition conditionForDataType = EntityKind.DATA_TYPE.equals(options.entityReference().kind())
                ? LOGICAL_FLOW_DECORATOR.DECORATOR_ENTITY_ID.in(dataTypeIdSelectorFactory.apply(options))
                : DSL.trueCondition();

        Field<Long> sourceFlowId = LOGICAL_FLOW.ID.as("sourceFlowId");
        Field<Long> targetFlowId = LOGICAL_FLOW.ID.as("targetFlowId");

        Select<Record1<Long>> sourceAppFlows = DSL.select(sourceFlowId)
                .from(LOGICAL_FLOW)
                .innerJoin(APPLICATION)
                    .on(LOGICAL_FLOW.SOURCE_ENTITY_ID.eq(APPLICATION.ID))
                .where(LOGICAL_FLOW.SOURCE_ENTITY_KIND.eq(EntityKind.APPLICATION.name()))
                .and(APPLICATION.ID.in(appIdSelector));

        Select<Record1<Long>> targetAppFlows = DSL.select(targetFlowId)
                .from(LOGICAL_FLOW)
                .innerJoin(APPLICATION)
                    .on(LOGICAL_FLOW.TARGET_ENTITY_ID.eq(APPLICATION.ID))
                .where(LOGICAL_FLOW.TARGET_ENTITY_KIND.eq(EntityKind.APPLICATION.name()))
                .and(APPLICATION.ID.in(appIdSelector));

        Field<String> sourceAssetCodeField = DSL
                .when(LOGICAL_FLOW.SOURCE_ENTITY_KIND.eq(EntityKind.APPLICATION.name()),
                        DSL.select(APPLICATION.ASSET_CODE)
                                .from(APPLICATION)
                                .where(APPLICATION.ID.eq(LOGICAL_FLOW.SOURCE_ENTITY_ID)));

        Field<String> sourceOrgUnitNameField = DSL
                .when(LOGICAL_FLOW.SOURCE_ENTITY_KIND.eq(EntityKind.APPLICATION.name()),
                        DSL.select(ORGANISATIONAL_UNIT.NAME)
                                .from(APPLICATION)
                                .innerJoin(ORGANISATIONAL_UNIT)
                                    .on(ORGANISATIONAL_UNIT.ID.eq(APPLICATION.ORGANISATIONAL_UNIT_ID))
                                .where(APPLICATION.ID.eq(LOGICAL_FLOW.SOURCE_ENTITY_ID)));

        Field<String> targetAssetCodeField = DSL
                .when(LOGICAL_FLOW.TARGET_ENTITY_KIND.eq(EntityKind.APPLICATION.name()),
                        DSL.select(APPLICATION.ASSET_CODE)
                                .from(APPLICATION)
                                .where(APPLICATION.ID.eq(LOGICAL_FLOW.TARGET_ENTITY_ID)));

        Field<String> targetOrgUnitNameField = DSL
                .when(LOGICAL_FLOW.TARGET_ENTITY_KIND.eq(EntityKind.APPLICATION.name()),
                        DSL.select(ORGANISATIONAL_UNIT.NAME)
                                .from(APPLICATION)
                                .innerJoin(ORGANISATIONAL_UNIT)
                                    .on(ORGANISATIONAL_UNIT.ID.eq(APPLICATION.ORGANISATIONAL_UNIT_ID))
                                .where(APPLICATION.ID.eq(LOGICAL_FLOW.TARGET_ENTITY_ID)));

        return dsl
                .select(SOURCE_NAME_FIELD.as("Source"),
                        sourceAssetCodeField.as("Source Asset Code"),
                        sourceOrgUnitNameField.as("Source Org Unit"))
                .select(TARGET_NAME_FIELD.as("Target"),
                        targetAssetCodeField.as("Target Asset Code"),
                        targetOrgUnitNameField.as("Target Org Unit"))
                .select(DATA_TYPE.NAME.as("Data Type"))
                .select(FLOW_CLASSIFICATION.NAME.as("Authoritativeness"))
                .select(LOGICAL_FLOW.ID)
                .from(LOGICAL_FLOW)
                .leftJoin(sourceAppFlows)
                    .on(sourceFlowId.eq(LOGICAL_FLOW.ID))
                .leftJoin(targetAppFlows)
                    .on(targetFlowId.eq(LOGICAL_FLOW.ID))
                .innerJoin(LOGICAL_FLOW_DECORATOR)
                    .on(LOGICAL_FLOW_DECORATOR.LOGICAL_FLOW_ID.eq(LOGICAL_FLOW.ID)
                        .and(LOGICAL_FLOW_DECORATOR.DECORATOR_ENTITY_KIND.eq(EntityKind.DATA_TYPE.name())))
                .innerJoin(DATA_TYPE)
                    .on(DATA_TYPE.ID.eq(LOGICAL_FLOW_DECORATOR.DECORATOR_ENTITY_ID)
                        .and(LOGICAL_FLOW_DECORATOR.DECORATOR_ENTITY_KIND.eq(EntityKind.DATA_TYPE.name())))
                .innerJoin(FLOW_CLASSIFICATION)
                    .on(FLOW_CLASSIFICATION.CODE.eq(LOGICAL_FLOW_DECORATOR.RATING))
                .where(LOGICAL_FLOW.ENTITY_LIFECYCLE_STATUS.ne(REMOVED.name()))
                .and(conditionForDataType)
                .and(sourceFlowId.isNotNull()
                        .or(targetFlowId.isNotNull()));
    }

    private Tuple3<ExtractFormat, String, byte[]> prepareFlows(SelectConditionStep<Record> query,
                                                               ExtractFormat format,
                                                               String reportName,
                                                               Map<Long, List<String>> tags) throws IOException {

        List<List<Object>> reportRows = prepareReportRows(query, tags);
        return formatReport(
                format,
                reportName,
                reportRows,
                ListUtilities.append(staticHeaders, "Tags")
        );
    }

    private List<List<Object>> prepareReportRows(SelectConditionStep<Record> qry,
                                                 Map<Long, List<String>> tags) {
        Result<Record> results = qry.fetch();

        return results
                .stream()
                .map(row -> {
                    ArrayList<Object> reportRow = new ArrayList<>();
                    staticHeaders.forEach(h -> reportRow.add(row.get(h)));

                    Long logicalFlowId = row.get(LOGICAL_FLOW.ID);
                    List<String> logicalFlowTags = tags.get(logicalFlowId);
                    reportRow.add(isEmpty(logicalFlowTags)
                            ? ""
                            : String.join(",", logicalFlowTags));

                    return reportRow;
                })
                .collect(toList());
    }

    private Map<Long, List<String>> getTagsMap() {
        return dsl.select(PHYSICAL_FLOW.LOGICAL_FLOW_ID, TAG.NAME)
                .from(TAG_USAGE)
                .join(TAG)
                .on(TAG.ID.eq(TAG_USAGE.TAG_ID))
                .join(PHYSICAL_FLOW)
                .on(TAG_USAGE.ENTITY_KIND.eq(EntityKind.PHYSICAL_FLOW.name())
                        .and(PHYSICAL_FLOW.ID.eq(TAG_USAGE.ENTITY_ID)))
                .fetchGroups(PHYSICAL_FLOW.LOGICAL_FLOW_ID, TAG.NAME);
    }
}
