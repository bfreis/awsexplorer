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
import javax.ws.rs.QueryParam;

import com.amazonaws.services.s3.AmazonS3;
import com.brunoreis.awsexplorer.demo.Demo;
import com.brunoreis.awsexplorer.demo.DemoOutput;
import com.google.inject.Inject;

@SuppressWarnings("UnusedDeclaration")
public class CreateBucket extends Demo {
  private final AmazonS3 s3;
  @Inject public CreateBucket(final AmazonS3 s3) {
    this.s3 = s3;
  }

  @GET @Path("/run") @Produces("application/json")
  public DemoOutput run(@QueryParam("bucketName") final String bucketName) {
    echo("running s3.createBucket(" + bucketName + ")");
    try {
      s3.createBucket(bucketName);
      echo("bucket " + bucketName + " created!");
    } catch (Exception e) {
      error(e.getClass().getSimpleName() + ": " + e.getMessage());
    }
    return makeOutput();
  }

  @Override public String getTitle() { return "Create Bucket"; }
  @Override public String getDescription() {
    return "This demo shows the usage of the command " +
            "s3.createBucket(String bucketName) to " +
            "create a bucket. Note that it is an error " +
            "to try to create a bucket that already exists.";
  }
}
