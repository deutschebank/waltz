package org.finos.waltz.web.endpoints.extracts;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.finos.waltz.model.CommonTableFields;
import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.EntityLifecycleStatus;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.ImmutableEntityReference;
import org.finos.waltz.schema.tables.ApplicationGroup;
import org.finos.waltz.schema.tables.ChangeInitiative;
import org.finos.waltz.schema.tables.EntityRelationship;
import org.finos.waltz.schema.tables.Involvement;
import org.finos.waltz.schema.tables.InvolvementKind;
import org.finos.waltz.schema.tables.Measurable;
import org.finos.waltz.schema.tables.Person;
import org.finos.waltz.schema.tables.RelationshipKind;
import org.jooq.CommonTableExpression;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record2;
import org.jooq.Record3;
import org.jooq.Record5;
import org.jooq.Record9;
import org.jooq.Select;
import org.jooq.SelectConditionStep;
import org.jooq.SelectOnConditionStep;
import org.jooq.SelectSelectStep;
import org.jooq.Table;
import org.jooq.impl.CustomTable;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.finos.waltz.data.JooqUtilities.determineCommonTableFields;
import static org.finos.waltz.schema.Tables.*;
import static org.finos.waltz.schema.tables.ChangeInitiative.CHANGE_INITIATIVE;
import static org.finos.waltz.schema.tables.EntityRelationship.ENTITY_RELATIONSHIP;
import static org.finos.waltz.schema.tables.Measurable.MEASURABLE;
import static org.finos.waltz.web.endpoints.extracts.DirectQueryBasedExtractorUtilities.writeExtract;

@RestController
@RequestMapping("/data-extract/entity-relationships/")
public class EntityRelationshipsExtractorController {


    private static final InvolvementKind ik = INVOLVEMENT_KIND;
    private static final Person p = PERSON;
    private static final Involvement inv = INVOLVEMENT;
    private static final RelationshipKind rk = RELATIONSHIP_KIND;
    private static final EntityRelationship er = ENTITY_RELATIONSHIP;
    private static final Measurable m = MEASURABLE;
    private static final ChangeInitiative ci = CHANGE_INITIATIVE;
    private static final ApplicationGroup ag = APPLICATION_GROUP;

    private final DSLContext dsl;


    @Autowired
    public EntityRelationshipsExtractorController(DSLContext dsl) {
        this.dsl = dsl;
    }


    @GetMapping("kind/{kind}/id/{id}")
    public Object handleExtractForEntity(@PathVariable("kind") String kind,
                                       @PathVariable("id") long id,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws IOException {
        EntityReference refFromParams = ImmutableEntityReference.builder().kind(EntityKind.valueOf(kind)).id(id).build();

        // This part of the logic seems to fetch name and external id for the given entity.
        CommonTableFields<?> common = determineCommonTableFields(refFromParams.kind());

        Record2<String, String> selfEntity = dsl
                .select(common.nameField(),
                        common.externalIdField())
                .from(common.table())
                .where(common.idField().eq(refFromParams.id()))
                .fetchOne();

        ImmutableEntityReference ref = ImmutableEntityReference.copyOf(refFromParams)
                .withName(selfEntity.get(common.nameField()))
                .withDescription(selfEntity.get(common.externalIdField()));

        SelectConditionStep<Record5<Long, String, String, String, String>> fromRef = DSL
                .select(er.ID_B.as("id"),
                        er.KIND_B.as("kind"),
                        er.DESCRIPTION,
                        rk.CODE.as("rel_kind"),
                        rk.NAME.as("rel_name"))
                .from(er)
                .innerJoin(rk).on(rk.CODE.eq(er.RELATIONSHIP))
                .where(er.KIND_A.eq(ref.kind().name())
                        .and(er.ID_A.eq(ref.id())));

        SelectConditionStep<Record5<Long, String, String, String, String>> toRef = DSL
                .select(er.ID_A.as("id"),
                        er.KIND_A.as("kind"),
                        er.DESCRIPTION,
                        rk.CODE.as("rel_kind"),
                        rk.REVERSE_NAME.as("rel_name"))
                .from(er)
                .innerJoin(rk).on(rk.CODE.eq(er.RELATIONSHIP))
                .where(er.KIND_B.eq(ref.kind().name())
                        .and(er.ID_B.eq(ref.id())));

        CommonTableExpression<Record5<Long, String, String, String, String>> rels = DSL
                .name("rels")
                .fields("id", "kind", "description", "rel_kind", "rel_name")
                .as(fromRef.union(toRef));

        Select<Record9<Long, String, String, String, String, Long, String, String, String>> qry = dsl
                .with(rels)
                .select(DSL.val(ref.id()).as("self_id"),
                        DSL.val(ref.name().orElse("?")).as("self_name"),
                        DSL.val(ref.externalId().orElse("?")).as("self_external_id"),
                        rels.field("rel_kind", String.class).as("relationship_kind"),
                        rels.field("rel_name", String.class).as("relationship_name"),
                        rels.field("id", Long.class).as("other_id"),
                        rels.field("kind", String.class).as("other_kind"),
                        DSL.coalesce(m.NAME, ci.NAME, ag.NAME).as("other_name"),
                        DSL.coalesce(m.EXTERNAL_ID, ci.EXTERNAL_ID, ag.EXTERNAL_ID).as("other_external_id"))
                .from(rels)
                .leftJoin(m)
                .on(m.ID.eq(rels.field("id", Long.class))
                        .and(rels.field("kind", String.class).eq(EntityKind.MEASURABLE.name())))
                .leftJoin(ci)
                .on(ci.ID.eq(rels.field("id", Long.class))
                        .and(rels.field("kind", String.class).eq(EntityKind.CHANGE_INITIATIVE.name())))
                .leftJoin(ag)
                .on(ag.ID.eq(rels.field("id", Long.class))
                        .and(rels.field("kind", String.class).eq(EntityKind.APP_GROUP.name())));

        return writeExtract(
                "entity_relationships",
                qry,request,
                response);
    }


    @GetMapping("change-initiative/measurable")
    public Object extractForCItoMeasurable(
            @RequestParam(value = "category-id", required = false) Long categoryId,
            @RequestParam(value = "inv-kind-ids", required = false) List<Long> involvementKindIds,
            @RequestParam(value = "rel-kind-ids", required = false) List<Long> relationshipKindIds,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        Condition condition = getCondition(Optional.ofNullable(categoryId), relationshipKindIds);

        //returns all of kindA, with kindB if exists
        SelectOnConditionStep<Record> qry = prepareCiMeasurableQuery(
                involvementKindIds,
                condition);

        return writeExtract(
                "ci_measurable_relationships",
                qry,request,
                response);
    }


    private Condition getCondition(Optional<Long> categoryId, List<Long> relationshipKindIds) {
        Condition categoryCondition = categoryId
                .map(m.MEASURABLE_CATEGORY_ID::eq)
                .orElse(DSL.trueCondition());

        Condition relKindCondition = (relationshipKindIds == null || relationshipKindIds.isEmpty())
                ? DSL.trueCondition()
                : rk.ID.in(relationshipKindIds);

        return categoryCondition.and(relKindCondition);
    }

    private SelectOnConditionStep<Record> prepareCiMeasurableQuery(List<Long> involvementKinds,
                                                                   Condition condition) {
        Table<Record3<String, Long, String>> involvementsSubQry = dsl
                .select(ik.NAME.as("Involvement"),
                        inv.ENTITY_ID.as("Involved Entity Id"),
                        p.EMAIL.as("Email"))
                .from(inv)
                .innerJoin(p).on(inv.EMPLOYEE_ID.eq(p.EMPLOYEE_ID))
                .innerJoin(ik).on(inv.KIND_ID.eq(ik.ID))
                .where(involvementKinds == null ? DSL.falseCondition() : inv.KIND_ID.in(involvementKinds))
                .asTable();

        Table<Record> entityRelationshipsSubQry = DSL
                .select(rk.NAME.as("Relationship"),
                        rk.DESCRIPTION.as("Relationship Kind Description"),
                        er.ID_A.as("Id A"),
                        er.KIND_A.as("Kind A"),
                        er.DESCRIPTION.as("Relationship Description"),
                        m.ID.as("Viewpoint Id"),
                        m.NAME.as("Viewpoint"),
                        m.DESCRIPTION.as("Viewpoint Description"),
                        m.EXTERNAL_ID.as("Viewpoint External Id"))
                .select(involvementsSubQry.field("Involvement", String.class),
                        involvementsSubQry.field("Email", String.class))
                .from(er)
                .innerJoin(m).on(er.ID_B.eq(m.ID)
                        .and(er.KIND_B.eq(EntityKind.MEASURABLE.name())))
                .innerJoin(rk).on(er.RELATIONSHIP.eq(rk.CODE))
                .leftJoin(involvementsSubQry).on(er.ID_A.eq(involvementsSubQry.field("Involved Entity Id", Long.class))
                        .or(m.ID.eq(involvementsSubQry.field("Involved Entity Id", Long.class)))
                )
                .where(condition)
                .and(er.KIND_A.eq(EntityKind.CHANGE_INITIATIVE.name()))
                .and(m.ENTITY_LIFECYCLE_STATUS.eq(EntityLifecycleStatus.ACTIVE.name()))
                .and(er.KIND_A.eq(EntityKind.CHANGE_INITIATIVE.name()))
                .asTable();

        SelectSelectStep<Record> selectFields = dsl
                .selectDistinct(
                        ci.ID.as("Change Initiative Id"),
                        ci.EXTERNAL_ID.as("Change Initiative External Id"),
                        ci.KIND.as("Change Initiative Kind"),
                        ci.NAME.as("Change Initiative Name"),
                        ci.DESCRIPTION.as("Change Initiative Description"))
                .select(entityRelationshipsSubQry.field("Relationship", String.class),
                        entityRelationshipsSubQry.field("Relationship Kind Description", String.class),
                        entityRelationshipsSubQry.field("Relationship Description", String.class),
                        entityRelationshipsSubQry.field("Viewpoint Id", String.class),
                        entityRelationshipsSubQry.field("Viewpoint", String.class),
                        entityRelationshipsSubQry.field("Viewpoint Description", String.class),
                        entityRelationshipsSubQry.field("Viewpoint External Id", String.class),
                        entityRelationshipsSubQry.field("Involvement", String.class),
                        entityRelationshipsSubQry.field("Email", String.class));

        return selectFields
                .from(ci)
                .leftJoin(entityRelationshipsSubQry)
                .on(ci.ID.eq(entityRelationshipsSubQry.field("Id A", Long.class)));
    }
}
