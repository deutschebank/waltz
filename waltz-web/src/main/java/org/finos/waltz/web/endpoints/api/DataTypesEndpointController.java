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

import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.datatype.DataType;
import org.finos.waltz.model.datatype.DataTypeMigrationResult;
import org.finos.waltz.model.entity_search.EntitySearchOptions;
import org.finos.waltz.service.data_type.DataTypeService;
import org.finos.waltz.service.user.UserRoleService;
import org.finos.waltz.web.WebUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.finos.waltz.common.Checks.checkNotNull;

@RestController
@RequestMapping("/api/data-types")
public class DataTypesEndpointController {
    private static final String BASE_URL = WebUtilities.mkPath("api", "data-types");

    private final DataTypeService dataTypeService;
    private final UserRoleService userRoleService;

    @Autowired
    public DataTypesEndpointController(DataTypeService dataTypeService, UserRoleService userRoleService) {
        checkNotNull(dataTypeService, "dataTypeService must not be null");
        checkNotNull(userRoleService, "userRoleService must not be null");
        this.userRoleService = userRoleService;
        this.dataTypeService = dataTypeService;
    }

    @GetMapping()
    List<DataType> getAll() {
        return dataTypeService.findAll();
    }

    @PostMapping("/search")
    Collection<DataType> search(@RequestBody String body) {
        return dataTypeService.search(EntitySearchOptions
                .mkForEntity(EntityKind.DATA_TYPE, body));
    }

    @GetMapping("/id/{id}")
    DataType getDataTypeById(@PathVariable Long id) {
        return dataTypeService.getDataTypeById(id);
    }

    @GetMapping("/code/{code}")
    DataType getDataTypeByCode(@PathVariable String code) {
        return dataTypeService.getDataTypeByCode(code);
    }

    @GetMapping("/suggested/entity/{kind}/{id}")
    Set<DataType> findSuggestedByEntityRef(@PathVariable EntityKind kind, @PathVariable Long id) {
        return dataTypeService.findSuggestedByEntityRef(EntityReference.mkRef(kind, id));
    }

    @GetMapping("/parent-id/{id}")
    Collection<DataType> findSuggestedByEntityRef(@PathVariable Long id) {
        return dataTypeService.findByParentId(id);
    }

    @PostMapping("/migrate/{sourceDataTypeId}/to/{targetDataTypeId}")
    DataTypeMigrationResult migratePath(@PathVariable Long sourceDataTypeId,
                                        @PathVariable Long targetDataTypeId,
                                        @RequestParam Boolean removeSource) {
        //TODO ensureUserHasMigrateAdminRights(request);
        return dataTypeService.migrate(sourceDataTypeId, targetDataTypeId, removeSource);
    }

}
