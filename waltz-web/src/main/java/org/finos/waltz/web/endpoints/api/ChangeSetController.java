/*
 * Waltz - Enterprise Architecture Platform
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
import org.finos.waltz.service.change_set.ChangeSetService;
import org.finos.waltz.model.EntityReference;
import org.finos.waltz.model.IdSelectionOptions;
import org.finos.waltz.model.change_set.ChangeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.finos.waltz.common.Checks.checkNotNull;
import static org.finos.waltz.web.WebUtilities.mkPath;


@RestController
@RequestMapping("/api/change-set")
public class ChangeSetController {

    private final ChangeSetService changeSetService;


    @Autowired
    public ChangeSetController(ChangeSetService changeSetService) {
        checkNotNull(changeSetService, "changeSetService cannot be null");
        this.changeSetService = changeSetService;
    }


    @GetMapping("id/{id}")
    public ChangeSet getById(@PathVariable("id") long id) {
        return changeSetService.getById(id);
    }

    @GetMapping("parent/{kind}/{id}")
    public List<ChangeSet> findByParentRef(@PathVariable("kind") String kind, @PathVariable("id") long id) {
        EntityReference ref = EntityReference.mkRef(EntityKind.valueOf(kind), id);
        return changeSetService.findByParentRef(ref);
    }

    @GetMapping("person/{employeeId}")
    public List<ChangeSet> findByPerson(@PathVariable("employeeId") String employeeId) {
        return changeSetService.findByPerson(employeeId);
    }

    @PostMapping("selector")
    public List<ChangeSet> findBySelector(@RequestBody IdSelectionOptions selectionOptions) {
        return changeSetService.findBySelector(selectionOptions);
    }
}
