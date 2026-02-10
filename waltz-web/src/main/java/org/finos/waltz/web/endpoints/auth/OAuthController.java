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
package org.finos.waltz.web.endpoints.auth;

import org.finos.waltz.service.settings.SettingsService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class OAuthController {
    private final static String OAUTH_PROVIDER_DETAILS = "oauth.provider.details";
    private final SettingsService settingsService;
    public OAuthController(SettingsService settingsService){
        this.settingsService = settingsService;
    }

    @GetMapping("/oauthdetails")
    ResponseEntity<String> getDetails(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/javascript"));
        Optional<String> OauthProviderDetails = Optional.of(settingsService
                .getValue(OAUTH_PROVIDER_DETAILS)
                .orElse("{name : null}"));
        return new ResponseEntity<>("const oauthdetails = " + OauthProviderDetails.get() + ";", headers, HttpStatus.OK);
    }
}
