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
import org.finos.waltz.data.SelectorUtilities;
import org.finos.waltz.data.application.ApplicationIdSelectorFactory;
import org.finos.waltz.model.IdSelectionOptions;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Select;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;

import static org.finos.waltz.schema.Tables.APPLICATION;
import static org.finos.waltz.schema.Tables.ORGANISATIONAL_UNIT;
import static org.finos.waltz.web.WebUtilities.mkPath;
import static org.finos.waltz.web.endpoints.extracts.DirectQueryBasedExtractorUtilities.writeExtract;


// TODO: remove in 1.61 if not needed
@RestController
@RequestMapping("/data-extract/application/")
@Deprecated
public class ApplicationExtractorController {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationExtractorController.class);

    private final ApplicationIdSelectorFactory applicationIdSelectorFactory;

    private final DSLContext dsl;

    @Autowired
    public ApplicationExtractorController(DSLContext dsl, ApplicationIdSelectorFactory applicationIdSelectorFactory) {
        this.dsl = dsl;
        this.applicationIdSelectorFactory = applicationIdSelectorFactory;
    }

    @PostMapping("by-selector-old")
    public Object findBySelectorOld(@RequestBody IdSelectionOptions idSelectionOptions,
                                                                   HttpServletRequest request, HttpServletResponse response) throws IOException {
        Select<Record1<Long>> idSelector = applicationIdSelectorFactory.apply(idSelectionOptions);
        Condition condition = APPLICATION.ID.in(idSelector)
                .and(SelectorUtilities.mkApplicationConditions(idSelectionOptions));
        SelectConditionStep<?> qry = prepareExtractQuery(condition);
        String fileName = String.format("application-for-%s-%s",
                idSelectionOptions.entityReference().kind().name().toLowerCase(),
                idSelectionOptions.entityReference().id());
        LOG.debug("extracted applications for entity ref {}", idSelectionOptions.entityReference());
        return writeExtract(fileName, qry, request, response);
    }

    private SelectConditionStep<Record8<Long, String, String, String, String, String, String, String>> prepareExtractQuery(Condition condition) {

        return dsl
                .selectDistinct(
                        APPLICATION.ID.as("Waltz Id"),
                        APPLICATION.NAME.as("Name"),
                        APPLICATION.ASSET_CODE.as("Asset Code"),
                        ORGANISATIONAL_UNIT.NAME.as("Org Unit"),
                        APPLICATION.KIND.as("Application Kind"),
                        APPLICATION.OVERALL_RATING.as("Overall Rating"),
                        APPLICATION.BUSINESS_CRITICALITY.as("Business Criticality"),
                        APPLICATION.LIFECYCLE_PHASE.as("Lifecycle Phase"))
                .from(APPLICATION)
                .innerJoin(ORGANISATIONAL_UNIT)
                .on(ORGANISATIONAL_UNIT.ID.eq(APPLICATION.ORGANISATIONAL_UNIT_ID))
                .where(condition.and(DSL.trueCondition())); // Ensure condition is not empty

    }
}
