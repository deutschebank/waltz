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
import org.finos.waltz.model.IdSelectionOptions;
import org.finos.waltz.model.changelog.ChangeLog;
import org.finos.waltz.model.tally.DateTally;
import org.finos.waltz.service.changelog.ChangeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.finos.waltz.common.Checks.checkNotNull;


@RestController
@RequestMapping("/api/change-log")
public class ChangeLogController {

    private final ChangeLogService service;


    @Autowired
    public ChangeLogController(ChangeLogService changeLogService) {
        checkNotNull(changeLogService, "changeLogService must not be null");
        this.service = changeLogService;
    }


    @GetMapping("/user/{userId}")
    public List<ChangeLog> findByUser(@PathVariable("userId") String userId,
                                      @RequestParam(value = "limit", required = false) Integer limit) {
        return service.findByUser(
                userId,
                Optional.ofNullable(limit));
    }


    @PostMapping("/summaries/{kind}")
    public List<DateTally> findCountByDateForParentKindBySelector(
            @PathVariable("kind") String kind,
            @RequestBody IdSelectionOptions options,
            @RequestParam(value = "limit", required = false) Integer limit) {
        return service.findCountByDateForParentKindBySelector(
                EntityKind.valueOf(kind),
                options,
                Optional.ofNullable(limit));
    }


    @GetMapping("/{kind}/{id}")
    public Collection<ChangeLog> findByEntityReference(
            @PathVariable("kind") String kind,
            @PathVariable("id") long id,
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
            @RequestParam(value = "limit", required = false) Integer limit) {

        EntityReference ref = EntityReference.mkRef(EntityKind.valueOf(kind), id);
        Optional<Date> dateParam = Optional.ofNullable(date);
        Optional<Integer> limitParam = Optional.ofNullable(limit);

        if (ref.kind() == EntityKind.PERSON) {
            return service.findByPersonReference(ref, dateParam, limitParam);
        } else {
            return service.findByParentReference(ref, dateParam, limitParam);
        }
    }


    @GetMapping("/{kind}/{id}/date-range")
    public Collection<ChangeLog> findByEntityReferenceForDateRange(
            @PathVariable("kind") String kind,
            @PathVariable("id") long id,
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam(value = "limit", required = false) Integer limit) {

        EntityReference ref = EntityReference.mkRef(EntityKind.valueOf(kind), id);
        Optional<Integer> limitParam = Optional.ofNullable(limit);

        java.sql.Date startSqlDate = new java.sql.Date(startDate.getTime());
        java.sql.Date endSqlDate = new java.sql.Date(endDate.getTime());

        if (ref.kind() == EntityKind.PERSON) {
            return service.findByPersonReferenceForDateRange(ref, startSqlDate, endSqlDate, limitParam);
        } else {
            return service.findByParentReferenceForDateRange(ref, startSqlDate, endSqlDate, limitParam);
        }
    }


    @GetMapping("/{kind}/{id}/unattested")
    public List<ChangeLog> findUnattestedChanges(
            @PathVariable("kind") String kind,
            @PathVariable("id") long id) {
        EntityReference ref = EntityReference.mkRef(EntityKind.valueOf(kind), id);
        return service.findUnattestedChanges(ref);
    }

}
