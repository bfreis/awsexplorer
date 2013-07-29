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

import java.util.List;

import javax.annotation.Nullable;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Throwables;
import com.google.common.collect.FluentIterable;
import com.google.common.reflect.ClassPath;
import com.google.inject.Inject;
import com.google.inject.Injector;

@SuppressWarnings("UnusedDeclaration")
public abstract class DemoGroup {
  @Inject private Injector injector;

  public String getTitle() { return getClass().getSimpleName(); }
  public String getLongTitle() { return getTitle() + " demos"; }
  public String getDescription() { return "Demos for " + getTitle(); }
  public String getGroupPath() { return getClass().getSimpleName().toLowerCase(); }
  public List<Demo> getDemos() { return getDemos0().toList(); }

  @GET @Produces("application/json")
  public DemoGroup info() { return this; }

  @Path("/{demoName}")
  public Demo getDemo(@PathParam("demoName") final String demoName) {
    final Optional<Demo> demo = getDemos0().filter(new Predicate<Demo>() {
      @Override public boolean apply(@Nullable final Demo input) {
        assert input != null;
        return input.getDemoPath().equals(demoName);
      }
    }).first();
    return demo.get();
  }

  private FluentIterable<Demo> getDemos0() {
    try {
      final ClassPath classPath = ClassPath.from(getClass().getClassLoader());
      return FluentIterable.from(classPath.getTopLevelClassesRecursive(getClass().getPackage().getName()))
              .transform(new Function<ClassPath.ClassInfo, Class<?>>() {
                @Nullable @Override public Class<?> apply(@Nullable final ClassPath.ClassInfo input) {
                  assert input != null; return input.load();
                }
              })
              .filter(new Predicate<Class<?>>() {
                @Override public boolean apply(@Nullable final Class<?> input) {
                  return Demo.class.isAssignableFrom(input);
                }
              })
              .transform(new Function<Class<?>, Demo>() {
                @Nullable @Override public Demo apply(@Nullable final Class<?> input) {
                  return (Demo) injector.getInstance(input);
                }
              });
    } catch (Exception e) {
      throw Throwables.propagate(e);
    }
  }
}
