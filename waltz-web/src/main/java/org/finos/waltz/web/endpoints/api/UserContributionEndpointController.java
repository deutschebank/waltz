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

import org.finos.waltz.model.tally.OrderedTally;
import org.finos.waltz.model.tally.Tally;
import org.finos.waltz.service.user_contribution.UserContributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.common.StringUtilities.parseInteger;

@RestController
@RequestMapping("/api/user-contribution")
public class UserContributionEndpointController {


    private static final int DEFAULT_LIMIT = 10;

    private final UserContributionService userContributionService;


    @Autowired
    public UserContributionEndpointController(UserContributionService userContributionService) {
        checkNotNull(userContributionService, "userContributionService cannot be null");

        this.userContributionService = userContributionService;
    }

    @GetMapping("/leader-board")
    public List<OrderedTally<String>> getLeaderBoard(@RequestParam String limitStr) {
        int limit = parseInteger(limitStr, DEFAULT_LIMIT);

        return userContributionService.getLeaderBoard(limit);
    }

    @GetMapping("/user/{userId}/score")
    public Double getScoreForUser(@PathVariable("userId") String userId) {
        return userContributionService.getScoreForUser(userId);
    }

    @GetMapping("/user/{userId}/directs/score")
    public List<Tally<String>> findScoresForDirectReports(@PathVariable("userId") String userId) {
        return userContributionService.findScoresForDirectReports(userId);
    }

    @GetMapping("/monthly-leader-board")
    public List<OrderedTally<String>> getLeaderBoardLastMonth(@RequestParam String limitStr) {
        int limit = parseInteger(limitStr, DEFAULT_LIMIT);
        return userContributionService.getLeaderBoardLastMonth(limit);
    }

    @GetMapping("/user/{userId}/ordered-leader-board")
    public List<OrderedTally<String>> getRankedLeaderBoard(@PathVariable("userId") String userId) {
        return userContributionService.getRankedLeaderBoard(userId);
    }

}
