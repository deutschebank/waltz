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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.finos.waltz.data.InlineSelectFieldFactory;
import org.finos.waltz.data.assessment_definition.AssessmentDefinitionDao;
import org.finos.waltz.model.assessment_definition.AssessmentDefinition;
import org.finos.waltz.web.WebUtilities;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Record11;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import static org.finos.waltz.schema.Tables.ASSESSMENT_DEFINITION;
import static org.finos.waltz.schema.Tables.ASSESSMENT_RATING;
import static org.finos.waltz.schema.Tables.RATING_SCHEME_ITEM;
import static org.finos.waltz.web.endpoints.extracts.DirectQueryBasedExtractorUtilities.writeExtract;


@RestController // Changed from @Service
public class AssessmentRatingExtractorController { // Renamed class

    private static final Logger LOG = LoggerFactory.getLogger(AssessmentRatingExtractorController.class); // Renamed class in logger

    private final Field<String> entityNameField = InlineSelectFieldFactory.mkNameField(
            ASSESSMENT_RATING.ENTITY_ID,
            ASSESSMENT_RATING.ENTITY_KIND);

    private final Field<String> entityExtIdField = InlineSelectFieldFactory.mkExternalIdField(
            ASSESSMENT_RATING.ENTITY_ID,
            ASSESSMENT_RATING.ENTITY_KIND);

    private final Field<String> entityLifecycleField = InlineSelectFieldFactory.mkEntityLifecycleField(
            ASSESSMENT_RATING.ENTITY_ID,
            ASSESSMENT_RATING.ENTITY_KIND);

    private final AssessmentDefinitionDao assessmentDefinitionDao;
    private final DSLContext dsl; // Added dsl context as a field

    @Autowired
    public AssessmentRatingExtractorController(DSLContext dsl, AssessmentDefinitionDao assessmentDefinitionDao) {
        this.dsl = dsl; // Initialize dsl
        this.assessmentDefinitionDao = assessmentDefinitionDao;
    }


    // Removed register() method

    @PostMapping("/data-extract/assessment-rating/by-definition/{id}") // Spring endpoint annotation
    public Object extractAssessmentRatingsByDefinition(
            @PathVariable("id") long definitionId, // Get id from path variable
            HttpServletRequest request,
            HttpServletResponse response) throws IOException { // Inject HttpServletResponse

        AssessmentDefinition definition = assessmentDefinitionDao.getById(definitionId);
        SelectConditionStep<?> qry = prepareExtractQuery(definitionId);
        String fileName = String.format("assessment-ratings-for-%s",
                definition.name().toLowerCase());
        LOG.debug("extracted assessment ratings for definition {}", definition.name());
        return writeExtract(fileName, qry, request, response);
    }


    private SelectConditionStep<Record11<Long, String, String, String, String, String, String, String, Timestamp, String, String>> prepareExtractQuery(Long definitionId) {

        return dsl
                .selectDistinct(
                        ASSESSMENT_RATING.ENTITY_ID.as("Waltz Id"),
                        entityExtIdField.as("External Id"),
                        entityNameField.as("Name"),
                        entityLifecycleField.as("Lifecycle status"),
                        RATING_SCHEME_ITEM.CODE.as("Code"),
                        RATING_SCHEME_ITEM.NAME.as("Rating Name"),
                        RATING_SCHEME_ITEM.DESCRIPTION.as("Comment"),
                        ASSESSMENT_RATING.DESCRIPTION.as("User Comment"),
                        ASSESSMENT_RATING.LAST_UPDATED_AT.as("Last Updated At"),
                        ASSESSMENT_RATING.LAST_UPDATED_BY.as("Last Updated By"),
                        ASSESSMENT_DEFINITION.NAME.as("Definition Name"))
                .from(ASSESSMENT_RATING)
                .innerJoin(RATING_SCHEME_ITEM)
                .on(ASSESSMENT_RATING.RATING_ID.eq(RATING_SCHEME_ITEM.ID))
                .innerJoin(ASSESSMENT_DEFINITION)
                .on(ASSESSMENT_DEFINITION.ID.eq(ASSESSMENT_RATING.ASSESSMENT_DEFINITION_ID))
                .where(ASSESSMENT_RATING.ASSESSMENT_DEFINITION_ID.eq(definitionId));
    }

    // Helper method to escape CSV values (simple implementation, might need more robust handling)
    private String escapeCsv(String value) {
        if (value.contains(",") || value.contains("\"") || value.contains("\n") || value.contains("\r")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}
