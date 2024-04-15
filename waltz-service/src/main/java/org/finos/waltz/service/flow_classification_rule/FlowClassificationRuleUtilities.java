package org.finos.waltz.service.flow_classification_rule;

import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.FlowDirection;
import org.finos.waltz.model.datatype.FlowDataType;
import org.finos.waltz.model.entity_hierarchy.EntityHierarchy;
import org.finos.waltz.model.flow_classification_rule.FlowClassificationRuleVantagePoint;
import org.finos.waltz.model.rating.AuthoritativenessRatingValue;
import org.finos.waltz.schema.tables.records.LogicalFlowDecoratorRecord;
import org.jooq.UpdateConditionStep;
import org.jooq.impl.DSL;
import org.jooq.lambda.function.Function4;
import org.jooq.lambda.tuple.Tuple2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.finos.waltz.model.rating._AuthoritativenessRatingValue.DISCOURAGED;
import static org.finos.waltz.schema.tables.LogicalFlowDecorator.LOGICAL_FLOW_DECORATOR;
import static org.jooq.lambda.tuple.Tuple.tuple;

public class FlowClassificationRuleUtilities {


    protected static List<UpdateConditionStep<LogicalFlowDecoratorRecord>> mkOutboundRuleUpdateStmts(Map<Long, String> outboundRatingCodeByRuleId,
                                                                                                     Map<Long, Tuple2<Long, MatchOutcome>> lfdIdToOutboundRuleIdMap) {
        return lfdIdToOutboundRuleIdMap
                .entrySet()
                .stream()
                .map(kv -> {

                    Tuple2<Long, MatchOutcome> ruleAndMatchOutcome = kv.getValue();

                    Long ruleId = ruleAndMatchOutcome.v1();
                    String ratingCode = outboundRatingCodeByRuleId.get(ruleId);

                    AuthoritativenessRatingValue ratingValue = MatchOutcome.POSITIVE_MATCH.equals(ruleAndMatchOutcome.v2)
                            ? AuthoritativenessRatingValue.of(ratingCode)
                            : DISCOURAGED;

                    return DSL
                            .update(LOGICAL_FLOW_DECORATOR)
                            .set(LOGICAL_FLOW_DECORATOR.RATING, ratingValue.value())
                            .set(LOGICAL_FLOW_DECORATOR.FLOW_CLASSIFICATION_RULE_ID, ruleId)
                            .where(LOGICAL_FLOW_DECORATOR.ID.eq(kv.getKey()));
                })
                .collect(Collectors.toList());
    }

    protected static List<UpdateConditionStep<LogicalFlowDecoratorRecord>> mkInboundRuleUpdateStmts(Map<Long, String> inboundRatingCodeByRuleId,
                                                                                                  Map<Long, Tuple2<Long, MatchOutcome>> lfdIdToInboundRuleIdMap) {
        return lfdIdToInboundRuleIdMap
                .entrySet()
                .stream()
                .map(kv -> {

                    Tuple2<Long, MatchOutcome> ruleAndMatchOutcome = kv.getValue();

                    Long ruleId = ruleAndMatchOutcome.v1();
                    String ratingCode = inboundRatingCodeByRuleId.get(ruleId);

                    if (MatchOutcome.POSITIVE_MATCH.equals(ruleAndMatchOutcome.v2)) {
                        return DSL
                                .update(LOGICAL_FLOW_DECORATOR)
                                .set(LOGICAL_FLOW_DECORATOR.TARGET_INBOUND_RATING, AuthoritativenessRatingValue.of(ratingCode).value())
                                .set(LOGICAL_FLOW_DECORATOR.INBOUND_FLOW_CLASSIFICATION_RULE_ID, ruleId)
                                .where(LOGICAL_FLOW_DECORATOR.ID.eq(kv.getKey()));
                    } else {
                        return null; // For inbound rules we don't want to automatically discourage flows that are not covered
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }


    protected static Map<Long, Tuple2<Long, MatchOutcome>> applyVantagePoints(FlowDirection direction,
                                                                              List<FlowClassificationRuleVantagePoint> ruleVantagePoints,
                                                                              Set<FlowDataType> population,
                                                                              EntityHierarchy ouHierarchy,
                                                                              EntityHierarchy dtHierarchy) {

        Function4<FlowClassificationRuleVantagePoint, Set<Long>, Set<Long>, FlowDataType, MatchOutcome> matcher = determineMatcherFn(direction);

        Map<Long, Tuple2<Long, MatchOutcome>> lfdIdToRuleAndOutcomeMap = new HashMap<>();
        ruleVantagePoints
                .stream()
                .forEach(rvp -> {
                    Set<Long> childOUs = ouHierarchy.findChildren(rvp.vantagePoint().id());
                    Set<Long> childDTs = dtHierarchy.findChildren(rvp.dataType().id());
                    population.forEach(p -> {
                        Tuple2<Long, MatchOutcome> currentRuleAndOutcome = lfdIdToRuleAndOutcomeMap.get(p.lfdId());
                        if (currentRuleAndOutcome != null && currentRuleAndOutcome.v2 == MatchOutcome.POSITIVE_MATCH) {
                            return; // skip, already got a good match
                        }
                        MatchOutcome outcome = matcher.apply(rvp, childOUs, childDTs, p);
                        if (outcome == MatchOutcome.NOT_APPLICABLE) {
                            // skip
                        } else if (currentRuleAndOutcome == null) {
                            lfdIdToRuleAndOutcomeMap.put(p.lfdId(), tuple(rvp.ruleId(), outcome));
                        } else if (currentRuleAndOutcome.v2 == MatchOutcome.NEGATIVE_MATCH && outcome == MatchOutcome.POSITIVE_MATCH) {
                            // override result as we have a positive match
                            lfdIdToRuleAndOutcomeMap.put(p.lfdId(), tuple(rvp.ruleId(), MatchOutcome.POSITIVE_MATCH));
                        } else {
                            // skip, leave the map alone as a more specific negative rule id already exists
                        }
                    });
                });

        return lfdIdToRuleAndOutcomeMap;
    }

    enum MatchOutcome {
        NOT_APPLICABLE,
        NEGATIVE_MATCH,
        POSITIVE_MATCH

    }

    private static Function4<FlowClassificationRuleVantagePoint, Set<Long>, Set<Long>, FlowDataType, MatchOutcome> determineMatcherFn(FlowDirection direction) {
        Function4<FlowClassificationRuleVantagePoint,  Set<Long>,  Set<Long>, FlowDataType, MatchOutcome> inboundMatcher =
                (rvp, childOUs, childDTs, p) -> {
                    boolean subjectMatches = p.target().equals(rvp.subjectReference());
                    boolean scopeMatches = checkScopeMatches(rvp, childOUs, p.source(), p.sourceOuId());
                    boolean dtAndOuMatches = childDTs.contains(p.dtId()) && scopeMatches;
                    return determineOutcome(subjectMatches, dtAndOuMatches);
                };

        Function4<FlowClassificationRuleVantagePoint,  Set<Long>,  Set<Long>, FlowDataType, MatchOutcome> outboundMatcher =
                (rvp, childOUs, childDTs, p) -> {
                    boolean subjectMatches = p.source().equals(rvp.subjectReference());
                    boolean scopeMatches = checkScopeMatches(rvp, childOUs, p.target(), p.targetOuId());
                    boolean dtAndOuMatches = childDTs.contains(p.dtId()) && scopeMatches;
                    return determineOutcome(subjectMatches, dtAndOuMatches);
                };

        return direction == FlowDirection.INBOUND
                ? inboundMatcher
                : outboundMatcher;
    }

    private static boolean checkScopeMatches(FlowClassificationRuleVantagePoint rvp, Set<Long> childOUs, EntityReference scopeEntity, Long scopeEntityOuId) {
        if (rvp.vantagePoint().kind() == EntityKind.ORG_UNIT) {
            return scopeEntityOuId != null && childOUs.contains(scopeEntityOuId);
        } else {
            // point-to-point flows e.g. ACTOR or APPLICATION
            return scopeEntity.equals(rvp.vantagePoint());
        }
    }


    private static MatchOutcome determineOutcome(boolean subjectMatches,
                                                 boolean dtAndOuMatches) {
        if (subjectMatches && dtAndOuMatches) {
            return MatchOutcome.POSITIVE_MATCH;
        } else if (dtAndOuMatches) {
            return MatchOutcome.NEGATIVE_MATCH;
        } else {
            return MatchOutcome.NOT_APPLICABLE;
        }
    }

}