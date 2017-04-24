package com.axisj.spring.cloud.aws.sqs;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class MessageConsumer {


   @MessageMapping("dev-hparmar-queue-1")
   public void onRandomQMessage(String payload) {
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
       Date now = new Date();
       String strDate = sdf.format(now);
       //System.out.println(i);
        System.out.println(payload+" "+ strDate);
   }
}