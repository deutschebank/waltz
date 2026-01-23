package org.finos.waltz.web.endpoints.extracts;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.finos.waltz.common.ListUtilities;
import org.finos.waltz.data.InlineSelectFieldFactory;
import org.finos.waltz.model.EntityKind;
import org.finos.waltz.web.WebUtilities;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SelectHavingStep;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static org.finos.waltz.common.ListUtilities.newArrayList;
import static org.finos.waltz.schema.Tables.*;
import static org.finos.waltz.web.endpoints.extracts.DirectQueryBasedExtractorUtilities.writeExtract;

@RestController
@RequestMapping("/data-extract/flow-classification-rule/")
public class FlowClassificationRuleExtractorController {

    private static final Field<String> ENTITY_NAME_FIELD = InlineSelectFieldFactory.mkNameField(
            FLOW_CLASSIFICATION_RULE.SUBJECT_ENTITY_ID,
            FLOW_CLASSIFICATION_RULE.SUBJECT_ENTITY_KIND,
            newArrayList(EntityKind.APPLICATION, EntityKind.ACTOR));

    private static final Field<String> ENTITY_EXT_ID_FIELD = InlineSelectFieldFactory.mkExternalIdField(
            FLOW_CLASSIFICATION_RULE.SUBJECT_ENTITY_ID,
            FLOW_CLASSIFICATION_RULE.SUBJECT_ENTITY_KIND,
            newArrayList(EntityKind.APPLICATION, EntityKind.ACTOR));

    private final DSLContext dsl;

    @Autowired
    public FlowClassificationRuleExtractorController(DSLContext dsl) {
        this.dsl = dsl;
    }


    @GetMapping("all")
    public Object exportAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SelectHavingStep<?> qry = prepareExtractQuery(DSL.trueCondition());
        String fileName = "flow-classification-rules";
        return writeExtract(fileName, qry, request, response);
    }


    private SelectHavingStep<?> prepareExtractQuery(Condition condition) {

        return dsl
                .select(FLOW_CLASSIFICATION_RULE.SUBJECT_ENTITY_ID.as("Subject Id"),
                        FLOW_CLASSIFICATION_RULE.SUBJECT_ENTITY_KIND.as("Subject Kind"),
                        ENTITY_EXT_ID_FIELD.as("Subject External Id"),
                        ENTITY_NAME_FIELD.as("Subject Name"))
                .select(DATA_TYPE.NAME.as("Data Type"),
                        DATA_TYPE.CODE.as("Data Type Code"))
                .select(FLOW_CLASSIFICATION.NAME.as("Rating Name"),
                        FLOW_CLASSIFICATION.CODE.as("Rating Code"))
                .select(FLOW_CLASSIFICATION_RULE.PARENT_KIND.as("Scope Entity Kind"))
                .select(DSL.coalesce(ORGANISATIONAL_UNIT.NAME, APPLICATION.NAME, ACTOR.NAME).as("Scope Entity Name"))
                .select(DSL.coalesce(ORGANISATIONAL_UNIT.EXTERNAL_ID, APPLICATION.ASSET_CODE, ACTOR.EXTERNAL_ID).as("Scope Entity External Id"))
                .select(FLOW_CLASSIFICATION_RULE.EXTERNAL_ID.as("Statement External Id"),
                        FLOW_CLASSIFICATION_RULE.LAST_UPDATED_AT.as("Statement Last Updated At"),
                        FLOW_CLASSIFICATION_RULE.LAST_UPDATED_BY.as("Statement Last Updated By"),
                        FLOW_CLASSIFICATION_RULE.PROVENANCE.as("Statement Provenance"))
                .from(FLOW_CLASSIFICATION_RULE)
                .innerJoin(FLOW_CLASSIFICATION).on(FLOW_CLASSIFICATION_RULE.FLOW_CLASSIFICATION_ID.eq(FLOW_CLASSIFICATION.ID))
                .innerJoin(DATA_TYPE).on(DATA_TYPE.ID.eq(FLOW_CLASSIFICATION_RULE.DATA_TYPE_ID))
                .leftJoin(ORGANISATIONAL_UNIT).on(ORGANISATIONAL_UNIT.ID.eq(FLOW_CLASSIFICATION_RULE.PARENT_ID))
                    .and(FLOW_CLASSIFICATION_RULE.PARENT_KIND.eq(EntityKind.ORG_UNIT.name()))
                .leftJoin(APPLICATION).on(APPLICATION.ID.eq(FLOW_CLASSIFICATION_RULE.PARENT_ID))
                    .and(FLOW_CLASSIFICATION_RULE.PARENT_KIND.eq(EntityKind.APPLICATION.name()))
                .leftJoin(ACTOR).on(ACTOR.ID.eq(FLOW_CLASSIFICATION_RULE.PARENT_ID))
                    .and(FLOW_CLASSIFICATION_RULE.PARENT_KIND.eq(EntityKind.ACTOR.name()))
                .where(condition);
    }

}
