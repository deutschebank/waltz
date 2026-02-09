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

import org.finos.waltz.data.EntityReferenceNameResolver;
import org.finos.waltz.data.application.ApplicationIdSelectorFactory;
import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.web.WebUtilities;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Select;
import org.jooq.SelectConditionStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.model.IdSelectionOptions.mkOpts;
import static org.finos.waltz.schema.Tables.DATABASE_INFORMATION;
import static org.finos.waltz.schema.Tables.DATABASE_USAGE;
import static org.finos.waltz.schema.Tables.ORGANISATIONAL_UNIT;
import static org.finos.waltz.schema.tables.Application.APPLICATION;


@RestController
@RequestMapping("/data-extract/technology-database/")
public class TechnologyEOLDatabaseExtractorController {

    private static final Logger LOG = LoggerFactory.getLogger(TechnologyEOLDatabaseExtractor.class);
    private final EntityReferenceNameResolver nameResolver;
    private final ApplicationIdSelectorFactory applicationIdSelectorFactory = new ApplicationIdSelectorFactory();
    private final DSLContext dsl;
    @Autowired
    public TechnologyEOLDatabaseExtractorController(DSLContext dsl,
                                          EntityReferenceNameResolver nameResolver) { //This should be TechnologyEOLDatabaseExtractorController

        checkNotNull(nameResolver, "nameResolver cannot be null");
        checkNotNull(applicationIdSelectorFactory, "appIdSelectorFactory cannot be null");
        this.nameResolver = nameResolver;
        this.dsl = dsl;
    }


    @GetMapping("{kind}/{id}")
    public void EOLDatabaseExtractor(
            @PathVariable("kind") String kind,
            @PathVariable("id") long id,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        EntityReference ref = getReference(EntityKind.valueOf(kind), id);
        Select<Record1<Long>> appIdSelector = applicationIdSelectorFactory.apply(mkOpts(ref));

        SelectConditionStep<Record> qry = dsl
                .selectDistinct(ORGANISATIONAL_UNIT.NAME.as("Org Unit"))
                .select(APPLICATION.NAME.as("Application Name"), APPLICATION.ASSET_CODE.as("Asset Code"))
                .select(DATABASE_INFORMATION.DATABASE_NAME.as("Database Name"),
                        DATABASE_INFORMATION.INSTANCE_NAME.as("Instance Name"),
                        DATABASE_USAGE.ENVIRONMENT.as("DBMS Environment"),
                        DATABASE_INFORMATION.DBMS_VENDOR.as("DBMS Vendor"),
                        DATABASE_INFORMATION.DBMS_NAME.as("DBMS Name"),
                        DATABASE_INFORMATION.END_OF_LIFE_DATE.as("End of Life date"),
                        DATABASE_INFORMATION.LIFECYCLE_STATUS.as("Lifecycle"))
                .from(DATABASE_INFORMATION)
                .innerJoin(DATABASE_USAGE)
                .on(DATABASE_USAGE.DATABASE_ID.eq(DATABASE_INFORMATION.ID))
                .innerJoin(APPLICATION)
                .on(APPLICATION.ID.eq(DATABASE_USAGE.ENTITY_ID))
                .and(DATABASE_USAGE.ENTITY_KIND.eq(EntityKind.APPLICATION.name()))
                .innerJoin(ORGANISATIONAL_UNIT)
                .on(ORGANISATIONAL_UNIT.ID.eq(APPLICATION.ORGANISATIONAL_UNIT_ID))
                .where(APPLICATION.ID.in(appIdSelector))
                .and(APPLICATION.LIFECYCLE_PHASE.notEqual("RETIRED"));

        DirectQueryBasedExtractorUtilities.writeExtract(
                mkFilename(ref),
                qry,
                request,
                response);
    }


    private EntityReference getReference(EntityKind kind, long id) {
        EntityReference origRef = EntityReference.mkRef(kind, id);
        return nameResolver
                .resolve(origRef)
                .orElse(origRef);
    }


    private String mkFilename(EntityReference ref) {
        return sanitizeName(ref.name().orElse(ref.kind().name()))
                + "-technology-database-eol";
    }


    private String sanitizeName(String str) {
        return str
                .replace(".", "-")
                .replace(" ", "-")
                .replace(",", "-");

    }
}
