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
import org.finos.waltz.service.app_group.AppGroupService;
import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.app_group.*;
import org.finos.waltz.model.entity_search.EntitySearchOptions;
import org.finos.waltz.service.app_group.AppGroupSubscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.finos.waltz.model.EntityReference.mkRef;
import static org.finos.waltz.model.entity_search.EntitySearchOptions.mkForEntity;
import static org.finos.waltz.web.WebUtilities.getUsernameForSB;

@RestController
@RequestMapping("/api/app-group/")
public class AppGroupEndpointController {

    private static final Logger LOG = LoggerFactory.getLogger(AppGroupEndpoint.class);

    private final AppGroupService appGroupService;


    @Autowired
    public AppGroupEndpointController(AppGroupService service) {
        this.appGroupService = service;
    }


    @GetMapping("id/{id}/detail")
    public AppGroupDetail getGroupDetailById(@PathVariable("id") long id) {
        return appGroupService.getGroupDetailById(id);
    }

    @PostMapping("id")
    public Collection<AppGroup> findByIds(HttpServletRequest request, @RequestBody List<Long> ids) {
        return appGroupService.findByIds(getUsernameForSB(request), ids);
    }

    @GetMapping("my-group-subscriptions")
    public Set<AppGroupSubscription> findGroupSubscriptionsForUser(HttpServletRequest request) {
        return appGroupService.findGroupSubscriptionsForUser(getUsernameForSB(request));
    }

    @GetMapping("public")
    public List<AppGroup> findPublicGroups() {
        return appGroupService.findPublicGroups();
    }

    @GetMapping("private")
    public List<AppGroup> findPrivateGroupsByOwner(HttpServletRequest request) {
        return appGroupService.findPrivateGroupsByOwner(getUsernameForSB(request));
    }

    @GetMapping("related/{kind}/{id}")
    public List<AppGroup> findRelatedByEntityReference(@PathVariable("kind") String kind,
                                                       @PathVariable("id") long id,
                                                       HttpServletRequest request) {
        EntityReference ref = mkRef(EntityKind.valueOf(kind), id);
        return appGroupService.findRelatedByEntityReference(ref, getUsernameForSB(request));
    }

    @PostMapping("id/{id}/subscribe")
    public Set<AppGroupSubscription> subscribe(@PathVariable("id") long groupId, HttpServletRequest request) {
        String userId = getUsernameForSB(request);
        LOG.info("Subscribing {} to group: {}", userId, groupId);
        appGroupService.subscribe(userId, groupId);
        return appGroupService.findGroupSubscriptionsForUser(userId);
    }

    @PostMapping("id/{id}/unsubscribe")
    public Set<AppGroupSubscription> unsubscribe(@PathVariable("id") long groupId, HttpServletRequest request) {
        String userId = getUsernameForSB(request);
        LOG.info("Unsubscribing {} from group: {}", userId, groupId);
        appGroupService.unsubscribe(userId, groupId);
        return appGroupService.findGroupSubscriptionsForUser(userId);
    }

    @PostMapping("id/{id}/members/owners")
    public Set<AppGroupMember> addOwner(@PathVariable("id") long groupId,
                                         @RequestBody String ownerId,
                                         HttpServletRequest request) throws InsufficientPrivelegeException {
        String userId = getUsernameForSB(request);
        LOG.info("User {} adding owner: {}, to group: {}", userId, ownerId, groupId);
        appGroupService.addOwner(userId, groupId, ownerId);
        return appGroupService.getMembers(groupId);
    }

    @DeleteMapping("id/{id}/members/owners/{ownerId}")
    public Set<AppGroupMember> removeOwner(@PathVariable("id") long groupId,
                                            @PathVariable("ownerId") String ownerToRemoveId,
                                            HttpServletRequest request) throws InsufficientPrivelegeException {
        String requestorName = getUsernameForSB(request);
        LOG.info("User {} removing owner: {} from app group: {}", requestorName, ownerToRemoveId, groupId);
        appGroupService.removeOwner(requestorName, groupId, ownerToRemoveId);
        return appGroupService.getMembers(groupId);
    }

    @DeleteMapping("id/{id}")
    public Set<AppGroupSubscription> deleteGroup(@PathVariable("id") long groupId, HttpServletRequest request) throws InsufficientPrivelegeException {
        String userId = getUsernameForSB(request);
        LOG.warn("User {} deleting group: {}", userId, groupId);
        appGroupService.deleteGroup(userId, groupId);
        return appGroupService.findGroupSubscriptionsForUser(userId);
    }

    @PostMapping("id/{id}/applications")
    public List<AppGroupEntry> addApplication(@PathVariable("id") long groupId,
                                              @RequestBody long applicationId,
                                              HttpServletRequest request) throws InsufficientPrivelegeException {
        LOG.info("Adding application: {}, to group: {} ", applicationId, groupId);
        return appGroupService.addApplication(getUsernameForSB(request), groupId, applicationId);
    }

    @PostMapping("id/{id}/applications/list")
    public List<AppGroupEntry> addApplicationList(@PathVariable("id") long groupId,
                                                  @RequestBody AppGroupBulkAddRequest bulkAddRequest,
                                                  HttpServletRequest request) throws InsufficientPrivelegeException {
        LOG.info("Adding applications: {}, to group: {} ", bulkAddRequest.applicationIds(), groupId);
        return appGroupService.addApplications(
                getUsernameForSB(request),
                groupId,
                bulkAddRequest.applicationIds(),
                bulkAddRequest.unknownIdentifiers());
    }

    @DeleteMapping("id/{id}/applications/{applicationId}")
    public List<AppGroupEntry> removeApplication(@PathVariable("id") long groupId,
                                                 @PathVariable("applicationId") long applicationId,
                                                 HttpServletRequest request) throws InsufficientPrivelegeException {
        LOG.info("Removing application: {}, from group: {} ", applicationId, groupId);
        return appGroupService.removeApplication(getUsernameForSB(request), groupId, applicationId);
    }

    @PostMapping("id/{id}/applications/list/remove")
    public List<AppGroupEntry> removeApplicationList(@PathVariable("id") long groupId,
                                                     @RequestBody List<Long> applicationIds,
                                                     HttpServletRequest request) throws InsufficientPrivelegeException {
        LOG.info("Removing applications: {}, from group: {} ", applicationIds, groupId);
        return appGroupService.removeApplications(getUsernameForSB(request), groupId, applicationIds);
    }

    @PostMapping("id/{id}/orgUnits")
    public List<AppGroupEntry> addOrgUnit(@PathVariable("id") long groupId,
                                          @RequestBody long orgUnitId,
                                          HttpServletRequest request) throws InsufficientPrivelegeException {
        LOG.info("Adding orgUnit: {}, to application group: {} ", orgUnitId, groupId);
        return appGroupService.addOrganisationalUnit(getUsernameForSB(request), groupId, orgUnitId);
    }

    @DeleteMapping("id/{id}/orgUnits/{orgUnitId}")
    public List<AppGroupEntry> removeOrgUnit(@PathVariable("id") long groupId,
                                             @PathVariable("orgUnitId") long orgUnitId,
                                             HttpServletRequest request) throws InsufficientPrivelegeException {
        LOG.info("Removing orgUnit: {}, from application group: {} ", orgUnitId, groupId);
        return appGroupService.removeOrganisationalUnit(getUsernameForSB(request), groupId, orgUnitId);
    }

    @PostMapping("id/{id}")
    public AppGroupDetail updateGroupOverview(@PathVariable("id") long groupId,
                                              @RequestBody AppGroup appGroup,
                                              HttpServletRequest request) throws InsufficientPrivelegeException {
        return appGroupService.updateOverview(getUsernameForSB(request), appGroup);
    }

    @PostMapping
    public Long createNewGroup(HttpServletRequest request) {
        return appGroupService.createNewGroup(getUsernameForSB(request));
    }

    @PostMapping("id/{id}/change-initiatives")
    public List<AppGroupEntry> addChangeInitiative(@PathVariable("id") long groupId,
                                                   @RequestBody long changeInitiativeId,
                                                   HttpServletRequest request) throws InsufficientPrivelegeException {
        LOG.info("Adding Change Initiative: {}, to group: {} ", changeInitiativeId, groupId);
        return appGroupService.addChangeInitiative(getUsernameForSB(request), groupId, changeInitiativeId);
    }

    @DeleteMapping("id/{id}/change-initiatives/{changeInitiativeId}")
    public List<AppGroupEntry> removeChangeInitiative(@PathVariable("id") long groupId,
                                                      @PathVariable("changeInitiativeId") long changeInitiativeId,
                                                      HttpServletRequest request) throws InsufficientPrivelegeException {
        LOG.info("Removing Change Initiative: {}, from group: {} ", changeInitiativeId, groupId);
        return appGroupService.removeChangeInitiative(getUsernameForSB(request), groupId, changeInitiativeId);
    }

    @PostMapping("id/{id}/change-initiatives/list")
    public List<AppGroupEntry> addChangeInitiativeList(@PathVariable("id") long groupId,
                                                       @RequestBody AppGroupBulkAddRequest bulkAddRequest,
                                                       HttpServletRequest request) throws InsufficientPrivelegeException {
        LOG.info("Adding change initiatives: {}, to group: {} ", bulkAddRequest.changeInitiativeIds(), groupId);
        return appGroupService.addChangeInitiatives(
                getUsernameForSB(request),
                groupId,
                bulkAddRequest.changeInitiativeIds());
    }

    @PostMapping("id/{id}/change-initiatives/list/remove")
    public List<AppGroupEntry> removeChangeInitiativeList(@PathVariable("id") long groupId,
                                                          @RequestBody List<Long> changeInitiativeIds,
                                                          HttpServletRequest request) throws InsufficientPrivelegeException {
        LOG.info("Removing change initiatives: {}, from group: {} ", changeInitiativeIds, groupId);
        return appGroupService.removeChangeInitiatives(getUsernameForSB(request), groupId, changeInitiativeIds);
    }

    @PostMapping("search")
    public List<AppGroup> search(@RequestBody String query) {
        EntitySearchOptions searchOptions = mkForEntity(EntityKind.APP_GROUP, query);
        return appGroupService.search(searchOptions);
    }

}
