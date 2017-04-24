package com.axisj.spring.cloud.aws.controllers;

import com.axisj.spring.cloud.aws.sqs.SQSWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by himanshupannar on 4/23/17.
 */
@RestController
@RequestMapping("/api/aws/sqs")
public class SQSController {

    @Autowired
    SQSWrapper sqsWrapper;

    @RequestMapping(value = "/readMessage", method = RequestMethod.GET)
    public String readSqs() throws IOException {
        return sqsWrapper.readFromSQS();

    }

    @RequestMapping(value = "/addMessage", method = RequestMethod.POST)
    public String addMessage(@RequestParam("message") String message) throws IOException {
        return sqsWrapper.writeToSQS(message);
    }
}
