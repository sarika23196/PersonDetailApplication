package com.sarika;

import com.sarika.com.sarika.wrapper.DynamoDBWrapper;
import com.sarika.com.sarika.wrapper.S3ClientWrapper;
import com.sarika.com.sarika.wrapper.SNSWrapper;
import com.sarika.model.Persons;
import com.sarika.model.ValidAndRejectedPersons;
import com.sarika.sede.PersonSeDe;
import com.sarika.validate.PersonValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

//orchestrator
@Component
public class PersonProcessor {

    private Logger logger = LogManager.getLogger(PersonProcessor.class);

    @Autowired
    private S3ClientWrapper s3ClientWrapper;
    @Autowired
    private PersonSeDe personSeDe;
    @Autowired
    private PersonValidator personValidate;
    @Autowired
    private DynamoDBWrapper dynamoDBWrapper;
    @Autowired
    private SNSWrapper snsWrapper;

    public void process( ) throws IOException {

        String fileName = System.getenv("fileName");
        logger.info("Started processing the file :" + fileName);

        String inputStringFile = s3ClientWrapper.downloadS3File(fileName);
        Persons persons = personSeDe.deserialization(inputStringFile);

        logger.info("Persons validation started");

        ValidAndRejectedPersons validAndRejectedPersons = personValidate.validatePerson(persons);

        logger.info("Successfully validated persons");
        logger.info("Number of valid records: " + validAndRejectedPersons.getValidPersonList().size());
        logger.info("Number of rejected records: " + validAndRejectedPersons.getRejectedPersonList().size());

        logger.info("Stared writing valid records to Dynamodb Person table");
        dynamoDBWrapper.writeToDynamoDB(validAndRejectedPersons.getValidPersonList());
        logger.info("Successfully written valid records to Person table");

        logger.info("Started deserialization Object to String");
        String contentToUpload = personSeDe.serialization(new Persons(validAndRejectedPersons.getRejectedPersonList()));
        logger.info("Successfully serialized string from Object");

        logger.info("Started writing rejected records to S3");
        s3ClientWrapper.uploadFile(contentToUpload,"rejected_" + fileName);
        logger.info("Successfully written rejected records to S3");

        logger.info("Sending report via SNS");
        snsWrapper.snsHandler(fileName, validAndRejectedPersons);
        logger.info("Successfully sent report via SNS");

        logger.info("Successfully processed the file: " + fileName);

    }
}
