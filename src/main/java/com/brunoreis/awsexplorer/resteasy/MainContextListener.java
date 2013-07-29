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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.brunoreis.awsexplorer.demo.DemoGroups;
import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.Stage;
import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;
import org.jboss.resteasy.plugins.providers.jackson.JacksonJsonpInterceptor;

@WebListener
public class MainContextListener extends GuiceResteasyBootstrapServletContextListener {
  @Override protected void withInjector(final Injector injector) {}

  @Override protected Stage getStage(final ServletContext context) {
    return Stage.PRODUCTION;
  }

  @Override protected List<Module> getModules(final ServletContext context) {
    final ArrayList<Module> modules = new ArrayList<>();
    modules.add(new AbstractModule() {
      @Override protected void configure() {
        bind(DemoGroups.class);
        bind(AmazonS3.class).to(AmazonS3Client.class).in(Singleton.class);
        bind(AmazonSQS.class).to(AmazonSQSClient.class).in(Singleton.class);
        bind(JacksonJsonpInterceptor.class); // just to initialize the class
      }
    });
    return modules;
  }
}
