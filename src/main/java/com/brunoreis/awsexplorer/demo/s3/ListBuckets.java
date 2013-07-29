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
package com.brunoreis.awsexplorer.demo.s3;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.brunoreis.awsexplorer.demo.Demo;
import com.brunoreis.awsexplorer.demo.DemoOutput;
import com.google.inject.Inject;

@SuppressWarnings("UnusedDeclaration")
public class ListBuckets extends Demo {
  private final AmazonS3 s3;
  @Inject public ListBuckets(final AmazonS3 s3) {
    this.s3 = s3;
  }

  @GET @Path("/run") @Produces("application/json")
  public DemoOutput run() {
    echo("running s3.listBuckets()...");
    try {
      for (final Bucket bucket : s3.listBuckets()) {
        echo("Bucket: " + bucket.getName());
      }
    } catch (Exception e) {
      error(e);
    }
    return makeOutput();
  }

  @Override public String getTitle() { return "List Buckets"; }
  @Override public String getDescription() {
    return "This demo uses the method s3.listBuckets() " +
            "to list the existing S3 buckets on this account. " +
            "Note that a bucket that has just been created " +
            "might take some time to appear in this list, " +
            "so please be patient!";
  }
}
