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
import org.finos.waltz.model.IdSelectionOptions;
import org.finos.waltz.model.changelog.ChangeLog;
import org.finos.waltz.model.tally.DateTally;
import org.finos.waltz.service.changelog.ChangeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spark.Request;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.common.DateTimeUtilities.toSqlDate;

@RestController
@RequestMapping("/api/change-log")
public class ChangeLogEndpointController {

    private final ChangeLogService service;


    @Autowired
    public ChangeLogEndpointController(ChangeLogService changeLogService) {
        checkNotNull(changeLogService, "changeLogService must not be null");
        this.service = changeLogService;
    }

    @GetMapping("/user/{userId}")
    List<ChangeLog> findByUserId(@PathVariable String userId,
                                 @RequestParam Integer limit) {
        return service.findByUser(userId, Optional.ofNullable(limit));
    }

    @PostMapping("/summaries/{kind}")
    List<DateTally> findByUserId(@PathVariable EntityKind kind,
                                 @RequestParam Integer limit,
                                 @RequestBody IdSelectionOptions body) {
        return service.findCountByDateForParentKindBySelector(kind, body, Optional.ofNullable(limit));
    }

    @GetMapping("/{kind}/{id}")
    List<ChangeLog> findByUserId(@PathVariable EntityKind kind,
                                 @PathVariable Long id,
                                 @RequestParam Integer limit,
                                 @RequestParam Date date) {
        EntityReference ref = EntityReference.mkRef(kind, id);

        if (ref.kind() == EntityKind.PERSON) {
            return service.findByPersonReference(ref, Optional.ofNullable(date), Optional.ofNullable(limit));
        } else {
            return service.findByParentReference(ref, Optional.ofNullable(date), Optional.ofNullable(limit));
        }
    }

    @GetMapping("/{kind}/{id}/date-range")
    List<ChangeLog> getByDateRange(@PathVariable EntityKind kind,
                                   @PathVariable Long id,
                                   @RequestParam Integer limit,
                                   @RequestParam java.sql.Date startDate,
                                   @RequestParam java.sql.Date endDate) {
        EntityReference ref = EntityReference.mkRef(kind, id);


        if (ref.kind() == EntityKind.PERSON) {
            return service.findByPersonReferenceForDateRange(ref, startDate, endDate, Optional.ofNullable(limit));
        } else {
            return service.findByParentReferenceForDateRange(ref, startDate, endDate, Optional.ofNullable(limit));
        }
    }

    @GetMapping("/{kind}/{id}/unattested")
    List<ChangeLog> getByDateRange(@PathVariable EntityKind kind,
                                   @PathVariable Long id) {
        EntityReference ref = EntityReference.mkRef(kind, id);

        return service.findUnattestedChanges(ref);
    }


    private java.sql.Date getStartDate(Request request) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return toSqlDate(formatter.parse(request.queryParams("startDate")));
    }


    private java.sql.Date getEndDate(Request request) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return toSqlDate(formatter.parse(request.queryParams("endDate")));
    }
}
