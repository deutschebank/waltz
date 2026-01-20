/*
 * Waltz - Enterprise Architecture
 * Copyright (C) 2016, 2017, 2018, 2019, 2020 Waltz open source project
 * See README.md for more information
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.∫
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
import org.finos.waltz.service.changelog.ChangeLogService;
import org.finos.waltz.service.entity_alias.EntityAliasService;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.Operation;
import org.finos.waltz.model.Severity;
import org.finos.waltz.model.changelog.ImmutableChangeLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;

import static org.finos.waltz.model.EntityReference.mkRef;
import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.web.WebUtilities.*;

@RestController
@RequestMapping("/api/entity/alias/")
public class EntityAliasEndpointController {

    private static final Logger LOG = LoggerFactory.getLogger(EntityAliasEndpointController.class);
    public static final String BASE_URL = mkPath("api", "entity", "alias");

    private final EntityAliasService entityAliasService;
    private final ChangeLogService changeLogService;

    @Autowired
    public EntityAliasEndpointController(EntityAliasService entityAliasService, ChangeLogService changeLogService) {
        checkNotNull(entityAliasService, "entityAliasService cannot be null");
        checkNotNull(changeLogService, "changeLogService cannot be null");

        this.entityAliasService = entityAliasService;
        this.changeLogService = changeLogService;
    }
    
    @RequestMapping(value = "{kind}/{id}", method = RequestMethod.GET)
    public List<String> findAliasesForEntityReference(@PathVariable("kind") String kind, @PathVariable("id") long id) {
        EntityReference ref = mkRef(EntityKind.valueOf(kind), id);
        return entityAliasService.findAliasesForEntityReference(ref);
    }
    
    @RequestMapping(value = "{kind}/{id}", method = RequestMethod.POST)
    public List<String> updateAliases(@PathVariable("kind") String kind,
                                      @PathVariable("id") long id,
                                      @RequestBody List<String> aliases,
                                      HttpServletRequest request) throws IOException {
        String user = getUsernameForSB(request);
        EntityReference ref = mkRef(EntityKind.valueOf(kind), id);
        String auditMessage = String.format("Aliases have been updated to: %s", aliases);
        entityAliasService.updateAliases(ref, aliases);
        LOG.info(auditMessage);
        changeLogService.write(ImmutableChangeLog.builder()
                .parentReference(ref)
                .userId(user)
                .message(auditMessage)
                .severity(Severity.INFORMATION)
                .operation(Operation.UPDATE)
                .build());
        return aliases;
    }
}
