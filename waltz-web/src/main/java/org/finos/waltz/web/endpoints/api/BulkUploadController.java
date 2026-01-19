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
import org.finos.waltz.model.Operation;
import org.finos.waltz.model.bulk_upload.BulkUploadCommand;
import org.finos.waltz.model.bulk_upload.ResolveBulkUploadRequestParameters;
import org.finos.waltz.model.bulk_upload.ResolveRowResponse;
import org.finos.waltz.service.bulk_upload.BulkUploadService;
import org.finos.waltz.service.user.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.web.WebUtilities.*;

@RestController
@RequestMapping("/api/bulk-upload/")
public class BulkUploadController {

    private static final Logger LOG = LoggerFactory.getLogger(BulkUploadController.class);

    private final BulkUploadService service;
    private final UserRoleService userRoleService;


    @Autowired
    public BulkUploadController(BulkUploadService service,
                                UserRoleService userRoleService) {
        checkNotNull(service, "service must not be null");
        checkNotNull(userRoleService, "userRoleService cannot be null");

        this.service = service;
        this.userRoleService = userRoleService;
    }


    @PostMapping("resolve")
    public List<ResolveRowResponse> resolveRoute(@RequestBody ResolveBulkUploadRequestParameters resolveParams,
                                                 Principal principal) {
        String username = principal.getName();
        ensureUserHasAdminRights(username, resolveParams.rowSubjectKind(), resolveParams.targetDomain().kind());
        LOG.info("User: {} resolving bulk upload: {}", username, resolveParams);

        return service.resolve(resolveParams);
    }


    @PostMapping()
    public Integer uploadRoute(@RequestBody BulkUploadCommand uploadCommand,
                               HttpServletRequest httpServletRequest) {
        ensureUserHasAdminRights(httpServletRequest, uploadCommand.rowSubjectKind(), uploadCommand.targetDomain().kind());
        String username = getUsernameForSB(httpServletRequest);
        LOG.info("User: {} requesting bulk upload: {}", username, uploadCommand);
        return service.upload(uploadCommand, username);
    }


    private void ensureUserHasAdminRights(HttpServletRequest httpServletRequest,
                                          EntityKind subjectKind,
                                          EntityKind targetKind) {
        requireEditRoleForEntityForSB(
                userRoleService,
                httpServletRequest,
                targetKind,
                Operation.UPDATE,
                subjectKind);
    }

}