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
import org.finos.waltz.model.IdSelectionOptions;
import org.finos.waltz.model.tally.ChangeLogTally;
import org.finos.waltz.service.changelog.ChangeLogSummariesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.common.DateTimeUtilities.toSqlDate;
import static org.finos.waltz.web.WebUtilities.mkPath;


@RestController
@RequestMapping("/api/change-log-summaries/")
public class ChangeLogSummariesController {

    private static final String BASE_URL = mkPath("api", "change-log-summaries");

    private final ChangeLogSummariesService changeLogSummariesService;

    @Autowired
    public ChangeLogSummariesController(ChangeLogSummariesService changeLogSummariesService) {
        checkNotNull(changeLogSummariesService, "changeLogSummariesService must not be null");
        this.changeLogSummariesService = changeLogSummariesService;
    }

    @PostMapping("kind/{kind}/selector")
    public Collection<ChangeLogTally> findSummariesForDateRange(
            @PathVariable("kind") EntityKind kind,
            @RequestBody IdSelectionOptions idSelectionOptions,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "limit", required = false) Integer limit) {

        Optional<Integer> optionalNumber = Optional.of(limit);
        return changeLogSummariesService.findCountByParentAndChildKindForDateRangeBySelector(
                kind,
                idSelectionOptions,
                toSqlDate(startDate),
                toSqlDate(endDate),
                optionalNumber);
    }

    @GetMapping("year-on-year")
    public Map<Integer, Long> findYearOnYearChanges(@RequestParam("parentKind") EntityKind parentKind,
                                                    @RequestParam("childKind") EntityKind childKind) {
        return changeLogSummariesService.findYearOnYearChanges(parentKind, childKind);
    }

    @GetMapping("get-years")
    public Collection<Integer> findChangeLogYears() {
        return changeLogSummariesService.findChangeLogYears();
    }

    @GetMapping("month-on-month")
    public Map<Integer, Long> findMonthOnMonthChanges(@RequestParam("parentKind") EntityKind parentKind,
                                                      @RequestParam("childKind") EntityKind childKind,
                                                      @RequestParam("year") int year) {
        return changeLogSummariesService.findMonthOnMonthChanges(parentKind, childKind, year);
    }
}
