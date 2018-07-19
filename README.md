# NlpTest

Test Stanford CoreNLP on AWS Lambda. 

# Purpose

Using Stanford CoreNLP system [1], please develop a simple AWS Lambda [2] function that accepts a paragraph of text in English and returns: 1) overall “sentiment” of the paragraph 2) named entities [3] recognised by the standard model. Please provide a complete repository of code with instructions for deploying the function. 

[1] https://stanfordnlp.github.io/CoreNLP
[2] https://aws.amazon.com/lambda/
[3] https://en.wikipedia.org/wiki/Named-entity_recognition

# Deployment 1 - Build Lambda Deployment Package on EC2  

I have prepared a Lambda Deployment Package for your convenience. The Lambda package (jar) is available at: http://second.world.stockvirtual.info/lambda-java-example-1.0-SNAPSHOT.jar 
This package is ready to be deployed to Lambda.

Follow the following steps to start from the scratch: 

1. create an ubuntu EC2 instance and make sure jdk and maven installed correctly.  

2. create a project folder, check out this repository. 

3. use the command "mvn package" to buildthe the jar file which should be located under the 'target' folder.

# Deployment 2 - setup Lambda function 

1. upload the package (jar file) to a S3 bucket. Because of the package size is larger than 50MB, so it needs to be uploaded to S3 before imported to Lambda.

2. create a Lambda function, set runtime to Java 8 and handling function to 'NlpTest::handleRequest'. 

3. import the jar file from S3. 

4. create an environment variable 'INPUT' with a paragraph of text. 

5. Click on 'Save' and then click on 'Test'. The result should look like this:
"Detected entity:  She(PERSON)  Tom(PERSON)  UK(COUNTRY)  George(PERSON)  King(TITLE) The sentiment of the sentences are: Negative, Very positive, Negative, Neutral, Positive, Neutral, Negative, , and the overall sentiment rating is 1".





