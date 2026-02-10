/*
 * Waltz - Enterprise Architecture_
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

package org.finos.waltz.web.endpoints.api;


import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.flow_classification_rule.FlowClassificationRuleView;
import org.finos.waltz.model.user.SystemRole;
import org.finos.waltz.service.flow_classification_rule.FlowClassificationRuleService;
import org.finos.waltz.service.flow_classification_rule.FlowClassificationRuleViewService;
import org.finos.waltz.service.user.UserRoleService;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.Entry;
import org.finos.waltz.model.IdSelectionOptions;
import org.finos.waltz.model.flow_classification_rule.DiscouragedSource;
import org.finos.waltz.model.flow_classification_rule.FlowClassificationRule;
import org.finos.waltz.model.flow_classification_rule.FlowClassificationRuleCreateCommand;
import org.finos.waltz.model.flow_classification_rule.FlowClassificationRuleUpdateCommand;
import org.finos.waltz.web.WebUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.model.EntityReference.mkRef;
import static org.finos.waltz.web.WebUtilities.getUsernameForSB;
import static org.finos.waltz.web.WebUtilities.mkPath;


@RestController
@RequestMapping("/api/flow-classification-rule/")
public class FlowClassificationRuleController {

    private static final Logger LOG = LoggerFactory.getLogger(FlowClassificationRuleEndpoint.class);
    public static final String BASE_URL = mkPath("api", "flow-classification-rule");

    private final FlowClassificationRuleService flowClassificationRuleService;
    private final UserRoleService userRoleService;
    private final FlowClassificationRuleViewService flowClassificationRuleViewService;
    private final ServletRequest httpServletRequest;


    @Autowired
    public FlowClassificationRuleController(FlowClassificationRuleService flowClassificationRuleService,
                                            UserRoleService userRoleService,
                                            FlowClassificationRuleViewService flowClassificationRuleViewService, ServletRequest httpServletRequest) {
        checkNotNull(flowClassificationRuleService, "flowClassificationRuleService must not be null");
        checkNotNull(flowClassificationRuleViewService, "flowClassificationRuleViewService must not be null");
        checkNotNull(userRoleService, "userRoleService cannot be null");

        this.flowClassificationRuleService = flowClassificationRuleService;
        this.userRoleService = userRoleService;
        this.flowClassificationRuleViewService = flowClassificationRuleViewService;
        this.httpServletRequest = httpServletRequest;
    }


    @GetMapping("entity-ref/{kind}/{id}")
    public List<FlowClassificationRule> findByEntityReference(@PathVariable("kind") String kind, @PathVariable("id") long id) {
        return flowClassificationRuleService.findByEntityReference(mkRef(EntityKind.valueOf(kind), id));
    }

    @GetMapping("app/{id}")
    public List<FlowClassificationRule> findByApplicationId(@PathVariable("id") long id) {
        return flowClassificationRuleService.findByApplicationId(id);
    }

    @PostMapping("discouraged")
    public List<DiscouragedSource> findDiscouragedSources(@RequestBody IdSelectionOptions options) {
        return flowClassificationRuleService.findDiscouragedSources(options);
    }

    @PostMapping("selector")
    public Set<FlowClassificationRule> findFlowClassificationRulesBySelector(@RequestBody IdSelectionOptions options) {
        return flowClassificationRuleService.findClassificationRules(options);
    }

    @GetMapping
    public List<FlowClassificationRule> findAll() {
        return flowClassificationRuleService.findAll();
    }

    @GetMapping("companion-rules/entity/id/{id}")
    public Set<FlowClassificationRule> findCompanionEntityRules(@PathVariable("id") long id) {
        return flowClassificationRuleService.findCompanionEntityRules(id);
    }

    @GetMapping("companion-rules/data-type/id/{id}")
    public Collection<FlowClassificationRule> findCompanionDataTypeRules(@PathVariable("id") long id) {
        return flowClassificationRuleService.findCompanionDataTypeRules(id);
    }

    @GetMapping("id/{id}")
    public FlowClassificationRule getById(@PathVariable("id") long id) {
        return flowClassificationRuleService.getById(id);
    }

    @PostMapping("view")
    public FlowClassificationRuleView getViewForSelector(@RequestBody IdSelectionOptions options) {
        return flowClassificationRuleViewService.getViewForSelector(options);
    }

    @GetMapping("recalculate-flow-ratings")
    public int recalculateFlowRatings(HttpServletRequest httpServletRequest) {
        WebUtilities.requireRoleForSB(userRoleService, httpServletRequest, SystemRole.ADMIN);
        String username = getUsernameForSB(httpServletRequest);
        LOG.info("Recalculating all flow ratings (requested by: {})", username);
        return flowClassificationRuleService.fastRecalculateAllFlowRatings();
    }

    @GetMapping("cleanup-orphans")
    public Integer cleanupOrphans(HttpServletRequest httpServletRequest) {
        WebUtilities.requireRoleForSB(userRoleService, httpServletRequest, SystemRole.ADMIN);
        String username = getUsernameForSB(httpServletRequest);
        LOG.info("User: {}, requested auth source cleanup", username);
        return flowClassificationRuleService.cleanupOrphans(username);
    }

    @PostMapping
    public String insert(@RequestBody FlowClassificationRuleCreateCommand command, HttpServletRequest httpServletRequest) {
        WebUtilities.requireRoleForSB(userRoleService, httpServletRequest, SystemRole.AUTHORITATIVE_SOURCE_EDITOR);
        String username = getUsernameForSB(httpServletRequest);
        flowClassificationRuleService.insert(command, username);
        return "done";
    }

    @PutMapping
    public String update(@RequestBody FlowClassificationRuleUpdateCommand command, HttpServletRequest httpServletRequest) {
        WebUtilities.requireRoleForSB(userRoleService, httpServletRequest, SystemRole.AUTHORITATIVE_SOURCE_EDITOR);
        String username = getUsernameForSB(httpServletRequest);
        flowClassificationRuleService.update(command, username);
        return "done";
    }

    @DeleteMapping("id/{id}")
    public String delete(@PathVariable("id") long id, HttpServletRequest httpServletRequest) {
        WebUtilities.requireRoleForSB(userRoleService, httpServletRequest, SystemRole.AUTHORITATIVE_SOURCE_EDITOR);
        String username = getUsernameForSB(httpServletRequest);
        flowClassificationRuleService.remove(id, username);
        return "done";
    }

    @PostMapping("data-type/consumers")
    public List<Entry<EntityReference, Collection<EntityReference>>> calculateConsumersForDataTypeIdSelector(@RequestBody IdSelectionOptions options) {
        Map<EntityReference, Collection<EntityReference>> result = flowClassificationRuleService
                .calculateConsumersForDataTypeIdSelector(options);

        return WebUtilities.simplifyMapToList(result);
    }
}
