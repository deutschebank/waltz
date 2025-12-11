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

import actorView from "./pages/actor-view/actor-view";
import listView from "./pages/list-view/actor-list-view";

const baseState = {
  url: "actor",
};

const viewState = {
  url: "/{id:int}",
  views: {"content@": actorView},
};

const listViewState = {
  url: "/list",
  views: {"content@": listView.id},
};

const listViewStateCatchAll = {
  url: "/*path",
  views: {"content@": listView.id},
};

function setup($stateProvider) {
  $stateProvider
    .state("main.actor", baseState)
    .state("main.actor.view", viewState)
    .state("main.actor.list", listViewState)

    // this is only a catch-all and should not be used for state changes via pageInfo store/slice
    .state("main.actor.list.*", listViewStateCatchAll);
}

setup.$inject = ["$stateProvider"];

export default setup;
