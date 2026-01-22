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
import org.finos.waltz.model.EntityKind;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.IdSelectionOptions;
import org.finos.waltz.model.Operation;
import org.finos.waltz.model.SetAttributeCommand;
import org.finos.waltz.model.command.CommandResponse;
import org.finos.waltz.model.entity_search.EntitySearchOptions;
import org.finos.waltz.model.entity_search.ImmutableEntitySearchOptions;
import org.finos.waltz.model.physical_specification.ImmutablePhysicalSpecificationDeleteCommand;
import org.finos.waltz.model.physical_specification.PhysicalSpecification;
import org.finos.waltz.model.physical_specification.PhysicalSpecificationDeleteCommand;
import org.finos.waltz.service.permission.permission_checker.FlowPermissionChecker;
import org.finos.waltz.service.physical_specification.PhysicalSpecificationService;
import org.finos.waltz.web.WebUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.web.WebUtilities.*;
import static org.finos.waltz.web.WebUtilities.readBody;

@RestController
@RequestMapping("/api/physical-specification/")
public class PhysicalSpecificationEndpointController {

    private final PhysicalSpecificationService specificationService;
    private final FlowPermissionChecker flowPermissionChecker;


    @Autowired
    public PhysicalSpecificationEndpointController(PhysicalSpecificationService specificationService,
                                                   FlowPermissionChecker flowPermissionChecker) {
        checkNotNull(specificationService, "specificationService cannot be null");
        checkNotNull(flowPermissionChecker, "flowPermissionChecker cannot be null");

        this.specificationService = specificationService;
        this.flowPermissionChecker = flowPermissionChecker;
    }

    @GetMapping("application/{kind}/{id}")
    public Set<PhysicalSpecification> findByApp(@PathVariable("kind") EntityKind kind, @PathVariable("id") long id) {
        return specificationService.findByEntityReference(EntityReference.mkRef(kind, id));
    }

    @PostMapping("selector")
    public Collection<PhysicalSpecification> findBySelector(@RequestBody IdSelectionOptions options) {
        return specificationService.findBySelector(options);
    }

    @PostMapping("ids")
    public Collection<PhysicalSpecification> findByIds(@RequestBody Set<Long> ids) {
        return specificationService.findByIds(ids);
    }

    @PostMapping("search")
    public List<PhysicalSpecification> search(@RequestBody EntitySearchOptions options,
                                              HttpServletRequest principal) throws IOException {
        String username = getUsernameForSB(principal);
        return specificationService.search(
                ImmutableEntitySearchOptions.builder()
                        .userId(username)
                        .searchQuery(readBodyForSB(principal, String.class))
                        .build());
    }

    @GetMapping("id/{id}")
    public PhysicalSpecification getById(@PathVariable("id") long id) {
        return specificationService.getById(id);
    }

    @GetMapping("external-id/{externalId}")
    public Collection<PhysicalSpecification> findByExternalId(@PathVariable("externalId") String externalId) {
        return specificationService.findByExternalId(externalId);
    }

    @DeleteMapping("{id}")
    public CommandResponse<PhysicalSpecificationDeleteCommand> deleteSpecification(@PathVariable("id") long specId, Principal principal) throws InsufficientPrivelegeException {
        String username = principal.getName();
        checkHasPermission(EntityReference.mkRef(EntityKind.PHYSICAL_SPECIFICATION, specId), username);

        ImmutablePhysicalSpecificationDeleteCommand deleteCommand = ImmutablePhysicalSpecificationDeleteCommand.builder()
                .specificationId(specId)
                .build();

        return specificationService.markRemovedIfUnused(deleteCommand, username);
    }

    @PostMapping("id/{id}/attribute")
    public int updateAttribute(@PathVariable("id") long id, @RequestBody SetAttributeCommand command, Principal principal) throws IOException, InsufficientPrivelegeException {
        String username = principal.getName();
        checkHasPermission(command.entityReference(), username);
        return specificationService.updateAttribute(username, command);
    }

    @GetMapping("id/{id}/permissions")
    public Set<Operation> findPermissionsForSpec(@PathVariable("id") long id, Principal principal) {
        return flowPermissionChecker.findPermissionsForSpec(id, principal.getName());
    }

    private void checkHasPermission(EntityReference ref, String username) throws InsufficientPrivelegeException {
        Set<Operation> permissions = flowPermissionChecker.findPermissionsForSpec(ref.id(), username);
        flowPermissionChecker.verifyEditPerms(permissions, EntityKind.PHYSICAL_SPECIFICATION, username);
    }
}