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
package com.brunoreis.awsexplorer.demo.sqs;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.brunoreis.awsexplorer.demo.Demo;
import com.brunoreis.awsexplorer.demo.DemoOutput;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.inject.Inject;

@SuppressWarnings("UnusedDeclaration")
public class SendMessages extends Demo {
  private final AmazonSQS sqs;
  @Inject public SendMessages(final AmazonSQS sqs) {
    this.sqs = sqs;
  }

  @GET @Path("/run") @Produces("application/json")
  public DemoOutput run(@QueryParam("queueUrl") final String queueUrl,
                        @QueryParam("numMessages") @DefaultValue("10") final int numMessages,
                        @QueryParam("messageTemplate") @DefaultValue("Message %d") final String messageTemplate) {
    try {
      Preconditions.checkNotNull(Strings.emptyToNull(queueUrl), "queueUrl was not provided");
      Preconditions.checkArgument(1 <= numMessages && numMessages <= 20, "numMessages must be 1 <= numMessages <= 20");
      echo("sending " + numMessages + " to queue " + queueUrl + "...");
      echo();
      for (int i = 1; i <= numMessages; i++) {
        final String messageBody;
        if (messageTemplate.contains("%d")) {
          messageBody = String.format(messageTemplate, i);
        } else {
          messageBody = messageTemplate;
        }
        echo("> sending message with body: " + messageBody);
        sqs.sendMessage(new SendMessageRequest(queueUrl, messageBody));
      }
      echo();
      echo("done!");
    } catch (Exception e) {
      error(e);
    }
    return makeOutput();
  }

  @Override public String getTitle() { return "Send Messages"; }
  @Override public String getDescription() {
    return "This demo shows the usage of the method sqs.sendMessage(). " +
            "to list the existing SQS queues on this account. " +
            "Note that a queue that has just been created " +
            "might take some time to appear in this list, " +
            "so please be patient!";
  }
}
