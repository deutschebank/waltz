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

import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.ImmutableEntityReference;
import org.finos.waltz.model.Operation;
import org.finos.waltz.model.Severity;
import org.finos.waltz.model.application.ApplicationsView;
import org.finos.waltz.service.application.ApplicationService;
import org.finos.waltz.service.changelog.ChangeLogService;
import org.finos.waltz.web.action.AppChangeAction;
import org.finos.waltz.web.endpoints.Endpoint;
import org.finos.waltz.common.ListUtilities;
import org.finos.waltz.model.IdSelectionOptions;
import org.finos.waltz.model.application.AppRegistrationRequest;
import org.finos.waltz.model.application.AppRegistrationResponse;
import org.finos.waltz.model.application.Application;
import org.finos.waltz.model.application.AssetCodeRelationshipKind;
import org.finos.waltz.model.changelog.ImmutableChangeLog;
import org.finos.waltz.model.external_identifier.ExternalIdValue;
import org.finos.waltz.model.tally.Tally;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.web.WebUtilities.mkPath;


@RestController
@RequestMapping("/api/app/")
public class ApplicationEndpointController {


    private static final Logger LOG = LoggerFactory.getLogger(ApplicationEndpointController.class);

    private final ApplicationService appService;
    private final org.finos.waltz.service.application.ApplicationViewService appViewService;
    private final ChangeLogService changeLogService;


    @Autowired
    public ApplicationEndpointController(ApplicationService appService,
                               ChangeLogService changeLogService,
                               org.finos.waltz.service.application.ApplicationViewService appViewService) {
        this.appViewService = appViewService;
        checkNotNull(appService, "appService must not be null");
        checkNotNull(changeLogService, "changeLogService must not be null");

        this.appService = appService;
        this.changeLogService = changeLogService;
    }


    @GetMapping("search/{query}")
    public List<Application> search(@PathVariable("query") String query) {
        return appService.search(query);
    }

    @GetMapping("count-by/org-unit")
    public List<Tally<Long>> countByOrganisationalUnit() {
        return appService.countByOrganisationalUnit();
    }

    @GetMapping("id/{id}/related")
    public Map<AssetCodeRelationshipKind, List<Application>> findRelated(@PathVariable("id") long id) {
        return appService.findRelated(id);
    }

    @PostMapping("by-ids")
    public List<Application> findByIds(@RequestBody List<Long> ids) {
        if (ListUtilities.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return appService.findByIds(ids);
    }

    @GetMapping("all")
    public List<Application> findAll() {
        return appService.findAll();
    }

    @GetMapping("id/{id}")
    public Application getById(@PathVariable("id") long id) {
        return appService.getById(id);
    }

    @PostMapping("selector")
    public List<Application> findByAppIdSelector(@RequestBody IdSelectionOptions options) {
        return appService.findByAppIdSelector(options);
    }

    @GetMapping({"/asset-code/{assetCode}", "/external-id/{assetCode}"})
    public List<Application> findByAssetCode(@PathVariable("assetCode") String assetCode) {
        return appService.findByAssetCode(ExternalIdValue.of(assetCode));
    }

    @PostMapping("view/selector")
    public ApplicationsView getViewBySelector(@RequestBody IdSelectionOptions options) {
        return appViewService.getViewBySelector(options);
    }

    @PostMapping
    public AppRegistrationResponse register(@RequestBody AppRegistrationRequest registrationRequest, Principal principal) {
        LOG.info("Registering new application:" + registrationRequest);
        String username = principal.getName();

        AppRegistrationResponse registrationResponse = appService
                .registerApp(registrationRequest, username);

        if (registrationResponse.registered()) {
            ImmutableChangeLog changeLogEntry = ImmutableChangeLog.builder()
                    .message("Registered new application: " + registrationRequest.name())
                    .severity(Severity.INFORMATION)
                    .userId(username)
                    .parentReference(ImmutableEntityReference.builder()
                            .kind(EntityKind.APPLICATION)
                            .id(registrationResponse.id().get())
                            .build())
                    .operation(Operation.ADD)
                    .build();
            changeLogService.write(changeLogEntry);
        }

        return registrationResponse;
    }

    @PostMapping("{id}")
    public boolean update(@PathVariable("id") long appId,
                          @RequestBody AppChangeAction appChange,
                          Principal principal) {
        LOG.info("Updating application: " + appChange);
        String username = principal.getName();

        EntityReference ref = ImmutableEntityReference.builder()
                .kind(EntityKind.APPLICATION)
                .id(appId)
                .build();

        appChange.changes()
                .forEach(c -> {
                    changeLogService.write(
                            ImmutableChangeLog.builder()
                                    .message(c.toDescription())
                                    .severity(Severity.INFORMATION)
                                    .userId(username)
                                    .parentReference(ref)
                                    .operation(Operation.UPDATE)
                                    .build());
                });

        appService.update(appChange.app());
        return true;
    }
}
