/*
 * Waltz - Enterprise Architecture
 * Copyright (C) 2016, 2017, 2018, 2019, 2024 Waltz open source project
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
import org.finos.waltz.common.exception.InsufficientPrivelegeException;
import org.finos.waltz.model.app_group.AppGroup;
import org.finos.waltz.model.app_group.AppGroupEntry;
import org.finos.waltz.service.app_group.FavouritesService;
import org.finos.waltz.web.WebUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

import static org.finos.waltz.web.WebUtilities.getUsernameForSB;


@RestController
@RequestMapping("/api/favourites/")
public class FavouritesController {

    private final FavouritesService favouritesService;


    @Autowired
    public FavouritesController(FavouritesService service) {
        this.favouritesService = service;
    }


    @GetMapping("group")
    public AppGroup getFavouriteGroup(Principal principal) {
        return favouritesService.getFavouritesGroup(principal.getName());
    }


    @GetMapping("entries")
    public Collection<AppGroupEntry> findFavouriteGroupEntries(Principal principal) {
        return favouritesService.findFavouriteGroupEntries(principal.getName());
    }


    @PostMapping("application/{id}")
    public Collection<AppGroupEntry> addApplication(HttpServletRequest principal,
                                              @PathVariable("id") long applicationId) throws InsufficientPrivelegeException {
        String username = getUsernameForSB(principal);
        return favouritesService.addApplication(
                username,
                applicationId);
    }


    @DeleteMapping("application/{id}")
    public Collection<AppGroupEntry> removeApplication(HttpServletRequest principal,
                                                 @PathVariable("id") long applicationId) throws InsufficientPrivelegeException {
        String username = getUsernameForSB(principal);
        return favouritesService.removeApplication(
                username,
                applicationId);
    }

}
