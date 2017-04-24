package com.axisj.spring.cloud.aws.sqs;

import com.amazonaws.services.sqs.AmazonSQSAsyncClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.SimpleMessageListenerContainer;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * Created by himanshupannar on 4/23/17.
 */
@Service
public class SQSWrapper {

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Autowired
    SimpleMessageListenerContainer simpleMessageListenerContainer;

    public String readFromSQS(){

        Message receive = queueMessagingTemplate.receive("dev-hparmar-queue-1");
        //start();

        return receive.getPayload().toString();
    }

    public String writeToSQS(String message){

        queueMessagingTemplate.convertAndSend("dev-hparmar-queue-1",
                MessageBuilder.withPayload(message).build());
        return message;

    }

    public void start(){
        simpleMessageListenerContainer.start("dev-hparmar-queue-1");
    }

}
