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
import jakarta.servlet.http.HttpServletResponse;
import org.jooq.DSLContext;
import org.jooq.Record8;
import org.jooq.Select;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.Timestamp;

import static org.finos.waltz.schema.Tables.ACTOR;
import static org.finos.waltz.web.endpoints.extracts.DirectQueryBasedExtractorUtilities.writeExtract;


@RestController
@RequestMapping("data-extract/actor")
public class ActorExtractorController {

    @Autowired
    protected DSLContext dsl;

    @GetMapping("all")
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Select<Record8<Long, String, String, String, String, Timestamp, String, String>> qry = prepareExtractQuery();
        writeExtract("actors", qry, request, response);
    }


    private Select<Record8<Long, String, String, String, String, Timestamp, String, String>> prepareExtractQuery() {
        return dsl
                .select(ACTOR.ID.as("Waltz ID"),
                        ACTOR.NAME.as("Name"),
                        ACTOR.DESCRIPTION.as("Description"),
                        DSL.when(ACTOR.IS_EXTERNAL.isTrue(), "External").otherwise("Internal").as("External/Internal"),
                        ACTOR.EXTERNAL_ID.as("External ID"),
                        ACTOR.LAST_UPDATED_AT.as("Last Updated At"),
                        ACTOR.LAST_UPDATED_BY.as("Last Updated By"),
                        ACTOR.PROVENANCE.as("Provenance"))
                .from(ACTOR);
    }
}
