/*
 * Copyright (C) 2013 Bruno França dos Reis <bfreis@gmail.com>
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
require.config({
  optimize: 'uglify2',
  modules: [
    {name: 'config'}
  ],
  shim: {
    'underscore': {exports: '_'},
    'backbone': {
      deps: ['underscore', 'jquery'],
      exports: 'Backbone'
    },
    'jquery-serialize-object': {deps: ['jquery']},
    bootstrap: {deps: ['jquery']}
  },
  paths: {
    jquery: ['//cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min', 'libs/jquery-2.0.3'],
    underscore: ['//cdnjs.cloudflare.com/ajax/libs/lodash.js/1.3.1/lodash.min', 'libs/lodash-1.3.1'],
    backbone: ['//cdnjs.cloudflare.com/ajax/libs/backbone.js/1.0.0/backbone-min', 'libs/backbone-1.0.0'],
    bootstrap: ['//netdna.bootstrapcdn.com/bootstrap/3.0.0-rc1/js/bootstrap.min', 'libs/bootstrap-3.0.0-rc1'],

    'jquery-serialize-object': 'libs/jquery-serialize-object',

    hogan: 'libs/require-plugins/require-hogan/hogan-2.0.0',
    cs: 'libs/require-plugins/require-cs/cs-0.4.3',
    'coffee-script': 'libs/require-plugins/require-cs/coffee-script-1.6.2-min',
    hgn: 'libs/require-plugins/require-hogan/hgn-0.2.1',
    text: 'libs/require-plugins/text-2.0.6'
  },
  pragmasOnSave: {excludeHogan: true},
  stubModules: ['cs', 'coffee-script', 'hgn', 'text']
});
require(['cs!scripts/main']);
