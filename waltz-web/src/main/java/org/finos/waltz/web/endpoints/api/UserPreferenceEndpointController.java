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
import org.finos.waltz.model.user.UserPreference;
import org.finos.waltz.service.user.UserPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.web.endpoints.auth.AuthenticationUtilities.getUsernameForSB;


@RestController
@RequestMapping("/api/user-preference")
public class UserPreferenceEndpointController {
    private final UserPreferenceService userPreferenceService;

    @Autowired
    public UserPreferenceEndpointController(UserPreferenceService userPreferenceService) {
        checkNotNull(userPreferenceService, "userPreferenceService cannot be null");
        this.userPreferenceService = userPreferenceService;
    }


    @GetMapping()
    public Object findAllForUser(HttpServletRequest request) {
        String userName = getUsernameForSB(request);
        return userPreferenceService.getPreferences(userName);
    }

    @PostMapping("/save-all")
    public Object saveAllForUser(HttpServletRequest request, @RequestBody List<UserPreference> preferences) {
        String userName = getUsernameForSB(request);
        return userPreferenceService.savePreferences(userName, preferences);
    }

    @PostMapping("/save")
    public Object saveForUser(HttpServletRequest request, @RequestBody UserPreference preferences) {
        String userName = getUsernameForSB(request);
        return userPreferenceService.savePreference(userName, preferences);
    }

    @DeleteMapping("/clear")
    public Object saveForUser(HttpServletRequest request) {
        String userName = getUsernameForSB(request);
        return userPreferenceService.clearPreferences(userName);
    }

}
