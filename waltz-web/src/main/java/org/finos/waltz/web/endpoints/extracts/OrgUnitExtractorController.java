/*
 * Waltz - Enterprise Architecture
 * Copyright (C) 2016, 2017, 2018, 2019, 2024 Waltz open source project
 * See README.md for more information
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License at
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
import org.finos.waltz.web.WebUtilities;
import org.jooq.DSLContext;
import org.jooq.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static org.finos.waltz.schema.tables.OrganisationalUnit.ORGANISATIONAL_UNIT;
import static org.finos.waltz.web.endpoints.extracts.DirectQueryBasedExtractorUtilities.writeExtract;


@RestController
@RequestMapping("/data-extract/org-units/")
public class OrgUnitExtractorController {

    public final DSLContext dsl;

    @Autowired
    public OrgUnitExtractorController(DSLContext dsl) {
        this.dsl = dsl;
    }

    @GetMapping
    public void extract(HttpServletRequest request,HttpServletResponse response) throws IOException {
        Select<?> query = prepareExtract();
        writeExtract(
                "organisational-units",
                query, request,
                response);
    }

    private Select<?> prepareExtract() {
        return dsl
                .select(
                        ORGANISATIONAL_UNIT.ID,
                        ORGANISATIONAL_UNIT.PARENT_ID.as("parentId"),
                        ORGANISATIONAL_UNIT.NAME.as("name"),
                        ORGANISATIONAL_UNIT.DESCRIPTION.as("description"),
                        ORGANISATIONAL_UNIT.EXTERNAL_ID.as("externalId"),
                        ORGANISATIONAL_UNIT.PROVENANCE.as("provenance"))
                .from(ORGANISATIONAL_UNIT);
    }

}
