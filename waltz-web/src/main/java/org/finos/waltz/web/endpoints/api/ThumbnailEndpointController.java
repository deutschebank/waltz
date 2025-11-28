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
import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.thumbnail.Thumbnail;
import org.finos.waltz.model.thumbnail.ThumbnailSaveCommand;
import org.finos.waltz.service.thumbnail.ThumbnailService;
import org.finos.waltz.web.WebUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.finos.waltz.common.Checks.checkNotNull;

@RestController
@RequestMapping("/api/thumbnail")
public class ThumbnailEndpointController {

    private final String BASE_URL = WebUtilities.mkPath("api", "thumbnail");
    private final ThumbnailService thumbnailService;

    @Autowired
    public ThumbnailEndpointController(ThumbnailService thumbnailService) {
        checkNotNull(thumbnailService, "thumbnailService cannot be null");
        this.thumbnailService = thumbnailService;
    }

    @GetMapping("/{kind}/{id}")
    public Thumbnail find(@PathVariable EntityKind kind, @PathVariable Long id) {
        return thumbnailService.getByReference(EntityReference.mkRef(kind, id));

    }

    @DeleteMapping("/{kind}/{id}")
    public Boolean delete(HttpServletRequest request, @PathVariable EntityKind kind, @PathVariable Long id) {
        return thumbnailService.deleteByReference(EntityReference.mkRef(kind, id), WebUtilities.getUsernameForSB(request));
    }

    @PostMapping("/save")
    public void save(HttpServletRequest request, @RequestBody ThumbnailSaveCommand body) {
        thumbnailService.save(body, WebUtilities.getUsernameForSB(request));
    }

}
