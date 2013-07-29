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
define ['jquery'], ($) ->
  delay = (ms, cb) -> setTimeout(cb, ms)

  cleanupParams = (params) ->
    newParams = {}
    for k,v of params
      if v && v != ""
        newParams[k] = v
    newParams

  class Api
    getDemoCollectionInfo: (demoCollection, cb) ->
      $.getJSON "demo/" + demoCollection + "/info", cb

    runDemo: (groupPath, demoPath, params, cb) -> delay 1, () ->
      runPath = "/demo/demogroups/" + groupPath + "/" + demoPath + "/run"
      cbError = (jqxhr, textStatus, error) -> cb(true, textStatus + ", " + error)
      cbSuccess = (data) -> cb(false, data)
      $.getJSON(runPath, cleanupParams params).done(cbSuccess).fail(cbError)

  new Api
