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
import org.finos.waltz.model.physical_flow_participant.ParticipationKind;
import org.finos.waltz.model.physical_flow_participant.PhysicalFlowParticipant;
import org.finos.waltz.service.physical_flow_participant.PhysicalFlowParticipantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.web.WebUtilities.getUsernameForSB;
import static org.finos.waltz.web.WebUtilities.mkPath;


@RestController
@RequestMapping(PhysicalFlowParticipantEndpointController.BASE_URL)
public class PhysicalFlowParticipantEndpointController {

    private static final Logger LOG = LoggerFactory.getLogger(PhysicalFlowParticipantEndpointController.class);
    public static final String BASE_URL = "/api/physical-flow-participant/";

    private final PhysicalFlowParticipantService service;

    @Autowired
    public PhysicalFlowParticipantEndpointController(PhysicalFlowParticipantService participantService) {
        checkNotNull(participantService, "participantService cannot be null");
        this.service = participantService;
    }


    @GetMapping("physical-flow/{id}")
    public Collection<PhysicalFlowParticipant> findByPhysicalFlowId(@PathVariable("id") long physicalFlowId) {
        return service.findByPhysicalFlowId(physicalFlowId);
    }


    @GetMapping("participant/{kind}/{id}")
    public Collection<PhysicalFlowParticipant> findByParticipant(@PathVariable("kind") String kind, @PathVariable("id") long id) {
        EntityReference ref = EntityReference.mkRef(EntityKind.valueOf(kind), id);
        return service.findByParticipant(ref);
    }


    @DeleteMapping("physical-flow/{physicalFlowId}/{kind}/{participantKind}/{participantId}")
    public boolean remove(@PathVariable("physicalFlowId") long physicalFlowId,
                          @PathVariable("kind") ParticipationKind kind,
                          @PathVariable("participantKind") String participantKind,
                          @PathVariable("participantId") long participantId,
                          HttpServletRequest principal) throws InsufficientPrivelegeException {

        String username = getUsernameForSB(principal);
        service.checkHasPermission(physicalFlowId, username);

        EntityReference participantRef = EntityReference.mkRef(EntityKind.valueOf(participantKind), participantId);

        return service.remove(
                physicalFlowId,
                kind,
                participantRef,
                username);
    }


    @PostMapping("physical-flow/{physicalFlowId}/{kind}/{participantKind}/{participantId}")
    public boolean add(@PathVariable("physicalFlowId") long physicalFlowId,
                       @PathVariable("kind") ParticipationKind kind,
                       @PathVariable("participantKind") String participantKind,
                       @PathVariable("participantId") long participantId,
                       HttpServletRequest principal) throws InsufficientPrivelegeException {

        String username = getUsernameForSB(principal);
        service.checkHasPermission(physicalFlowId, username);
        EntityReference participantRef = EntityReference.mkRef(EntityKind.valueOf(participantKind), participantId);
        return service.add(physicalFlowId, kind, participantRef, username);
    }
}
