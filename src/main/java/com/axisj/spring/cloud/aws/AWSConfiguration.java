package com.axisj.spring.cloud.aws;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.*;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import org.apache.http.HttpHost;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.config.QueueMessageHandlerFactory;
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory;
import org.springframework.cloud.aws.messaging.listener.QueueMessageHandler;
import org.springframework.cloud.aws.messaging.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSAsyncClient;

import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;

import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;


@Configuration
public class AWSConfiguration {

	@Value("${cloud.aws.credentials.accessKey}")
	private String accessKey;

	@Value("${cloud.aws.credentials.secretKey}")
	private String secretKey;

	@Value("${cloud.aws.region}")
	private String region;

	private String useProxy = "N";

	@Bean
	public BasicAWSCredentials basicAWSCredentials() {
		return new BasicAWSCredentials(accessKey, secretKey);
	}

	@Bean
	public AmazonS3Client amazonS3Client(AWSCredentials awsCredentials) {
		AmazonS3Client amazonS3Client = new AmazonS3Client(awsCredentials);
		amazonS3Client.setRegion(Region.getRegion(Regions.fromName(region)));
		return amazonS3Client;
	}

	@Lazy
	@Bean(name = "amazonSQS", destroyMethod = "shutdown")
	public AmazonSQSAsync amazonSQSClient() {
		AmazonSQSAsyncClient awsSQSAsyncClient;
		if (useProxy.equalsIgnoreCase("Y")) {
			ClientConfiguration clientConfig = new ClientConfiguration();
			clientConfig.setProxyHost("localhost");
			clientConfig.setProxyPort(8080);

			awsSQSAsyncClient = new AmazonSQSAsyncClient(new DefaultAWSCredentialsProviderChain(), clientConfig);
		} else {
			awsSQSAsyncClient = new AmazonSQSAsyncClient(new DefaultAWSCredentialsProviderChain());
		}

		awsSQSAsyncClient.setRegion(Region.getRegion(Regions.fromName("us-east-1")));

		return awsSQSAsyncClient;
	}


	@Bean
	public QueueMessagingTemplate queueMessagingTemplate() {

		return new QueueMessagingTemplate(amazonSQSClient()
		);
	}


	@Bean
	public SimpleMessageListenerContainer simpleMessageListenerContainer() {
		SimpleMessageListenerContainer msgListenerContainer = simpleMessageListenerContainerFactory().createSimpleMessageListenerContainer();
		msgListenerContainer.setMessageHandler(queueMessageHandler());

		return msgListenerContainer;
	}

	@Bean
	public SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory() {
		SimpleMessageListenerContainerFactory msgListenerContainerFactory = new SimpleMessageListenerContainerFactory();
		msgListenerContainerFactory.setAmazonSqs(amazonSQSClient());
		// max allowed is 10 messages per poller
		msgListenerContainerFactory.setMaxNumberOfMessages(10);
		msgListenerContainerFactory.setWaitTimeOut(1);
		// default is auto start
		msgListenerContainerFactory.setAutoStartup(true);
		return msgListenerContainerFactory;
	}

	@Bean
	public QueueMessageHandler queueMessageHandler() {
		QueueMessageHandlerFactory queueMsgHandlerFactory = new QueueMessageHandlerFactory();
		queueMsgHandlerFactory.setAmazonSqs(amazonSQSClient());

		QueueMessageHandler queueMessageHandler = queueMsgHandlerFactory.createQueueMessageHandler();



		return queueMessageHandler;
	}

}
