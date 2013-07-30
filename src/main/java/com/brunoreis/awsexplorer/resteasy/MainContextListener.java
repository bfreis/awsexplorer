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
package com.brunoreis.awsexplorer.resteasy;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;

import com.google.common.collect.ImmutableList;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;
import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;

@WebListener
public class MainContextListener extends GuiceResteasyBootstrapServletContextListener {
  private final DemoModule demoModule = new DemoModule();

  @Override protected void withInjector(final Injector injector) {
    demoModule.injectInjector(injector);
  }

  @Override protected Stage getStage(final ServletContext context) {
    return Stage.PRODUCTION;
  }

  @Override protected List<Module> getModules(final ServletContext context) {
    return ImmutableList.<Module>of(demoModule);
  }
}

