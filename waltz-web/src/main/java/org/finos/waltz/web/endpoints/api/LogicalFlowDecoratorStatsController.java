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
import org.finos.waltz.model.IdSelectionOptions;
import org.finos.waltz.model.data_flow_decorator.DecoratorRatingSummary;
import org.finos.waltz.model.data_flow_decorator.LogicalFlowDecoratorStat;
import org.finos.waltz.model.data_flow_decorator.UpdateDataFlowDecoratorsAction;
import org.finos.waltz.model.datatype.DataTypeDecorator;
import org.finos.waltz.model.user.SystemRole;
import org.finos.waltz.service.data_flow_decorator.LogicalFlowDecoratorService;
import org.finos.waltz.service.data_type.DataTypeDecoratorService;
import org.finos.waltz.service.user.UserRoleService;
import org.finos.waltz.web.WebUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.common.CollectionUtilities.map;
import static org.finos.waltz.model.EntityKind.LOGICAL_DATA_FLOW;


@RestController
@RequestMapping("/api/logical-flow-decorator")
public class LogicalFlowDecoratorStatsController {

    private final LogicalFlowDecoratorService logicalFlowDecoratorService;
    private final DataTypeDecoratorService dataTypeDecoratorService;
    private final UserRoleService userRoleService;


    @Autowired
    public LogicalFlowDecoratorStatsController(LogicalFlowDecoratorService logicalFlowDecoratorService,
                                               DataTypeDecoratorService dataTypeDecoratorService,
                                               UserRoleService userRoleService) {
        checkNotNull(logicalFlowDecoratorService, "logicalFlowDecoratorService cannot be null");
        checkNotNull(dataTypeDecoratorService, "dataTypeDecoratorService cannot be null");
        checkNotNull(userRoleService, "userRoleService cannot be null");

        this.logicalFlowDecoratorService = logicalFlowDecoratorService;
        this.dataTypeDecoratorService = dataTypeDecoratorService;
        this.userRoleService = userRoleService;
    }


    @PostMapping("summarize-inbound")
    public List<DecoratorRatingSummary> summarizeInboundForSelector(@RequestBody IdSelectionOptions selectionOptions) {
        return logicalFlowDecoratorService.summarizeInboundForSelector(selectionOptions);
    }


    @PostMapping("summarize-outbound")
    public List<DecoratorRatingSummary> summarizeOutboundForSelector(@RequestBody IdSelectionOptions selectionOptions) {
        return logicalFlowDecoratorService.summarizeOutboundForSelector(selectionOptions);
    }


    @GetMapping("summarize-inbound")
    public List<DecoratorRatingSummary> summarizeForAll() {
        return logicalFlowDecoratorService.summarizeForAll();
    }


    @PostMapping("datatype-stats")
    public Set<LogicalFlowDecoratorStat> findDatatypeStatsForEntity(@RequestBody IdSelectionOptions selectionOptions) {
        return logicalFlowDecoratorService.findFlowsByDatatypeForEntity(selectionOptions);
    }


    @PostMapping("update/batch")
    public Set<DataTypeDecorator> updateDecoratorsBatch(@RequestBody List<UpdateDataFlowDecoratorsAction> actions, HttpServletRequest principal) {
        WebUtilities.requireRoleForSB(userRoleService, principal, SystemRole.BULK_FLOW_EDITOR);
        String user = WebUtilities.getUsernameForSB(principal);
        logicalFlowDecoratorService.addDecoratorsBatch(actions, user);
        return dataTypeDecoratorService.findByFlowIds(map(actions, UpdateDataFlowDecoratorsAction::flowId), LOGICAL_DATA_FLOW);
    }

}
