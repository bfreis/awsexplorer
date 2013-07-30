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

import java.util.concurrent.atomic.AtomicReference;

import javax.annotation.PreDestroy;

import com.amazonaws.AmazonWebServiceClient;
import com.amazonaws.http.IdleConnectionReaper;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.brunoreis.awsexplorer.demo.DemoGroups;
import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import org.jboss.resteasy.plugins.providers.jackson.JacksonJsonpInterceptor;

public class DemoModule extends AbstractModule {
  private final AtomicReference<Injector> injectorRef = new AtomicReference<>();

  void injectInjector(final Injector injector) {
    injectorRef.set(injector);
  }

  private void shutdownClient(final Object client) {
    if (client instanceof AmazonWebServiceClient) {
      ((AmazonWebServiceClient) client).shutdown();
    }
  }

  @PreDestroy public void terminateClients() {
    final Injector injector = injectorRef.get();
    if (injector != null) {
      shutdownClient(injector.getInstance(AmazonS3.class));
      shutdownClient(injector.getInstance(AmazonSQS.class));
    }
    IdleConnectionReaper.shutdown();
  }

  @Override protected void configure() {
    bind(DemoGroups.class);
    bind(AmazonS3.class).to(AmazonS3Client.class).in(Singleton.class);
    bind(AmazonSQS.class).to(AmazonSQSClient.class).in(Singleton.class);
    bind(JacksonJsonpInterceptor.class); // just to initialize the class
  }
}
