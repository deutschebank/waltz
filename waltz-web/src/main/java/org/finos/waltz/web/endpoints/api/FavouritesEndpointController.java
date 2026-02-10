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
import org.finos.waltz.common.exception.InsufficientPrivelegeException;
import org.finos.waltz.model.app_group.AppGroup;
import org.finos.waltz.model.app_group.AppGroupEntry;
import org.finos.waltz.service.app_group.FavouritesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.finos.waltz.web.WebUtilities.getUsernameForSB;
import static org.finos.waltz.web.WebUtilities.mkPath;

@RestController
@RequestMapping("/api/favourites")
public class FavouritesEndpointController {


    private final FavouritesService favouritesService;


    @Autowired
    public FavouritesEndpointController(FavouritesService service) {
        this.favouritesService = service;
    }

    @GetMapping("/group")
    AppGroup getFavouriteGroup(HttpServletRequest request) {
        return favouritesService.getFavouritesGroup(getUsernameForSB(request));
    }

    @GetMapping("/entries")
    Collection<AppGroupEntry> getFavouriteGroupEntries(HttpServletRequest request) {
        return favouritesService.findFavouriteGroupEntries(getUsernameForSB(request));
    }

    @PostMapping("/application/{id}")
    Collection<AppGroupEntry> getFavouriteGroupEntries(HttpServletRequest request,
                                                       @PathVariable Long id) throws InsufficientPrivelegeException {
        return favouritesService.addApplication(getUsernameForSB(request), id);
    }

    @DeleteMapping("/application/{id}")
    Collection<AppGroupEntry> removeApplication(HttpServletRequest request,
                                                @PathVariable Long id) throws InsufficientPrivelegeException {
        return favouritesService.removeApplication(getUsernameForSB(request), id);
    }

}
