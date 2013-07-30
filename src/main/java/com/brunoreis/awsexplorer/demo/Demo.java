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

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.brunoreis.awsexplorer.VersionProvider;
import com.google.common.collect.ImmutableList;
import com.google.common.reflect.Invokable;
import com.google.common.reflect.Parameter;

@SuppressWarnings("UnusedDeclaration")
public abstract class Demo {
  public final List<DemoParam> getParams() { return getParams0(); }
  public String getTitle() { return "Unknown"; }
  public String getDescription() { return "No description."; }
  public String getDemoPath() { return getClass().getSimpleName().toLowerCase(); }
  public String getGithubLink() { return "https://github.com/bfreis/awsexplorer/blob/" + VersionProvider.getVersion() + "/src/main/java/" + getClass().getName().replaceAll("\\.", "/") + ".java"; }

  @GET @Produces("application/json")
  public final Demo info() { return this; }


  private List<DemoParam> getParams0() {
    final Invokable<?, Object> run = Invokable.from(findRunMethod());
    final List<DemoParam> list = new ArrayList<>();
    for (final Parameter parameter : run.getParameters()) {
      final String name = extractName(parameter);
      final String type = extractType(parameter);
      list.add(new DemoParam(name, type));
    }
    return list;
  }

  private String extractType(final Parameter parameter) {
    final StringBuilder sb = new StringBuilder();
    final Type type = parameter.getType().getType();
    if (type instanceof Class) {
      sb.append(((Class) type).getSimpleName());
    } else {
      sb.append(type.toString());
    }

    final DefaultValue defaultValue = parameter.getAnnotation(DefaultValue.class);
    if (defaultValue != null) {
      sb.append(", optional, default = ").append(defaultValue.value());
    } else {
      sb.append(", required");
    }

    return sb.toString();
  }

  private String extractName(final Parameter parameter) {
    final QueryParam queryParam = parameter.getAnnotation(QueryParam.class);
    if (queryParam == null) {
      throw new IllegalArgumentException("parameter " + parameter + " is not annotated with QueryParam!");
    }
    return queryParam.value();
  }

  private Method findRunMethod() {
    for (final Method method : getClass().getDeclaredMethods()) {
      if (method.getName().equals("run")) {
        return method;
      }
    }
    throw new IllegalStateException("Cannot find run method");
  }


  private boolean error = false;
  private ImmutableList.Builder<String> lines = ImmutableList.builder();

  protected final void echo(final Object line) { lines.add(String.valueOf(line)); }
  protected final void echo() { echo(""); }
  protected final void error(final Object line) { echo(line); error = true; }
  protected final void error(final Exception e) { error(e.getClass().getSimpleName() + ": " + e.getMessage());}

  protected final DemoOutput makeOutput() {
    return new DemoOutput(lines.build(), error);
  }
}

