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
define ['backbone',
        'hgn!templates/sidebar'],
(Backbone, sidebarTemplate) ->
  class SidebarView extends Backbone.View
    initialize: () ->
      @$el.html(sidebarTemplate({demogroups: @collection.toJSON()}))

    selectDemoGroup: (groupPath) ->
      @$el.find("a").parent().removeClass('active')
      $demogroupli = @$el.find("a[href='#" + groupPath + "']").parent()
      $demogroupli.addClass('active')

    selectDemo: (groupPath, demoPath) ->
      @selectDemoGroup groupPath
      $demoli = @$el.find("a[href='#" + groupPath + "/" + demoPath + "']").parent()
      $demoli.addClass('active')

    selectHome: () ->
      @$el.find("a").parent().removeClass('active')
      @$el.find("a[href='#home']").parent().addClass('active')

    selectAbout: () ->
      @$el.find("a").parent().removeClass('active')
      @$el.find("a[href='#about']").parent().addClass('active')
