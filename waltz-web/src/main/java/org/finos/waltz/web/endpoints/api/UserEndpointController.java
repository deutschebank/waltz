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
import org.finos.waltz.model.bulk_upload.BulkUploadMode;
import org.finos.waltz.model.user.*;
import org.finos.waltz.service.user.UserRoleService;
import org.finos.waltz.service.user.UserService;
import org.finos.waltz.web.WebUtilities;
import org.finos.waltz.web.endpoints.auth.AuthenticationUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static org.finos.waltz.common.ListUtilities.asList;
import static org.finos.waltz.web.WebUtilities.requireAnyRoleForSB;

@RestController
@RequestMapping("/api/user")
public class UserEndpointController {
    private final UserService userService;
    private final UserRoleService userRoleService;


    @Autowired
    public UserEndpointController(UserService userService, UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @PostMapping("/new-user")
    public Boolean findNewUser(HttpServletRequest request, @RequestBody UserRegistrationRequest requestBody) {
        ensureUserHasAdminRights(request);
        return userService.registerNewUser(requestBody) == 1;
    }

    @PostMapping("/{userName}/roles")
    public Integer updateUserRoles(HttpServletRequest request, @PathVariable("userName") String targetUserName, @RequestBody UpdateRolesCommand cmd) {
        ensureUserHasAdminRights(request);
        String userName = WebUtilities.getUsernameForSB(request);
        return userRoleService.updateRoles(userName, targetUserName, cmd);
    }

    @PostMapping("/reset-password")
    public Boolean updateUserPassword(HttpServletRequest request, @RequestBody PasswordResetRequest requestBody) {
        boolean validate = !userRoleService.hasAnyRole(WebUtilities.getUsernameForSB(request), SystemRole.USER_ADMIN, SystemRole.ADMIN);
        return userService.resetPassword(requestBody, validate);

    }

    @DeleteMapping("/{userName}")
    public Object deleteUser(HttpServletRequest request, @PathVariable("userName") String userName) {
        ensureUserHasAdminRights(request);
        return userService.deleteUser(userName);
    }

    @GetMapping("/whoami")
    public User whoAmI(HttpServletRequest request) {
        if (AuthenticationUtilities.isAnonymousForSB(request)) {
            return UserUtilities.ANONYMOUS_USER;
        }

        String username = WebUtilities.getUsernameForSB(request);

        userService.ensureExists(username);

        return ImmutableUser.builder()
                .userName(username)
                .roles(userRoleService.getUserRoles(username))
                .build();
    }

    @GetMapping()
    public Set<User> findAll() {
        return userRoleService.findAllUsers();
    }

    @GetMapping("/user-id/{userId}")
    public User findByUserId(@PathVariable("userId") String userId) {
        return userRoleService.getByUserId(userId);
    }

    @PostMapping("/bulk/{mode}/preview")
    public List<BulkUserOperationRowPreview> bulkUploadPreview(HttpServletRequest request, @PathVariable("mode") String mode, @RequestBody String body) {
        ensureUserHasAdminRights(request);

        String username = WebUtilities.getUsernameForSB(request);
        String[] lines = body.split("\\R");
        return userRoleService.bulkUploadPreview(
                BulkUploadMode.valueOf(mode),
                asList(lines),
                username);
    }


    @PostMapping("/bulk/{mode}/upload")
    public Integer bulkUpload(HttpServletRequest request, @PathVariable("mode") String mode, @RequestBody String body) {
        ensureUserHasAdminRights(request);

        String username = WebUtilities.getUsernameForSB(request);
        String[] lines = body.split("\\R");
        return userRoleService.bulkUpload(
                BulkUploadMode.valueOf(mode),
                asList(lines),
                username);
    }


    private void ensureUserHasAdminRights(HttpServletRequest request) {
        requireAnyRoleForSB(
                userRoleService,
                request,
                SystemRole.USER_ADMIN,
                SystemRole.ADMIN);
    }
}
