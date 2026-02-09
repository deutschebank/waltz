/*
 * Waltz - Enterprise Architecture
 * Copyright (C) 2016, 2017, 2018, 2019, 2020 Waltz open source project
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
import org.finos.waltz.model.orgunit.OrganisationalUnit;
import org.finos.waltz.service.changelog.ChangeLogService;
import org.finos.waltz.service.orgunit.OrganisationalUnitService;
import org.finos.waltz.service.user.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.finos.waltz.common.Checks.checkNotNull;


@RestController
@RequestMapping("/api/org-unit")
public class OrganisationUnitEndpointController {

    // ChangeLogService and UserRoleService are injected but not used in the provided snippet.
    // They are kept here as they might be used in other parts of the original, larger class.
    // If they are confirmed to be unused, they can be safely removed.
    private final ChangeLogService changeLogService;
    private final UserRoleService userRoleService;

    private final OrganisationalUnitService service;


    @Autowired
    public OrganisationUnitEndpointController(OrganisationalUnitService service,
                                    ChangeLogService changeLogService,
                                    UserRoleService userRoleService) {
        checkNotNull(service, "service must not be null");
        checkNotNull(changeLogService, "changeLogService must not be null");
        checkNotNull(userRoleService, "userRoleService must not be null");

        this.service = service;
        this.changeLogService = changeLogService;
        this.userRoleService = userRoleService;
    }


    @GetMapping()
    public List<OrganisationalUnit> findAll() {
        return service.findAll();
    }


    @GetMapping("/{id}")
    public OrganisationalUnit getById(@PathVariable long id) {
        return service.getById(id);
    }


    @GetMapping("/search/{query}")
    public List<OrganisationalUnit> search(@PathVariable String query) {
        return service.search(query);
    }


    @PostMapping("/by-ids")
    public List<OrganisationalUnit> findByIds(@RequestBody Long[] ids) {
        return service.findByIds(ids);
    }


    @GetMapping("/related/{kind}/{id}")
    public List<OrganisationalUnit> findRelatedByEntityRef(@PathVariable String kind, @PathVariable long id) {
        EntityReference ref = EntityReference.mkRef(EntityKind.valueOf(kind), id);
        return service.findRelatedByEntityRef(ref);
    }


    @GetMapping("/{id}/descendants")
    public List<OrganisationalUnit> findDescendants(@PathVariable long id) {
        return service.findDescendants(id);
    }

}
