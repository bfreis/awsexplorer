# Copyright (C) 2013 Bruno França dos Reis <bfreis@gmail.com>
# All rights reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
define ['jquery',
        'backbone',
        'cs!./DemoApp',
        'cs!./DemoRouter',
        'cs!./views/base/ContentView',
        'cs!./views/base/SidebarView',
        'cs!./domain/DemoGroupCollection',
        'jquery-serialize-object',
        'bootstrap'],
($, Backbone, DemoApp, DemoRouter, ContentView, SidebarView, DemoGroupCollection) -> $ () ->
  demoGroupCollection = new DemoGroupCollection

  demoGroupCollection.once 'reset', () ->
    contentView = new ContentView({el: "#content"})
    sidebarView = new SidebarView({el: "#sidebar", collection: demoGroupCollection})

    app = new DemoApp(contentView, sidebarView, demoGroupCollection)
    router = new DemoRouter(app)
    Backbone.history.start();

  demoGroupCollection.fetch({reset: true})
