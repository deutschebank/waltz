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
import org.finos.waltz.model.person.Person;
import org.finos.waltz.model.report_grid.ReportGridMember;
import org.finos.waltz.model.report_grid.ReportGridMemberCreateCommand;
import org.finos.waltz.model.report_grid.ReportGridMemberDeleteCommand;
import org.finos.waltz.model.report_grid.ReportGridMemberUpdateRoleCommand;
import org.finos.waltz.service.report_grid.ReportGridMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Set;

import static org.finos.waltz.common.Checks.checkNotNull;


@RestController
@RequestMapping(ReportGridMemberEndpointController.BASE_URL)
public class ReportGridMemberEndpointController {

    public static final String BASE_URL = "/api/report-grid-member";

    private final ReportGridMemberService reportGridMemberService;


    @Autowired
    public ReportGridMemberEndpointController(ReportGridMemberService reportGridMemberService) {
        checkNotNull(reportGridMemberService, "reportGridMemberService cannot be null");
        this.reportGridMemberService = reportGridMemberService;
    }


    @GetMapping("/grid-id/{id}")
    public Set<ReportGridMember> findForGridIdRoute(@PathVariable("id") long gridId) {
        return reportGridMemberService.findByGridId(gridId);
    }


    @GetMapping("/grid-id/{id}/people")
    public Set<Person> findPeopleForGridIdRoute(@PathVariable("id") long gridId) {
        return reportGridMemberService.findPeopleByGridId(gridId);
    }


    @PostMapping("/grid-id/{id}/update-role")
    public Set<ReportGridMember> updateUserRoleRoute(@PathVariable("id") long gridId,
                                                     @RequestBody ReportGridMemberUpdateRoleCommand command,
                                                     @AuthenticationPrincipal String username) throws InsufficientPrivelegeException {
        return reportGridMemberService.updateUserRole(
                gridId,
                command,
                username);
    }


    @Deprecated
    @PostMapping("/create")
    public int createRoute(@RequestBody ReportGridMemberCreateCommand command,
                           @AuthenticationPrincipal String username) throws InsufficientPrivelegeException {
        return reportGridMemberService.create(
                command,
                username);
    }

    @PostMapping("/update")
    public int updateRoute(@RequestBody ReportGridMemberCreateCommand command,
                           @AuthenticationPrincipal String username) throws InsufficientPrivelegeException {
        return reportGridMemberService.update(
                command,
                username);
    }


    @PostMapping("/delete")
    public boolean deleteRoute(@RequestBody ReportGridMemberDeleteCommand command,
                               @AuthenticationPrincipal String username) throws InsufficientPrivelegeException {
        return reportGridMemberService.delete(
                command,
                username);
    }
}