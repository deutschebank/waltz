/*
 * Waltz - Enterprise Architecture
 * Copyright (C) 2016, 2017, 2018, 2019, 2024 Waltz open source project
 * See README.md for more information
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.∫
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

import org.finos.waltz.service.report_grid.ReportGridFilterViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import static org.finos.waltz.web.WebUtilities.mkPath;

@Controller
@RequestMapping(ReportGridPageEndpointController.BASE_URL)
public class ReportGridPageEndpointController {

    protected static final String BASE_URL = "/page/report-grid-view/";

    private final ReportGridFilterViewService reportGridFilterViewService;


    @Autowired
    public ReportGridPageEndpointController(ReportGridFilterViewService reportGridFilterViewService) {
        this.reportGridFilterViewService = reportGridFilterViewService;
    }


    @GetMapping("recalculate/app-group-id/{id}")
    public ModelAndView recalculateAppGroup(
            @PathVariable("id") long appGroupId,
            Principal principal) {

        Map<String, Object> model = new HashMap<>();
        model.put("appGroupId", String.valueOf(appGroupId));

        try {
            int entityCount = reportGridFilterViewService.recalculateAppGroupFromNoteText(appGroupId, principal.getName());
            model.put("success", true);
            model.put("entityCount", entityCount);
        } catch (IllegalArgumentException e) {
            model.put("success", false);
            model.put("errorMessage", e.getMessage());
        }
        return new ModelAndView("recalculate-app-group-page", model);
    }
}
