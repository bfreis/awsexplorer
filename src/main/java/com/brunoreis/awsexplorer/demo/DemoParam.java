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
package com.brunoreis.awsexplorer.demo;

@SuppressWarnings("UnusedDeclaration")
public class DemoParam {
  private final String name;
  private final String info;

  public DemoParam(final String name, final String info) {
    this.name = name;
    this.info = info;
  }

  public String getName() { return name; }
  public String getInfo() { return info; }
}
