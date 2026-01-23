/*
 * Waltz - Enterprise Architecture
 * Copyright (C) 2016, 2017, 2018, 2019, 2024 Waltz open source project
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
import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.EntityLifecycleStatus;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record9;
import org.jooq.SelectConditionStep;
import org.jooq.SelectOnConditionStep;
import org.jooq.SelectOrderByStep;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.finos.waltz.schema.Tables.*;
import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.web.WebUtilities.mkPath;
import static org.finos.waltz.web.endpoints.extracts.DirectQueryBasedExtractorUtilities.writeExtract;


@RestController
@RequestMapping("/data-extract/application/person/")
public class PersonApplicationExtractorController {

    private static final Logger LOG = LoggerFactory.getLogger(PersonApplicationExtractorController.class);
    private final DSLContext  dsl;


    @Autowired
    public PersonApplicationExtractorController(DSLContext dsl) {
        this.dsl = dsl;
    }


    @GetMapping("{empId}")
    public void getApplicationsForPerson(
            @PathVariable("empId") String empId,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        String personName = dsl
                .select(PERSON.DISPLAY_NAME)
                .from(PERSON)
                .where(PERSON.EMPLOYEE_ID.eq(empId))
                .fetchOne(PERSON.DISPLAY_NAME);

        checkNotNull(personName, "Cannot find person with empId: %s", empId);

        String suggestedFilename = "Applications-associated-with-" + personName.replaceAll("[\\s,.]", "-");

        writeExtract(suggestedFilename, prepareExtractQuery(empId), request,response);
    }


    private SelectOrderByStep<Record9<Long, String, String, String, String, String, String, String, String>> prepareExtractQuery(String empId) {

        Condition appIsActive = APPLICATION.ENTITY_LIFECYCLE_STATUS.notEqual(EntityLifecycleStatus.REMOVED.name())
                .and(APPLICATION.IS_REMOVED.isFalse());

        SelectConditionStep<Record9<Long, String, String, String, String, String, String, String, String>> directInvolvementQry = mkBaseInvolvementSelect(dsl, empId)
                .where(dsl
                        .renderInlined(INVOLVEMENT.EMPLOYEE_ID.eq(empId) // This is redundant due to join condition in mkBaseInvolvementSelect but harmless
                                .and(appIsActive)));

        SelectConditionStep<Record9<Long, String, String, String, String, String, String, String, String>> oversightInvolvementQry = mkBaseInvolvementSelect(dsl, empId)
                .innerJoin(PERSON_HIERARCHY)
                .on(PERSON_HIERARCHY.EMPLOYEE_ID.eq(INVOLVEMENT.EMPLOYEE_ID))
                .where(dsl
                        .renderInlined(PERSON_HIERARCHY.MANAGER_ID.eq(empId) // This finds people managed by empId
                        .and(appIsActive)));

        return oversightInvolvementQry.union(directInvolvementQry);
    }


    private SelectOnConditionStep<Record9<Long, String, String, String, String, String, String, String, String>> mkBaseInvolvementSelect(
            DSLContext dsl,
            String empId) {

        Condition isDirect = DSL.exists(DSL
                .select(INVOLVEMENT.ENTITY_ID)
                .from(INVOLVEMENT)
                .where(INVOLVEMENT.EMPLOYEE_ID.eq(empId)
                        .and(INVOLVEMENT.ENTITY_ID.eq(APPLICATION.ID))));

        Field<String> directOrOversightField = DSL
                .when(isDirect, "Direct")
                .otherwise("Oversight");

        return dsl
                .selectDistinct(
                        APPLICATION.ID.as("Waltz Id"),
                        APPLICATION.NAME.as("Name"),
                        APPLICATION.ASSET_CODE.as("Asset Code"),
                        ORGANISATIONAL_UNIT.NAME.as("Org Unit"),
                        APPLICATION.KIND.as("Application Kind"),
                        APPLICATION.OVERALL_RATING.as("Overall Rating"),
                        APPLICATION.BUSINESS_CRITICALITY.as("Business Criticality"),
                        APPLICATION.LIFECYCLE_PHASE.as("Lifecycle Phase"),
                        directOrOversightField.as("Direct or Oversight"))
                .from(APPLICATION)
                .innerJoin(INVOLVEMENT)
                .on(INVOLVEMENT.ENTITY_ID.eq(APPLICATION.ID)
                        .and(INVOLVEMENT.ENTITY_KIND.eq(EntityKind.APPLICATION.name())))
                .innerJoin(ORGANISATIONAL_UNIT)
                .on(ORGANISATIONAL_UNIT.ID.eq(APPLICATION.ORGANISATIONAL_UNIT_ID));
    }
}
