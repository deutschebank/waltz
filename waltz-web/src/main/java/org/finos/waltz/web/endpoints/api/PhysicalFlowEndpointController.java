/*
 * Waltz - Enterprise Architecture
 * Copyright (C) 2016, 2017, 2018, 2019, 2024 Waltz open source project
 * See README.md for more information
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at_
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

import com.fasterxml.jackson.databind.JsonMappingException;
import org.finos.waltz.common.exception.InsufficientPrivelegeException;
import org.finos.waltz.model.*;
import org.finos.waltz.model.physical_flow.*;
import org.finos.waltz.model.user.SystemRole;
import org.finos.waltz.service.logical_flow.LogicalFlowService;
import org.finos.waltz.service.permission.permission_checker.FlowPermissionChecker;
import org.finos.waltz.service.physical_flow.PhysicalFlowService;
import org.finos.waltz.service.physical_flow.PhysicalFlowUploadService;
import org.finos.waltz.service.user.UserRoleService;
import org.finos.waltz.web.WebUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.web.WebUtilities.getLong;
import static org.finos.waltz.web.WebUtilities.getUsername;

@RestController
@RequestMapping("/api/physical-flow/")
public class PhysicalFlowEndpointController {

    private static final Logger LOG = LoggerFactory.getLogger(PhysicalFlowEndpointController.class);
    private static final Pattern ERROR_PATTERN = Pattern.compile(".*required attributes.*\\[(.*)\\].*");

    private final PhysicalFlowService physicalFlowService;
    private final UserRoleService userRoleService;
    private final LogicalFlowService logicalFlowService;
    private final PhysicalFlowUploadService physicalFlowUploadService;
    private final FlowPermissionChecker flowPermissionChecker;


    @Autowired
    public PhysicalFlowEndpointController(PhysicalFlowService physicalFlowService,
                                PhysicalFlowUploadService physicalFlowUploadService,
                                LogicalFlowService logicalFlowService,
                                UserRoleService userRoleService,
                                FlowPermissionChecker flowPermissionChecker) {
        checkNotNull(physicalFlowService, "physicalFlowService cannot be null");
        checkNotNull(physicalFlowUploadService, "physicalFlowUploadService cannot be null");
        checkNotNull(userRoleService, "userRoleService cannot be null");
        checkNotNull(logicalFlowService, "logicalFlowService cannot be null");
        checkNotNull(flowPermissionChecker, "flowPermissionService cannot be null");

        this.physicalFlowService = physicalFlowService;
        this.physicalFlowUploadService = physicalFlowUploadService;
        this.userRoleService = userRoleService;
        this.logicalFlowService = logicalFlowService;
        this.flowPermissionChecker = flowPermissionChecker;
    }


    @GetMapping("/id/{id}")
    public PhysicalFlow getById(@PathVariable("id") long id) {
        return physicalFlowService.getById(id);
    }

    @GetMapping("/entity/{kind}/{id}")
    public List<PhysicalFlow> findByEntityReference(@PathVariable("kind") String kind, @PathVariable("id") long id) {
        return physicalFlowService.findByEntityReference(EntityReference.mkRef(EntityKind.valueOf(kind), id));
    }

    @GetMapping("/entity/{kind}/{id}/produces")
    public List<PhysicalFlow> findByProducerEntityReference(@PathVariable("kind") String kind, @PathVariable("id") long id) {
        return physicalFlowService.findByProducerEntityReference(EntityReference.mkRef(EntityKind.valueOf(kind), id));
    }

    @GetMapping("/entity/{kind}/{id}/consumes")
    public List<PhysicalFlow> findByConsumerEntityReference(@PathVariable("kind") String kind, @PathVariable("id") long id) {
        return physicalFlowService.findByConsumerEntityReference(EntityReference.mkRef(EntityKind.valueOf(kind), id));
    }

    @GetMapping("/specification/{id}")
    public List<PhysicalFlow> findBySpecificationId(@PathVariable("id") long id) {
        return physicalFlowService.findBySpecificationId(id);
    }

    @GetMapping("/external-id/{externalId}")
    public List<PhysicalFlow> findByExternalId(@PathVariable("externalId") String externalId) {
        return physicalFlowService.findByExternalId(externalId);
    }

    @PostMapping("/selector")
    public Collection<PhysicalFlow> findBySelector(@RequestBody IdSelectionOptions options) {
        return physicalFlowService.findBySelector(options);
    }

    @GetMapping("/underlying/logical-flow/{flowId}")
    public Collection<PhysicalFlowInfo> findUnderlyingPhysicalFlows(@PathVariable("flowId") long flowId) {
        return physicalFlowService.findUnderlyingPhysicalFlows(flowId);
    }

    @PostMapping("/merge/from/{fromId}/to/{toId}")
    public boolean merge(@PathVariable("fromId") long fromId, @PathVariable("toId") long toId, Principal principal) {
        return physicalFlowService.merge(fromId, toId, principal.getName());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PhysicalFlowCreateCommandResponse createFlow(@RequestBody PhysicalFlowCreateCommand command, Principal principal) throws IOException, InsufficientPrivelegeException {
        String username = principal.getName();
        physicalFlowService.checkLogicalFlowPermission(EntityReference.mkRef(EntityKind.LOGICAL_DATA_FLOW, command.logicalFlowId()), username);
        return physicalFlowService.create(command, username);
    }

    @PostMapping("/id/{id}/spec-definition")
    public int updateSpecDefinitionId(@PathVariable("id") long flowId,
                                      @RequestBody PhysicalFlowSpecDefinitionChangeCommand command,
                                      Principal principal) throws IOException {
        // WebUtilities.requireRole(userRoleService, request, SystemRole.LOGICAL_DATA_FLOW_EDITOR);
        // Consider using Spring Security's @PreAuthorize("hasRole('LOGICAL_DATA_FLOW_EDITOR')")
        return physicalFlowService.updateSpecDefinitionId(principal.getName(), flowId, command);
    }

    @PostMapping("/id/{id}/attribute")
    public int updateAttribute(@PathVariable("id") long flowId,
                               @RequestBody SetAttributeCommand command,
                               Principal principal) throws IOException, InsufficientPrivelegeException {
        String username = principal.getName();
        physicalFlowService.checkHasPermission(command.entityReference().id(), username);
        return physicalFlowService.updateAttribute(username, command);
    }

    @DeleteMapping("/{id}")
    public PhysicalFlowDeleteCommandResponse deleteFlow(@PathVariable("id") long flowId, Principal principal) throws InsufficientPrivelegeException {
        String username = principal.getName();
        physicalFlowService.checkHasPermission(flowId, username);
        ImmutablePhysicalFlowDeleteCommand deleteCommand = ImmutablePhysicalFlowDeleteCommand.builder()
                .flowId(flowId)
                .build();
        return physicalFlowService.delete(deleteCommand, username);
    }

    @PostMapping("/upload/validate")
    public ResponseEntity<List<PhysicalFlowUploadCommandResponse>> validateUpload(@RequestBody PhysicalFlowUploadCommand[] commands) throws IOException {
        // WebUtilities.requireRole(userRoleService, request, SystemRole.BULK_FLOW_EDITOR);
        // Consider using Spring Security's @PreAuthorize("hasRole('BULK_FLOW_EDITOR')")
        List<PhysicalFlowUploadCommandResponse> validation = physicalFlowUploadService.validate(Arrays.asList(commands));
        return ResponseEntity.ok(validation);
    }

    @PostMapping("/upload")
    public List<PhysicalFlowUploadCommandResponse> upload(@RequestBody PhysicalFlowUploadCommand[] commands, Principal principal) throws Exception {
        // WebUtilities.requireRole(userRoleService, request, SystemRole.BULK_FLOW_EDITOR);
        // Consider using Spring Security's @PreAuthorize("hasRole('BULK_FLOW_EDITOR')")
        return physicalFlowUploadService.upload(principal.getName(), Arrays.asList(commands));
    }

    @GetMapping("/cleanup-orphans")
    public Integer cleanupOrphans(Principal principal) {
        // WebUtilities.requireRole(userRoleService, request, SystemRole.ADMIN);
        // Consider using Spring Security's @PreAuthorize("hasRole('ADMIN')")
        String username = principal.getName();
        LOG.info("User: {}, requested physical flow cleanup", username);
        return physicalFlowService.cleanupOrphans();
    }

    @GetMapping("/{id}/sibling-physical-flows-count")
    public int getPhysicalFlowsCountForAssociatedLogicalFlow(@PathVariable("id") long physicalFlowId){
        return physicalFlowService.getPhysicalFlowsCountForAssociatedLogicalFlow(physicalFlowId);
    }

}
