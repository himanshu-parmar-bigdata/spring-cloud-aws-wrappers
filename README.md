
Spring-Boot based Amazon S3 and SQS Wrapper Sample
=======



```
Spring Boot + Spring Cloud AWS
```

###Description
This little demo project helps to understand:
1) How to create AWS Configuration for initalizing and injecting BasicAWSCredentials, amazonS3Client, SimpleMessageListenerContainer, QueueMessagingTemplate, AmazonSQSAsync(client)
2) How to create S3Wrapper to facilitate create and delete objects on S3
3) How to create SQSWrapper to facilitate create and delete messages on SQS

4) Create SQS listener for any arbitary SQS queue and start it as background server and configure it to read number of messages during each poll
 

###How to Open
```
IntelliJ -> Open -> build.gradle
```

###Compile Setting
```
Open IntelliJ Preference
    - Build, Execution, Deployment -> Compiler
        -> Check 'Make project automatically'
```

###How to Run
```
- application-example.properties rename to application.properties
- set your AWS accessKey & secretKey
- commands to run from terminal
  gradle build 
  gradle bootRun
  
URL to verify http://localhost:8080//api/aws/sqs/readMessage
```

### API 
```
GET /api/aws/s3/list : List of Objects
GET /api/aws/s3/download?key={key} : Download
POST /api/aws/s3/upload : Upload

GET /api/aws/sqs/readMessage : reads a message from SQS
POST /api/aws/sqs/addMessage : push a message to SQS 

```

### Environment
- Java 8
- Spring Boot 1.3.0.M2
- Spring Cloud AWS 1.0.2.RELEASE
- Gradle 2.4
