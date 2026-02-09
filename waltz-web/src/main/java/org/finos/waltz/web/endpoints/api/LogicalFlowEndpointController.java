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

package org.finos.waltz.web.endpoints.api;

import jakarta.servlet.http.HttpServletRequest;
import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.IdSelectionOptions;
import org.finos.waltz.model.Operation;
import org.finos.waltz.model.logical_flow.AddLogicalFlowCommand;
import org.finos.waltz.model.logical_flow.LogicalFlow;
import org.finos.waltz.model.logical_flow.LogicalFlowGraphSummary;
import org.finos.waltz.model.logical_flow.LogicalFlowStatistics;
import org.finos.waltz.model.logical_flow.LogicalFlowView;
import org.finos.waltz.model.logical_flow.UpdateReadOnlyCommand;
import org.finos.waltz.service.logical_flow.LogicalFlowService;
import org.finos.waltz.service.permission.permission_checker.FlowPermissionChecker;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.common.ListUtilities.map;
import static org.finos.waltz.model.EntityReference.mkRef;
import static org.finos.waltz.web.WebUtilities.*;


@RestController
@RequestMapping("/api/logical-flow")
public class LogicalFlowEndpointController {

    private static final Logger LOG = LoggerFactory.getLogger(LogicalFlowEndpointController.class);

    private final LogicalFlowService logicalFlowService;
    private final FlowPermissionChecker flowPermissionChecker;


    @Autowired
    public LogicalFlowEndpointController(LogicalFlowService logicalFlowService,
                                         FlowPermissionChecker flowPermissionChecker) {
        checkNotNull(logicalFlowService, "logicalFlowService must not be null");
        checkNotNull(flowPermissionChecker, "flowPermissionChecker must not be null");

        this.logicalFlowService = logicalFlowService;
        this.flowPermissionChecker = flowPermissionChecker;
    }

    @GetMapping("/entity/{kind}/{id}")
    public List<LogicalFlow> findByEntityReference(@PathVariable("kind") String kind, @PathVariable("id") long id) {
        return logicalFlowService.findByEntityReference(mkRef(EntityKind.valueOf(kind), id));
    }

    @PostMapping("/selector")
    public List<LogicalFlow> findBySelector(@RequestBody IdSelectionOptions options) {
        return logicalFlowService.findBySelector(options);
    }

    @PostMapping("/ids")
    public Collection<LogicalFlow> findByIds(@RequestBody List<Long> flowIds) {
        return logicalFlowService.findActiveByFlowIds(flowIds);
    }

    @PostMapping("/source-targets")
    public List<LogicalFlow> findBySourceAndTargets(@RequestBody List<SourceAndTargetRequest> sourceAndTargets) {
        List<Tuple2<EntityReference, EntityReference>> refs = map(
                sourceAndTargets,
                st -> Tuple.tuple(st.source(), st.target()));
        return logicalFlowService.findBySourceAndTargetEntityReferences(refs);
    }

    @PostMapping("/stats")
    public LogicalFlowStatistics calculateStats(@RequestBody IdSelectionOptions options) {
        return logicalFlowService.calculateStats(options);
    }

    @GetMapping("/entity/{kind}/{id}/permissions")
    public Set<Operation> findFlowPermissionsForParentEntity(@PathVariable("kind") String kind,
                                                              @PathVariable("id") long id,
                                                              HttpServletRequest principal) {
        return flowPermissionChecker.findFlowPermissionsForParentEntity(
                mkRef(EntityKind.valueOf(kind), id),
        getUsernameForSB(principal));
    }

    @GetMapping("/entity/{kind}/{id}/editable-flows")
    public Set<Long> findEditableFlowIdsForParentReference(@PathVariable("kind") String kind,
                                                            @PathVariable("id") long id,
                                                            HttpServletRequest principal) {
        return logicalFlowService.findEditableFlowIdsForParentReference(
                mkRef(EntityKind.valueOf(kind), id),
                getUsernameForSB(principal));
    }

    @GetMapping("/id/{id}/permissions")
    public Set<Operation> findPermissionsForFlow(@PathVariable("id") long id, HttpServletRequest principal) {
        return flowPermissionChecker.findPermissionsForFlow(id, getUsernameForSB(principal));
    }

    @PostMapping("/find-upstream-flows")
    public Collection<LogicalFlow> findUpstreamFlowsForEntityReferences(@RequestBody List<EntityReference> refs) {
        return logicalFlowService.findUpstreamFlowsForEntityReferences(refs);
    }

    @GetMapping("/{id}")
    public LogicalFlow getById(@PathVariable("id") long id) {
        return logicalFlowService.getById(id);
    }

    @GetMapping("/external-id/{externalId}")
    public LogicalFlow getByExternalId(@PathVariable("externalId") String externalId) {
        return logicalFlowService.getByExternalId(externalId);
    }

    @DeleteMapping("/{id}")
    public int removeFlow(@PathVariable("id") long flowId, HttpServletRequest principal) {
        String username = getUsernameForSB(principal);
        LOG.info("User: {} removing logical flow: {}", username, flowId);
        return logicalFlowService.removeFlow(flowId, username);
    }

    @PutMapping("/{id}/restore")
    public boolean restoreFlow(@PathVariable("id") long flowId, HttpServletRequest principal) {
        String username = getUsernameForSB(principal);
        LOG.info("User: {} restoring logical flow: {}", username, flowId);
        return logicalFlowService.restoreFlow(flowId, username);
    }

    @GetMapping("/cleanup-orphans")
    public Integer cleanupOrphans(HttpServletRequest principal) {
        String username = getUsernameForSB(principal);
        LOG.info("User: {}, requested logical flow cleanup", username);
        return logicalFlowService.cleanupOrphans();
    }

    @GetMapping("/cleanup-self-references")
    public int cleanupSelfReferencingFlows(HttpServletRequest principal) {
        String username = getUsernameForSB(principal);
        LOG.info("User: {}, requested self referencing logical flow cleanup", username);
        return logicalFlowService.cleanupSelfReferencingFlows();
    }

    @PostMapping
    public LogicalFlow addFlow(@RequestBody AddLogicalFlowCommand command, HttpServletRequest principal) {
        String username = getUsernameForSB(principal);
        LOG.info("User: {}, adding new logical flow: {}", username, command);
        return logicalFlowService.addFlow(command, username);
    }

    @PostMapping("/list")
    public Set<LogicalFlow> addFlows(@RequestBody List<AddLogicalFlowCommand> commands, HttpServletRequest principal) {
        String username = getUsernameForSB(principal);
        LOG.info("User: {}, adding new logical flows: {}", username, commands.size());
        return logicalFlowService.addFlows(commands, username);
    }

    @GetMapping("/entity/{kind}/{id}/data-type/{dtId}/graph-summary")
    public LogicalFlowGraphSummary getFlowGraphSummary(@PathVariable("kind") String kind,
                                                       @PathVariable("id") long id,
                                                       @PathVariable("dtId") long dtId) {
        return logicalFlowService.getFlowInfoByDirection(mkRef(EntityKind.valueOf(kind), id), dtId);
    }

    @PostMapping("/view")
    public LogicalFlowView getFlowView(@RequestBody IdSelectionOptions options) {
        return logicalFlowService.getFlowView(options);
    }

    @PostMapping("/update/read-only/{id}")
    public LogicalFlow updateReadOnly(@PathVariable("id") long flowId,
                                      @RequestBody UpdateReadOnlyCommand command,
                                      HttpServletRequest principal) throws IOException {
        String user = getUsernameForSB(principal);
        LogicalFlow resp = logicalFlowService.updateReadOnly(flowId, command.readOnly(), user);
        if (resp == null) {
            throw new IllegalArgumentException("No such Logical Flow exists");
        }
        return resp;
    }

    /**
     * This DTO is to help Spring deserialize the request body for the
     * findBySourceAndTargets endpoint.
     */
    private static class SourceAndTargetRequest {
        private EntityReference source;
        private EntityReference target;

        public EntityReference source() {
            return source;
        }

        public EntityReference target() {
            return target;
        }

        public void setSource(EntityReference source) {
            this.source = source;
        }

        public void setTarget(EntityReference target) {
            this.target = target;
        }
    }
}