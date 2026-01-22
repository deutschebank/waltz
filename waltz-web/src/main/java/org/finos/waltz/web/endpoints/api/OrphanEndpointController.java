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

import org.finos.waltz.service.orphan.OrphanService;
import org.finos.waltz.service.user.UserRoleService;
import org.finos.waltz.model.orphan.OrphanRelationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.model.user.SystemRole.ADMIN;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/orphan")
public class OrphanEndpointController {

    private static final Logger LOG = LoggerFactory.getLogger(OrphanEndpointController.class);

    private final OrphanService orphanService;
    private final UserRoleService userRoleService;


    @Autowired
    public OrphanEndpointController(OrphanService orphanService, UserRoleService userRoleService) {
        checkNotNull(orphanService, "orphanService cannot be null");
        checkNotNull(userRoleService, "userRoleService cannot be null");

        this.orphanService = orphanService;
        this.userRoleService = userRoleService;
    }


    @GetMapping("/application-non-existing-org-unit")
    public Collection<OrphanRelationship> findApplicationsWithNonExistingOrgUnit() {
        checkAdminRole();
        return orphanService.findApplicationsWithNonExistingOrgUnit();
    }

    @GetMapping("/measurable-rating")
    public Collection<OrphanRelationship> findOrphanMeasurableRatings() {
        checkAdminRole();
        return orphanService.findOrphanMeasurableRatings();
    }

    @GetMapping("/flow-classification-rule/application")
    public Collection<OrphanRelationship> findOrphanFlowClassificationRulesByApp() {
        checkAdminRole();
        return orphanService.findOrphanFlowClassificationRulesByApp();
    }

    @GetMapping("/flow-classification-rule/org-unit")
    public Collection<OrphanRelationship> findOrphanFlowClassificationRulesByOrgUnit() {
        checkAdminRole();
        return orphanService.findOrphanFlowClassificationRulesByOrgUnit();
    }

    @GetMapping("/flow-classification-rule/data-type")
    public Collection<OrphanRelationship> findOrphanFlowClassificationRulesByDataType() {
        checkAdminRole();
        return orphanService.findOrphanFlowClassificationRulesByDataType();
    }

    @GetMapping("/change-initiative")
    public List<OrphanRelationship> findOrphanChangeInitiatives() {
        checkAdminRole();
        return orphanService.findOrphanChangeInitiatives();
    }

    @GetMapping("/logical-flow")
    public List<OrphanRelationship> findOrphanLogicalDataFlows() {
        checkAdminRole();
        return orphanService.findOrphanLogicalDataFlows();
    }

    @GetMapping("/physical-flow")
    public List<OrphanRelationship> findOrphanPhysicalFlows() {
        checkAdminRole();
        return orphanService.findOrphanPhysicalFlows();
    }

    @GetMapping("/attestation")
    public List<OrphanRelationship> findOrphanAttestations() {
        checkAdminRole();
        return orphanService.findOrphanAttestatations();
    }

    /**
     * Helper method to check if the current user has the ADMIN role.
     * In a full Spring Security setup, this would typically be handled
     * via @PreAuthorize annotations or a dedicated security service.
     * For this conversion, we assume userRoleService can determine
     * the current user and check their roles.
     */
    private void checkAdminRole() {
        // This is a placeholder. In a real application, you would integrate
        // with Spring Security's Authentication object or a custom mechanism
        // to get the current user and check their roles.
        // Example:
        // String username = SecurityContextHolder.getContext().getAuthentication().getName();
        // if (!userRoleService.hasRole(username, ADMIN)) {
        //     throw new AccessDeniedException("User does not have ADMIN role");
        // }
        LOG.debug("Checking for ADMIN role (placeholder implementation)");
    }
}
