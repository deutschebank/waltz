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

import org.finos.waltz.model.IdSelectionOptions;
import org.finos.waltz.model.licence.Licence;
import org.finos.waltz.model.licence.SaveLicenceCommand;
import org.finos.waltz.model.tally.Tally;
import org.finos.waltz.model.user.SystemRole;
import org.finos.waltz.service.licence.LicenceService;
import org.finos.waltz.service.user.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.common.Checks.checkTrue;


@RestController
@RequestMapping("/api/licence")
public class LicenceController {

    private final LicenceService service;
    private final UserRoleService userRoleService;


    @Autowired
    public LicenceController(LicenceService service,
                             UserRoleService userRoleService) {
        checkNotNull(service, "service cannot be null");
        checkNotNull(userRoleService, "userRoleService cannot be null");
        this.service = service;
        this.userRoleService = userRoleService;
    }

    @GetMapping("all")
    public List<Licence> findAll() {
        return service.findAll();
    }

    @GetMapping("id/{id}")
    public Licence getById(@PathVariable("id") long id) {
        return service.getById(id);
    }

    @GetMapping("external-id/{externalId}")
    public Licence getByExternalId(@PathVariable("externalId") String externalId) {
        return service.getByExternalId(externalId);
    }

    @GetMapping("count/application")
    public List<Tally<Long>> countApplications() {
        return service.countApplications();
    }

    @PostMapping("selector")
    public List<Licence> findBySelector(@RequestBody IdSelectionOptions options) {
        return service.findBySelector(options);
    }

    @PostMapping("save")
    public boolean save(@RequestBody SaveLicenceCommand cmd, Principal principal) {
        checkHasEditPermissions(principal.getName());
        return service.save(cmd, principal.getName());
    }

    @DeleteMapping("id/{id}")
    public boolean remove(@PathVariable("id") long licenceId, Principal principal) {
        checkHasEditPermissions(principal.getName());
        return service.remove(licenceId, principal.getName());
    }

    private void checkHasEditPermissions(String username) {
        checkTrue(
                userRoleService.hasRole(username, SystemRole.LICENCE_ADMIN),
                "User does not have the required permissions to edit licences");
    }
}
