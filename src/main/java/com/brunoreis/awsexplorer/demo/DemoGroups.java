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

import java.io.IOException;
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

@Path("/demogroups")
public class DemoGroups {
  private final Injector injector;

  @Inject public DemoGroups(final Injector injector) {
    this.injector = injector;
  }

  @GET @Produces("application/json")
  public List<DemoGroup> listDemoGroups() throws Exception {
    return getDemoGroups().toList();
  }

  @Path("/{groupPath}")
  public DemoGroup getDemoGroup(@PathParam("groupPath") final String groupPath) {
    final Optional<DemoGroup> g = getDemoGroups().firstMatch(new Predicate<DemoGroup>() {
      @Override public boolean apply(@Nullable final DemoGroup input) {
        assert input != null;
        return input.getGroupPath().equals(groupPath);
      }
    });
    if (!g.isPresent()) {
      throw new IllegalArgumentException("Cannot find demo group " + groupPath);
    } else {
      return g.get();
    }
  }

  private FluentIterable<DemoGroup> getDemoGroups() {
    final ClassPath classPath;
    try {
      classPath = ClassPath.from(getClass().getClassLoader());
    } catch (IOException e) {
      throw Throwables.propagate(e);
    }

    return FluentIterable.from(classPath.getTopLevelClassesRecursive(getClass().getPackage().getName()))
            .transform(new Function<ClassPath.ClassInfo, Class<?>>() {
              @Nullable @Override public Class<?> apply(@Nullable final ClassPath.ClassInfo input) {
                assert input != null; return input.load();
              }
            })
            .filter(new Predicate<Class<?>>() {
              @Override public boolean apply(@Nullable final Class<?> input) {
                return input != DemoGroup.class && DemoGroup.class.isAssignableFrom(input);
              }
            })
            .transform(new Function<Class<?>, DemoGroup>() {
              @Nullable @Override public DemoGroup apply(@Nullable final Class<?> input) {
                return (DemoGroup) injector.getInstance(input);
              }
            });
  }
}
