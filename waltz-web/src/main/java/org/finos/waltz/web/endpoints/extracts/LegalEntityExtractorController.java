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
import org.finos.waltz.data.InlineSelectFieldFactory;
import org.finos.waltz.model.EntityKind;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Record9;
import org.jooq.SelectSeekStep2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.sql.Timestamp;

import static java.lang.String.format;
import static org.finos.waltz.common.ListUtilities.newArrayList;
import static org.finos.waltz.schema.Tables.LEGAL_ENTITY;
import static org.finos.waltz.schema.Tables.LEGAL_ENTITY_RELATIONSHIP;
import static org.finos.waltz.schema.Tables.LEGAL_ENTITY_RELATIONSHIP_KIND;
import static org.finos.waltz.web.endpoints.extracts.DirectQueryBasedExtractorUtilities.writeExtract;


@Controller
@RequestMapping("/data-extract/legal-entity/")
public class LegalEntityExtractorController {


    private static final Field<String> ENTITY_NAME_FIELD = InlineSelectFieldFactory.mkNameField(
                    LEGAL_ENTITY_RELATIONSHIP.TARGET_ID,
                    LEGAL_ENTITY_RELATIONSHIP.TARGET_KIND,
                    newArrayList(EntityKind.APPLICATION))
            .as("entity_name");

    private static final Field<String> ENTITY_EXT_ID_FIELD = InlineSelectFieldFactory.mkExternalIdField(
                    LEGAL_ENTITY_RELATIONSHIP.TARGET_ID,
                    LEGAL_ENTITY_RELATIONSHIP.TARGET_KIND,
                    newArrayList(EntityKind.APPLICATION))
            .as("entity_external_id");

    private final DSLContext dsl;
    
    @Autowired
    public LegalEntityExtractorController(DSLContext dsl) {
        this.dsl = dsl;
    }


    @RequestMapping(method = RequestMethod.GET, value = "target-ref/{kind}/{id}")
    public Object findByTargetRef(HttpServletResponse response,
                                HttpServletRequest request,
                                @PathVariable("kind") String kind,
                                @PathVariable("id") long id) throws IOException {

        SelectSeekStep2<Record9<Long, String, String, String, String, String, Timestamp, String, String>, String, String> qry = dsl
                .selectDistinct(
                        LEGAL_ENTITY.ID.as("Legal Entity Id"),
                        LEGAL_ENTITY.NAME.as("Legal Entity Name"),
                        LEGAL_ENTITY.DESCRIPTION.as("Legal Entity Description"),
                        LEGAL_ENTITY.EXTERNAL_ID.as("Legal Entity External Id"),
                        LEGAL_ENTITY_RELATIONSHIP_KIND.NAME.as("Relationship Kind"),
                        LEGAL_ENTITY_RELATIONSHIP.DESCRIPTION.as("Comment"),
                        LEGAL_ENTITY_RELATIONSHIP.LAST_UPDATED_AT.as("Last Updated At"),
                        LEGAL_ENTITY_RELATIONSHIP.LAST_UPDATED_BY.as("Last Updated By"),
                        LEGAL_ENTITY_RELATIONSHIP.PROVENANCE.as("Provenance"))
                .from(LEGAL_ENTITY_RELATIONSHIP)
                .innerJoin(LEGAL_ENTITY_RELATIONSHIP_KIND)
                .on(LEGAL_ENTITY_RELATIONSHIP_KIND.ID.eq(LEGAL_ENTITY_RELATIONSHIP.RELATIONSHIP_KIND_ID)
                        .and(LEGAL_ENTITY_RELATIONSHIP_KIND.TARGET_KIND.eq(LEGAL_ENTITY_RELATIONSHIP.TARGET_KIND)))
                .innerJoin(LEGAL_ENTITY)
                .on(LEGAL_ENTITY.ID.eq(LEGAL_ENTITY_RELATIONSHIP.LEGAL_ENTITY_ID))
                .where(LEGAL_ENTITY_RELATIONSHIP.TARGET_KIND.eq(kind)
                        .and(LEGAL_ENTITY_RELATIONSHIP.TARGET_ID.eq(id)))
                .orderBy(LEGAL_ENTITY.NAME, LEGAL_ENTITY_RELATIONSHIP_KIND.NAME);

        String filename = format("legal-entities-%s/%s", kind, id);

         return writeExtract(
                filename,
                qry,request,
                response);
    }


    @RequestMapping(method = RequestMethod.GET, value = "id/{id}")
    public Object findByLegalEntityId(HttpServletRequest request, HttpServletResponse response,
                                    @PathVariable("id") long id) throws IOException {

        String legalEntityName = dsl
                .select(LEGAL_ENTITY.NAME)
                .from(LEGAL_ENTITY)
                .where(LEGAL_ENTITY.ID.eq(id))
                .fetchOne(LEGAL_ENTITY.NAME);

        SelectSeekStep2<Record, String, String> qry = getRelationshipsForLegalEntity(id);

        String filename = format("%s-related-entities", legalEntityName);

        return writeExtract(
                filename,
                qry,request,
                response);
    }

    private SelectSeekStep2<Record, String, String> getRelationshipsForLegalEntity(Long id) {
        Field<String> targetEntityName = ENTITY_NAME_FIELD.as("Target Entity Name");
        return dsl
                .select(LEGAL_ENTITY_RELATIONSHIP.TARGET_ID.as("Target Entity Id"),
                        LEGAL_ENTITY_RELATIONSHIP.TARGET_KIND.as("Target Entity Kind"))
                .select(targetEntityName)
                .select(LEGAL_ENTITY_RELATIONSHIP_KIND.NAME.as("Relationship Kind"),
                        LEGAL_ENTITY_RELATIONSHIP.DESCRIPTION.as("Comment"),
                        LEGAL_ENTITY_RELATIONSHIP.LAST_UPDATED_AT.as("Last Updated At"),
                        LEGAL_ENTITY_RELATIONSHIP.LAST_UPDATED_BY.as("Last Updated By"),
                        LEGAL_ENTITY_RELATIONSHIP.PROVENANCE.as("Provenance"))
                .from(LEGAL_ENTITY_RELATIONSHIP)
                .innerJoin(LEGAL_ENTITY_RELATIONSHIP_KIND)
                .on(LEGAL_ENTITY_RELATIONSHIP_KIND.ID.eq(LEGAL_ENTITY_RELATIONSHIP.RELATIONSHIP_KIND_ID)
                        .and(LEGAL_ENTITY_RELATIONSHIP_KIND.TARGET_KIND.eq(LEGAL_ENTITY_RELATIONSHIP.TARGET_KIND)))
                .innerJoin(LEGAL_ENTITY)
                .on(LEGAL_ENTITY.ID.eq(LEGAL_ENTITY_RELATIONSHIP.LEGAL_ENTITY_ID))
                .where(dsl.renderInlined(LEGAL_ENTITY_RELATIONSHIP.LEGAL_ENTITY_ID.eq(id)))
                .orderBy(targetEntityName, LEGAL_ENTITY_RELATIONSHIP_KIND.NAME);
    }

}
