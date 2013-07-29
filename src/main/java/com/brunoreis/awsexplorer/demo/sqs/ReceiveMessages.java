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
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.brunoreis.awsexplorer.demo.Demo;
import com.brunoreis.awsexplorer.demo.DemoOutput;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.inject.Inject;

@SuppressWarnings("UnusedDeclaration")
public class ReceiveMessages extends Demo {
  private final AmazonSQS sqs;
  @Inject public ReceiveMessages(final AmazonSQS sqs) {
    this.sqs = sqs;
  }

  @GET @Path("/run") @Produces("application/json")
  public DemoOutput run(@QueryParam("queueUrl") final String queueUrl,
                        @QueryParam("maxNumMessages") @DefaultValue(value = "10") final int maxNumMessages,
                        @QueryParam("deleteMessages") @DefaultValue(value = "true") final boolean deleteMessages) {
    try {
      Preconditions.checkNotNull(Strings.emptyToNull(queueUrl), "queueUrl was not provided");
      echo("creating ReceiveMessageRequest: ");
      echo("> queueUrl: " + queueUrl);
      echo("> maxNumMessages: " + maxNumMessages);
      final ReceiveMessageRequest req = new ReceiveMessageRequest(queueUrl);
      req.setMaxNumberOfMessages(maxNumMessages);

      echo();
      echo("calling sqs.receiveMessage()...");
      final ReceiveMessageResult res = sqs.receiveMessage(req);

      echo();
      echo("received " + res.getMessages().size() + " messages!");

      for (final Message message : res.getMessages()) {
        echo();
        echo("Message: ");
        echo("> id: " + message.getMessageId());
        echo("> receipt handle: " + message.getReceiptHandle());
        echo("> body: " + message.getBody());
        echo();
        if (deleteMessages) {
          echo("calling sqs.deleteMessage()...");
          sqs.deleteMessage(new DeleteMessageRequest(queueUrl, message.getReceiptHandle()));
          echo("done.");
        }
      }
    } catch (Exception e) {
      error(e);
    }
    return makeOutput();
  }

  @Override public String getTitle() { return "Receive Messages"; }
  @Override public String getDescription() {
    return "Uses sqs.receiveMessage(...) to receive messages from " +
            "the specified queue. Requests the specified amount of " +
            "messages to SQS, and at most 10. The actual number of " +
            "received messages may be less than the specified amount, " +
            "even if there are enough messages available, due to the " +
            "consistency model of SQS. If the deleteMessages parameter is " +
            "set to true, then sqs.deleteMessage is called on each received " +
            "message; if the message is not deleted, it will eventually " +
            "return to the queue (after the visibility timeout expires); " +
            "finally, note that even if a message is deleted, the consistency " +
            "model of SQS cannot guarantee that it won't be received twice, " +
            "so remember to write your code accounting for this possibility!";
  }
}
