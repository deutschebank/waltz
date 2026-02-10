/*
 * Waltz - Enterprise Architecture
 * Copyright (C) 2016, 2017, 2018, 2019 Waltz open source project
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
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.finos.waltz.web.endpoints.api;

import jakarta.servlet.http.HttpServletRequest;
import org.finos.waltz.common.exception.InsufficientPrivelegeException;
import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.custom_environment.CustomEnvironment;
import org.finos.waltz.service.custom_environment.CustomEnvironmentService;
import org.finos.waltz.web.endpoints.auth.AuthenticationUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collection;

import static org.finos.waltz.web.WebUtilities.getUsername;


@RestController
@RequestMapping("/api/custom-environment")
public class CustomEnvironmentEndpointController {

    private final CustomEnvironmentService customEnvironmentService;


    @Autowired
    public CustomEnvironmentEndpointController(CustomEnvironmentService customEnvironmentService) {
        this.customEnvironmentService = customEnvironmentService;
    }


    @GetMapping("/all")
    public Collection<CustomEnvironment> findAll() {
        return customEnvironmentService.findAll();
    }

    @GetMapping("/owning-entity/kind/{kind}/id/{id}")
    public Collection<CustomEnvironment> findByOwningEntityReference(
            @PathVariable("kind") EntityKind kind,
            @PathVariable("id") long id) {
        return customEnvironmentService.findByOwningEntityRef(EntityReference.mkRef(kind, id));
    }

    @PostMapping("/create")
    public Long create(@RequestBody CustomEnvironment env, HttpServletRequest principal) throws InsufficientPrivelegeException {
        return customEnvironmentService.create(env, AuthenticationUtilities.getUsernameForSB(principal));
    }

    @DeleteMapping("/remove/id/{id}")
    public Boolean delete(@PathVariable("id") long id, HttpServletRequest principal) throws InsufficientPrivelegeException {
        return customEnvironmentService.remove(id, AuthenticationUtilities.getUsernameForSB(principal));
    }

}
