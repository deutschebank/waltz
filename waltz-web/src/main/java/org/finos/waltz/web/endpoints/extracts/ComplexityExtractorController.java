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
import org.finos.waltz.data.GenericSelectorFactory;
import org.finos.waltz.data.InlineSelectFieldFactory;
import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.IdSelectionOptions;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

import static org.finos.waltz.schema.Tables.COMPLEXITY;
import static org.finos.waltz.schema.Tables.COMPLEXITY_KIND;
import static java.lang.String.format;
import static org.finos.waltz.common.ListUtilities.newArrayList;
import static org.finos.waltz.web.WebUtilities.mkPath;
import static org.finos.waltz.web.endpoints.extracts.DirectQueryBasedExtractorUtilities.writeExtract;


@Controller
@RequestMapping("/data-extract/complexity/")
public class ComplexityExtractorController {

    private final GenericSelectorFactory genericSelectorFactory = new GenericSelectorFactory();

    private static final Field<String> ENTITY_NAME_FIELD = InlineSelectFieldFactory.mkNameField(
            COMPLEXITY.ENTITY_ID,
            COMPLEXITY.ENTITY_KIND,
            newArrayList(EntityKind.APPLICATION))
            .as("Entity Name");


    @Autowired
    public DSLContext dsl;


    @RequestMapping(value = "target-kind/{kind}/selector", method = RequestMethod.POST)
    public void findBySelector(
            @PathVariable("kind") String kind,
            @RequestBody IdSelectionOptions idSelectionOptions,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        var genericSelector = genericSelectorFactory.applyForKind(EntityKind.valueOf(kind), idSelectionOptions);

        SelectConditionStep<Record> qry = dsl
                .select(ENTITY_NAME_FIELD)
                .select(COMPLEXITY.ENTITY_ID)
                .select(COMPLEXITY_KIND.NAME.as("Complexity Kind"))
                .select(COMPLEXITY.SCORE.as("Score"))
                .select(COMPLEXITY.PROVENANCE)
                .from(COMPLEXITY)
                .innerJoin(COMPLEXITY_KIND).on(COMPLEXITY.COMPLEXITY_KIND_ID.eq(COMPLEXITY_KIND.ID))
                .where(COMPLEXITY.ENTITY_ID.in(genericSelector.selector())
                        .and(COMPLEXITY.ENTITY_KIND.eq(genericSelector.kind().name())));
        
        writeExtract(mkFilename(idSelectionOptions), qry, request, response);
    }


    private String mkFilename(IdSelectionOptions idSelectionOptions) {
        return format("%s-%d-complexity-scores", idSelectionOptions.entityReference().kind(), idSelectionOptions.entityReference().id());
    }

}
