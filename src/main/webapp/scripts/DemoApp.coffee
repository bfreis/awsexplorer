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
define ['cs!./views/HomeView',
        'cs!./views/AboutView',
        'cs!./views/DemoGroupView',
        'cs!./views/DemoView'],
(HomeView, AboutView, DemoGroupView, DemoView) ->
  class DemoApp
    constructor: (@contentView, @sidebarView, @demoGroupCollection) ->

    home: () ->
      @contentView.setSubview(new HomeView()).render()
      @sidebarView.selectHome()

    about: () ->
      @contentView.setSubview(new AboutView()).render()
      @sidebarView.selectAbout()

    license: () ->
      @contentView.setSubview(new LicenseView()).render()
      @sidebarView.selectLicense()

    demogroup: (groupPath) ->
      group = @findGroupByPath groupPath
      @sidebarView.selectDemoGroup(groupPath)
      @contentView.setSubview(new DemoGroupView(group.toJSON())).render()

    demo: (groupPath, demoPath) ->
      group = @findGroupByPath groupPath
      demo = @findDemoByPath group, demoPath
      @sidebarView.selectDemo(groupPath, demoPath)
      @contentView.setSubview(new DemoView(group.toJSON(), demo)).render()

    findGroupByPath: (groupPath) ->
      @demoGroupCollection.find (dg) -> dg.get("groupPath") == groupPath

    findDemoByPath: (group, demoPath) ->
      if (demoPath)
        _.find group.get("demos"), (d) -> d.demoPath == demoPath
      else
        null
