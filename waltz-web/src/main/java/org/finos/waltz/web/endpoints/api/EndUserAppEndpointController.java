/*
 * Waltz - Enterprise Architecture
 * Copyright (C) 2016, 2017, 2018, 2019, 2024 Waltz open source project
 * See README.md for more information
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License at
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
import org.finos.waltz.model.application.AppRegistrationResponse;
import org.finos.waltz.model.changelog.ChangeLogComment;
import org.finos.waltz.model.enduserapp.EndUserApplication;
import org.finos.waltz.model.tally.Tally;
import org.finos.waltz.service.end_user_app.EndUserAppService;
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

import static org.finos.waltz.web.WebUtilities.*;

@RestController
@RequestMapping("/api/end-user-application/")
public class EndUserAppEndpointController {

    private static final String BASE_URL = mkPath("api", "end-user-application");

    private final EndUserAppService endUserAppService;

    @Autowired
    public EndUserAppEndpointController(EndUserAppService endUserAppService) {
        this.endUserAppService = endUserAppService;
    }

    @GetMapping()
    public List<EndUserApplication> findAll() {
        return endUserAppService.findAll();
    }
    
    @PostMapping("selector")
    public List<EndUserApplication> findBySelector(@RequestBody IdSelectionOptions selectionOptions) {
        return endUserAppService.findBySelector(selectionOptions);
    }
    
    @GetMapping("id/{id}")
    public EndUserApplication getById(@PathVariable("id") long id) {
        return endUserAppService.getById(id);
    }
    
    @GetMapping("count-by/org-unit")
    public Collection<Tally<Long>> countByOrgUnit() {
        return endUserAppService.countByOrgUnitId();
    }
    
    @PostMapping("promote/{id}")
    public AppRegistrationResponse promoteToApplication(@PathVariable("id") long id,
                                                        @RequestBody(required = false) ChangeLogComment comment,
                                                        Principal principal) {
        return endUserAppService.promoteToApplication(
                id,
                comment,
                principal.getName());
    }

}
