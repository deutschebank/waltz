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

import org.finos.waltz.service.access_log.AccessLogService;
import org.finos.waltz.model.WaltzVersionInfo;
import org.finos.waltz.model.accesslog.AccessLog;
import org.finos.waltz.model.accesslog.AccessLogSummary;
import org.finos.waltz.model.accesslog.AccessTime;
import org.finos.waltz.model.accesslog.ImmutableAccessLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.Duration;
import java.util.List;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.web.WebUtilities.mkPath;


@RestController
@RequestMapping("/api/access-log/")
public class AccessLogEndpointController {

    private final AccessLogService accessLogService;
    private final WaltzVersionInfo waltzVersionInfo;

    @Autowired
    public AccessLogEndpointController(AccessLogService accessLogService,
                             WaltzVersionInfo waltzVersionInfo) {
        checkNotNull(accessLogService, "accessLogService cannot be null");
        checkNotNull(waltzVersionInfo, "waltzVersionInfo cannot be null");

        this.accessLogService = accessLogService;
        this.waltzVersionInfo = waltzVersionInfo;
    }

    @GetMapping("user/{userId}")
    public List<AccessLog> findForUser(@PathVariable String userId,
                                       @RequestParam(value = "limit", required = false) Integer limit) {
        return accessLogService.findForUserId(userId, limit.describeConstable());
    }

    @GetMapping("active/{minutes}")
    public List<AccessTime> findActiveUsers(@PathVariable int minutes) {
        Duration duration = Duration.ofMinutes(minutes);
        return accessLogService.findActiveUsersSince(duration);
    }

    @GetMapping("counts_by_state/{days}")
    public List<AccessLogSummary> findAccessLogCountsByStateSince(@PathVariable int days) {
        Duration duration = Duration.ofDays(days);
        return accessLogService.findAccessLogCountsByStateSince(duration);
    }

    @GetMapping("counts_since/{days}")
    public List<AccessLogSummary> findWeeklyAccessLogSummary(@PathVariable int days) {
        Duration duration = Duration.ofDays(days);
        return accessLogService.findWeeklyAccessLogSummary(duration);
    }

    @GetMapping("users_since/{days}")
    public List<AccessLogSummary> findDailyActiveUserCountsSince(@PathVariable int days) {
        Duration duration = Duration.ofDays(days);
        return accessLogService.findDailyUniqueUsersSince(duration);
    }

    @GetMapping("summary/year_on_year/{mode}")
    public List<AccessLogSummary> findYearOnYearUsers(@PathVariable String mode) {
        return accessLogService.findYearOnYearAccessLogSummary(mode);
    }

    @GetMapping("get_years")
    public List<Integer> findAccessLogYears() {
        return accessLogService.findAccessLogYears();
    }

    @GetMapping("summary/month_on_month/{mode}/{year}")
    public List<AccessLogSummary> findMonthOnMonthUsers(@PathVariable String mode,
                                                        @PathVariable int year) {
        return accessLogService.findMonthOnMonthAccessLogSummary(mode, year);
    }

    @PostMapping("{state}")
    public WaltzVersionInfo write(@PathVariable String state,
                                  @RequestBody(required = false) String params,
                                  Principal principal) {
        AccessLog accessLog = ImmutableAccessLog.builder()
                .userId(principal.getName())
                .state(state)
                .params(params)
                .build();

        accessLogService.write(accessLog);

        return waltzVersionInfo;
    }

}
