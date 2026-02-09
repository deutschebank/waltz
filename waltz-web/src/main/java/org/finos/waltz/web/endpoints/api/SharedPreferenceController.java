/*
 * Waltz - Enterprise Architecture
 * Copyright (C) 2016, 2017, 2018, 2019, 2024 Waltz open source project
 * See README.md for more information
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may obtain a copy of the License at
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

import org.finos.waltz.service.shared_preference.SharedPreferenceService;
import org.finos.waltz.model.shared_preference.SharedPreference;
import org.finos.waltz.model.shared_preference.SharedPreferenceSaveCommand;
import org.finos.waltz.web.json.SharedPreferenceKeyAndCategory;
import org.finos.waltz.web.WebUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.common.DigestUtilities.digest;

@RestController
@RequestMapping("/api/shared-preference/")
public class SharedPreferenceController {

    private final SharedPreferenceService sharedPreferenceService;

    @Autowired
    public SharedPreferenceController(SharedPreferenceService sharedPreferenceService) {
        checkNotNull(sharedPreferenceService, "sharedPreferenceService cannot be null");
        this.sharedPreferenceService = sharedPreferenceService;
    }
    
    @RequestMapping(value = "/key-category", method = RequestMethod.POST)
    public SharedPreference getByKeyAndCategoryRoute(@RequestBody SharedPreferenceKeyAndCategory keyCat) {
        return sharedPreferenceService.getPreference(keyCat.key(), keyCat.category());
    }

    @RequestMapping(value = "/category/{category}", method = RequestMethod.GET)
    public List<SharedPreference> findByCategoryRoute(@PathVariable("category") String category) {
        return sharedPreferenceService.findPreferencesByCategory(category);
    }

    @RequestMapping(value = "/generate-key", method = RequestMethod.POST)
    public String generateKeyRoute(@RequestBody String body) throws NoSuchAlgorithmException {
        return digest(body.getBytes());
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public boolean saveRoute(Principal principal, @RequestBody SharedPreferenceSaveCommand sp) {
        return sharedPreferenceService.savePreference(principal.getName(), sp);
    }

}
