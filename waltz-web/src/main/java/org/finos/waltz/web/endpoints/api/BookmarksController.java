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
import org.finos.waltz.model.bookmark.Bookmark;
import org.finos.waltz.model.user.SystemRole;
import org.finos.waltz.service.bookmark.BookmarkService;
import org.finos.waltz.service.changelog.ChangeLogService;
import org.finos.waltz.service.user.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.web.WebUtilities.*;

@RestController
@RequestMapping("/api/bookmarks/")
public class BookmarksController {

    private static final Logger LOG = LoggerFactory.getLogger(BookmarksController.class);

    private final BookmarkService bookmarkService;
    private final ChangeLogService changeLogService;
    private final UserRoleService userRoleService;


    @Autowired
    public BookmarksController(BookmarkService service,
                               ChangeLogService changeLogService,
                               UserRoleService userRoleService) {
        checkNotNull(service, "bookmarkService must not be null");
        checkNotNull(changeLogService, "changeLogService must not be null");
        checkNotNull(userRoleService, "userRoleService cannot be null");

        this.bookmarkService = service;
        this.changeLogService = changeLogService;
        this.userRoleService = userRoleService;
    }


    @GetMapping("/{kind}/{id}")
    public List<Bookmark> findByReference(@PathVariable("kind") EntityKind kind, @PathVariable("id") long id) {
        return bookmarkService.findByReference(EntityReference.mkRef(kind, id));
    }


    @PostMapping()
    public Bookmark save(@RequestBody Bookmark bookmark, HttpServletRequest httpServletRequest) {
        requireRoleForSB(userRoleService, httpServletRequest, SystemRole.BOOKMARK_EDITOR);
        String username = getUsernameForSB(httpServletRequest);

        LOG.info("Saving bookmark: " + bookmark);
        boolean isUpdate = bookmark.id().isPresent();

        return isUpdate
                ? bookmarkService.update(bookmark, username)
                : bookmarkService.create(bookmark, username);
    }


    @DeleteMapping("/{id}")
    public boolean remove(@PathVariable("id") long bookmarkId, HttpServletRequest httpServletRequest) {
        requireRoleForSB(userRoleService, httpServletRequest, SystemRole.BOOKMARK_EDITOR);
        String username = getUsernameForSB(httpServletRequest);
        Bookmark bookmark = bookmarkService.getById(bookmarkId);
        if (bookmark == null) {
            LOG.warn("Attempt made to delete non-existent bookmark: " + bookmarkId);
            return false;
        }

        LOG.info("Deleting bookmark: " + bookmark);
        return bookmarkService.deleteById(bookmark, username);
    }
}