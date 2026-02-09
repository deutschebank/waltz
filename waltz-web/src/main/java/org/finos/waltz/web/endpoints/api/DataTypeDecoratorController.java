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

package org.finos.waltz.web.endpoints.api;


import jakarta.servlet.http.HttpServletRequest;
import org.finos.waltz.common.exception.InsufficientPrivelegeException;
import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.IdSelectionOptions;
import org.finos.waltz.model.Operation;
import org.finos.waltz.model.datatype.DataType;
import org.finos.waltz.model.datatype.DataTypeDecorator;
import org.finos.waltz.model.datatype.DataTypeDecoratorRatingCharacteristics;
import org.finos.waltz.model.datatype.DataTypeDecoratorRatingCharacteristicsRequest;
import org.finos.waltz.model.datatype.DataTypeUsageCharacteristics;
import org.finos.waltz.model.logical_flow.DataTypeDecoratorView;
import org.finos.waltz.service.data_type.DataTypeDecoratorService;
import org.finos.waltz.service.data_type.DataTypeService;
import org.finos.waltz.service.permission.permission_checker.FlowPermissionChecker;
import org.finos.waltz.web.action.UpdateDataTypeDecoratorAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.model.EntityReference.mkRef;
import static org.finos.waltz.web.WebUtilities.*;

@RestController
@RequestMapping("/api/data-type-decorator/")
public class DataTypeDecoratorController {

    public static final String BASE_URL = mkPath("api", "data-type-decorator");

    private final DataTypeDecoratorService dataTypeDecoratorService;
    private final FlowPermissionChecker flowPermissionChecker;
    private final DataTypeService dataTypeService;

    @Autowired
    public DataTypeDecoratorController(DataTypeDecoratorService dataTypeDecoratorService,
                                     DataTypeService dataTypeService,
                                     FlowPermissionChecker flowPermissionChecker) {
        checkNotNull(dataTypeDecoratorService, "DataTypeDecoratorService cannot be null");
        checkNotNull(flowPermissionChecker, "flowPermissionChecker cannot be null");
        checkNotNull(dataTypeService, "dataTypeService cannot be null");

        this.dataTypeDecoratorService = dataTypeDecoratorService;
        this.flowPermissionChecker = flowPermissionChecker;
        this.dataTypeService = dataTypeService;
    }
    
    @GetMapping("/entity/{kind}/{id}")
    public Collection<DataTypeDecorator> findByEntityReference(@PathVariable("kind") String kind, @PathVariable("id") long id) {
        EntityReference entityReference = mkRef(EntityKind.valueOf(kind), id);
        return dataTypeDecoratorService.findByEntityId(entityReference);
    }

    @PostMapping("/selector/targetKind/{targetKind}")
    public Collection<DataTypeDecorator> findBySelector(@PathVariable("targetKind") String targetKind, @RequestBody IdSelectionOptions options) {
        return dataTypeDecoratorService.findByEntityIdSelector(
                EntityKind.valueOf(targetKind),
                options);
    }

    @PostMapping("/flow-ids/kind/{kind}")
    public Collection<DataTypeDecorator> findByFlowIdsAndKind(@RequestBody Set<Long> flowIds, @PathVariable("kind") String kind) {
        return dataTypeDecoratorService.findByFlowIds(flowIds, EntityKind.valueOf(kind));
    }

    @GetMapping("/suggested/entity/{kind}/{id}")
    public Collection<DataType> findSuggestedByEntityRef(@PathVariable("kind") String kind, @PathVariable("id") long id) {
        EntityReference entityReference = mkRef(EntityKind.valueOf(kind), id);
        return dataTypeService.findSuggestedByEntityRef(entityReference);
    }

    @GetMapping("/entity/{kind}/{id}/usage-characteristics")
    public Collection<DataTypeUsageCharacteristics> findDatatypeUsageCharacteristics(@PathVariable("kind") String kind, @PathVariable("id") long id) {
        EntityReference entityReference = mkRef(EntityKind.valueOf(kind), id);
        return dataTypeDecoratorService.findDatatypeUsageCharacteristics(entityReference);
    }

    @GetMapping("/entity/{kind}/{id}/permissions")
    public Collection<Operation> findPermissions(@PathVariable("kind") String kind,
                                                 @PathVariable("id") long id,
                                                 HttpServletRequest httpServletRequest) {
        return flowPermissionChecker.findPermissionsForDecorator(
                mkRef(EntityKind.valueOf(kind), id),
                getUsernameForSB(httpServletRequest));
    }

    @GetMapping("/entity/{kind}/{id}/view")
    public DataTypeDecoratorView findDecoratorView(@PathVariable("kind") String kind, @PathVariable("id") long id) {
        return dataTypeDecoratorService.getDecoratorView( mkRef(EntityKind.valueOf(kind), id));
    }

    @PostMapping("/rating-characteristics")
    public Set<DataTypeDecoratorRatingCharacteristics> findDatatypeRatingCharacteristicsForSourceAndTarget(
            @RequestBody DataTypeDecoratorRatingCharacteristicsRequest request) {
        return dataTypeDecoratorService.findDatatypeRatingCharacteristicsForSourceAndTarget(
                request.source(),
                request.target());
    }

    @PostMapping("/save/entity/{kind}/{id}")
    public boolean updateDataTypes(@PathVariable("kind") String kind,
                                   @PathVariable("id") long id,
                                   @RequestBody UpdateDataTypeDecoratorAction action,
                                   HttpServletRequest httpServletRequest) throws InsufficientPrivelegeException {
        String username = getUsernameForSB(httpServletRequest);
        EntityReference entityReference = mkRef(EntityKind.valueOf(kind), id);

        checkHasPermissionToUpdateDataTypes(entityReference, username);

        return dataTypeDecoratorService.updateDecorators(
                username,
                action.entityReference(),
                action.addedDataTypeIds(),
                action.removedDataTypeIds());
    }

    private void checkHasPermissionToUpdateDataTypes(EntityReference ref,
                                                     String username) throws InsufficientPrivelegeException {
        Set<Operation> permissions = flowPermissionChecker.findPermissionsForDecorator(ref, username);
        flowPermissionChecker.verifyEditPerms(permissions, ref.kind(), username);
    }
}
