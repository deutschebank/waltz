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

import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.svg.SvgDiagram;
import org.finos.waltz.model.user.SystemRole;
import org.finos.waltz.service.svg.SvgDiagramService;
import org.finos.waltz.service.user.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.finos.waltz.web.WebUtilities.*;


@RestController
@RequestMapping("/api/svg-diagram/")
public class SvgDiagramEndpointController {

    private final SvgDiagramService svgDiagramService;
    private final UserRoleService userRoleService;


    @Autowired
    public SvgDiagramEndpointController(SvgDiagramService svgDiagramService,
                                        UserRoleService userRoleService) {
        this.svgDiagramService = svgDiagramService;
        this.userRoleService = userRoleService;
    }

    @GetMapping()
    public Collection<SvgDiagram> findAll() {
        return svgDiagramService.findAll();
    }
    
    @GetMapping("group")
    public Collection<SvgDiagram> findByGroups(@RequestParam("group") List<String> groups) {
        return svgDiagramService.findByGroups(String.valueOf(groups));
    }
    
    @GetMapping("{id}")
    public SvgDiagram getById(@PathVariable("id") long id) {
        return svgDiagramService.getById(id);
    }
    
    @PostMapping("save")
    public boolean save(@RequestBody SvgDiagram diagram, @RequestParam("username") String username) {
        Set<String> set = new HashSet<>(SystemRole.ADMIN.ordinal());
        requireRoleForSB(userRoleService, username, set);
        return svgDiagramService.save(diagram);
    }
    
    @DeleteMapping("{id}")
    public boolean remove(@PathVariable("id") long id, @RequestParam("username") String username) {
        Set<String> set = new HashSet<>(SystemRole.ADMIN.ordinal());
        requireRoleForSB(userRoleService, username, set);
        return svgDiagramService.remove(id);
    }
}
