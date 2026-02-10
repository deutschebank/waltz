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

import jakarta.servlet.http.HttpServletRequest;
import org.finos.waltz.common.DateTimeUtilities;
import org.finos.waltz.model.user_agent_info.ImmutableUserAgentInfo;
import org.finos.waltz.model.user_agent_info.UserAgentInfo;
import org.finos.waltz.service.user_agent_info.UserAgentInfoService;
import org.finos.waltz.web.WebUtilities;
import org.finos.waltz.web.json.BrowserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.finos.waltz.common.Checks.checkNotNull;
@RestController
@RequestMapping("/api/user-agent-info")
public class UserAgentInfoEndpointController {

    private static final int DEFAULT_LIMIT = 10;

    private final UserAgentInfoService userAgentInfoService;


    @Autowired
    public UserAgentInfoEndpointController(UserAgentInfoService userAgentInfoService) {
        checkNotNull(userAgentInfoService, "userAgentInfoService cannot be null");
        this.userAgentInfoService = userAgentInfoService;
    }

    @GetMapping("/user/{userName}")
    public List<UserAgentInfo> findForUser(@PathVariable String userName, @RequestParam String limitStr){

        int limit = limitStr == null || limitStr.equals("")
                ? DEFAULT_LIMIT
                : Integer.parseInt(limitStr);

        return userAgentInfoService.findLoginsForUser(userName, limit);
    }

    @PostMapping()
    public Integer recordLogin(HttpServletRequest request, @RequestBody BrowserInfo browserInfo){

        String userAgent = request.getHeader("User-Agent");
        UserAgentInfo userAgentInfo = ImmutableUserAgentInfo.builder()
                .userName(WebUtilities.getUsernameForSB(request))
                .userAgent(userAgent)
                .operatingSystem(browserInfo.operatingSystem())
                .resolution(browserInfo.resolution())
                .loginTimestamp(DateTimeUtilities.nowUtc())
                .ipAddress(request.getRemoteAddr())
                .build();

        return userAgentInfoService.save(userAgentInfo);
    }
}
