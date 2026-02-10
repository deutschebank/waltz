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

import org.finos.waltz.common.exception.InsufficientPrivelegeException;
import org.finos.waltz.common.exception.NotFoundException;
import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.IdSelectionOptions;
import org.finos.waltz.model.report_grid.*;
import org.finos.waltz.service.report_grid.ReportGridService;
import org.finos.waltz.web.endpoints.Endpoint;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.io.IOException;
import java.util.Set;

import static org.finos.waltz.common.Checks.checkNotNull;

@RestController
@RequestMapping(ReportGridEndpointController.BASE_URL)
public class ReportGridEndpointController {

    public static final String BASE_URL = "/api/report-grid";

    private final ReportGridService reportGridService;

    // Note: If Waltz uses a custom security context and not Spring Security's AuthenticationPrincipal,
    // you might need to adapt how the username is retrieved (e.g., via a custom argument resolver or a request header).
    // For this conversion, @AuthenticationPrincipal is used as a common Spring Security pattern.

    @Autowired
    public ReportGridEndpointController(ReportGridService reportGridService) {
        this.reportGridService = reportGridService;
    }


    @GetMapping("definition/all")
    public Set<ReportGridDefinition> findAllDefinitions() {
        return reportGridService.findAllDefinitions();
    }

    @GetMapping("definition/user")
    public Set<ReportGridDefinition> findGridDefinitionsForUser(@AuthenticationPrincipal String username) {
        return reportGridService.findGridDefinitionsForUser(username);
    }

    @GetMapping("info/user")
    public Set<ReportGridInfo> findGridInfoForUser(@AuthenticationPrincipal String username) {
        return reportGridService.findGridInfoForUser(username);
    }

    @PostMapping("view/id/{id}")
    public ReportGrid getViewByIdRoute(@PathVariable Long id,
                                       @RequestBody IdSelectionOptions selectionOptions,
                                       @AuthenticationPrincipal String username) throws IOException {
        checkNotNull(id, "ID cannot be null");
        checkNotNull(selectionOptions, "Selection options cannot be null");
        return reportGridService
                .getByIdAndSelectionOptions(
                        id,
                        selectionOptions,
                        username)
                .orElseThrow(() -> new NotFoundException("404", "ID not found"));
    }

    @GetMapping("definition/id/{id}")
    public ReportGridDefinition getDefinitionByIdRoute(@PathVariable Long id) throws IOException {
        checkNotNull(id, "ID cannot be null");
        return reportGridService.getGridDefinitionById(id);
    }

    @PostMapping("id/{id}/column-definitions/update")
    public ReportGridDefinition updateColumnDefsRoute(@PathVariable Long id,
                                                      @RequestBody ReportGridColumnDefinitionsUpdateCommand command,
                                                      @AuthenticationPrincipal String username) throws IOException, InsufficientPrivelegeException {
        checkNotNull(id, "ID cannot be null");
        checkNotNull(command, "Command cannot be null");
        return reportGridService.updateColumnDefinitions(
                id,
                command,
                username);
    }

    @PostMapping("create")
    public ReportGridInfo createRoute(@RequestBody ReportGridCreateCommand command,
                                      @AuthenticationPrincipal String username) throws IOException {
        checkNotNull(command, "Command cannot be null");
        return reportGridService.create(command, username);
    }

    @PostMapping("id/{id}/update")
    public ReportGridInfo updateRoute(@PathVariable Long id,
                                      @RequestBody ReportGridUpdateCommand command,
                                      @AuthenticationPrincipal String username) throws IOException, InsufficientPrivelegeException {
        checkNotNull(id, "ID cannot be null");
        checkNotNull(command, "Command cannot be null");
        return reportGridService.update(id, command, username);
    }

    @PostMapping("id/{id}/clone")
    public ReportGridInfo cloneRoute(@PathVariable Long id,
                                     @RequestBody ReportGridUpdateCommand command,
                                     @AuthenticationPrincipal String username) throws IOException, InsufficientPrivelegeException {
        checkNotNull(id, "ID cannot be null");
        checkNotNull(command, "Command cannot be null");
        return reportGridService.clone(id, command, username);
    }

    @DeleteMapping("id/{id}")
    public boolean removalRoute(@PathVariable Long id,
                                @AuthenticationPrincipal String username) throws InsufficientPrivelegeException {
        checkNotNull(id, "ID cannot be null");
        return reportGridService.remove(id, username);
    }

    @GetMapping("definition/owner")
    public Set<ReportGridDefinition> findDefinitionsForOwnerRoute(@AuthenticationPrincipal String username) {
        return reportGridService.findDefinitionsForOwner(username);
    }

    @GetMapping("additional-column-options/kind/{kind}")
    public Set<AdditionalColumnOptions> findAdditionalColumnOptionsForKindRoute(@PathVariable String kind) {
        checkNotNull(kind, "Kind cannot be null");
        return reportGridService.findAdditionalColumnOptionsForKind(EntityKind.valueOf(kind));
    }
}
