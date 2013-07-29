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
        'underscore',
        'cs!../api',
        'hgn!templates/demo'],
(Backbone, _, api, demoTemplate) ->
  class DemoView extends Backbone.View
    constructor: (@group, @demo) -> super

    events:
      "click #runDemo": "runDemo"

    render: () -> @setElement(demoTemplate(@demo))

    runDemo: (e) ->
      e.preventDefault()

      $demoResults = @$el.find("#demoResults")
      $demoError = @$el.find("#demoError")
      $demoOutput = @$el.find("#demoOutput")
      $runDemo = @$el.find("#runDemo")

      $runDemo.addClass "disabled"
      $runDemo.text "Running..."

      $demoOutput.empty()
      $demoError.addClass "hidden"

      gPath = @group.groupPath
      dPath = @demo.demoPath
      params = @$el.find("form").serializeObject()

      api.runDemo gPath, dPath, params, (err, results) ->
        $runDemo.removeClass "disabled"
        $runDemo.text "Run demo"
        $demoResults.removeClass "hidden"
        if (err || results.error)
          $demoError.removeClass "hidden"
        if (err)
          $demoOutput.append results
        else
          _.each results.lines, (line) ->
            $demoOutput.append(line + "\n")

