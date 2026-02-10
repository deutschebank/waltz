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
import org.finos.waltz.service.usage_info.DataTypeUsageService;
import org.finos.waltz.service.user.UserRoleService;
import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.IdSelectionOptions;
import org.finos.waltz.model.data_type_usage.DataTypeUsage;
import org.finos.waltz.model.tally.Tally;
import org.finos.waltz.model.usage_info.UsageInfo;
import org.finos.waltz.model.user.SystemRole;
import org.finos.waltz.web.WebUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.common.ListUtilities.newArrayList;
import static org.finos.waltz.model.EntityReference.mkRef;
import static org.finos.waltz.web.WebUtilities.*;

@RestController
@RequestMapping("/api/data-type-usage/")
public class DataTypeUsageController {


    private static final String BASE_URL = WebUtilities.mkPath("api", "data-type-usage");

    private final DataTypeUsageService dataTypeUsageService;
    private final UserRoleService userRoleService;

    @Autowired
    public DataTypeUsageController(DataTypeUsageService dataTypeUsageService, UserRoleService userRoleService) {
        checkNotNull(dataTypeUsageService, "dataTypeUsageService cannot be null");
        checkNotNull(userRoleService, "userRoleService cannot be null");

        this.dataTypeUsageService = dataTypeUsageService;
        this.userRoleService = userRoleService;
    }
    
    @GetMapping("entity/{kind}/{id}")
    public List<DataTypeUsage> findForEntity(@PathVariable("kind") String kind, @PathVariable("id") long id) {
        return dataTypeUsageService.findForEntity(mkRef(EntityKind.valueOf(kind), id));
    }

    @PostMapping("type")
    public List<DataTypeUsage> findForDataTypeSelector(@RequestBody IdSelectionOptions options) {
        return dataTypeUsageService.findForDataTypeSelector(options);
    }

    @PostMapping("type/stats")
    public List<Tally<String>> findUsageStatsForDataTypeSelector(@RequestBody IdSelectionOptions options) {
        return dataTypeUsageService.findUsageStatsForDataTypeSelector(options);
    }
    
    @PostMapping("selector")
    public Collection<DataTypeUsage> findForSelector(@RequestBody IdSelectionOptions options) {
        return dataTypeUsageService.findForAppIdSelector(EntityKind.APPLICATION, options);
    }
    
    @GetMapping("calculate-all/application")
    public boolean calculateForAllApplications(HttpServletRequest request) {
        requireRoleForSB(userRoleService, request, SystemRole.ADMIN);
        return dataTypeUsageService.recalculateForAllApplications();
    }

    @PostMapping("entity/{kind}/{id}/{typeId}")
    public List<DataTypeUsage> save(@PathVariable("kind") String kind,
                                    @PathVariable("id") long id,
                                    @PathVariable("typeId") long dataTypeId,
                                    @RequestBody UsageInfo[] usages,
                                    HttpServletRequest request) {
        requireRoleForSB(userRoleService, request, SystemRole.LOGICAL_DATA_FLOW_EDITOR);
        
        String user = getUsernameForSB(request);
        EntityReference ref = mkRef(EntityKind.valueOf(kind), id);

        dataTypeUsageService.save(
                ref,
                dataTypeId,
                newArrayList(usages),
                user);
        
        return dataTypeUsageService.findForEntityAndDataType(
                ref,
                dataTypeId);
    }
}
