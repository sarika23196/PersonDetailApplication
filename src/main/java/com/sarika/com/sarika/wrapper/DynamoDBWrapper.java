package com.sarika.com.sarika.wrapper;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.sarika.model.Person;
import com.sarika.model.PersonDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DynamoDBWrapper {

    private Logger logger = LogManager.getLogger(DynamoDBWrapper.class);

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public void writeToDynamoDB(List<Person> records) {

        for (Person person: records) {

            PersonDto personDto = PersonDto.builder()
                    .firstName_lastName(person.getFirstName()+"_"+person.getLastName())
                    .email(person.getEmail())
                    .phone(person.getPhone())
                    .country(person.getCountry().toString())
                    .build();

            try {
                dynamoDBMapper.save(personDto);
            }catch (RuntimeException e){
                logger.error("Error occurred while writing to DynamoDB table ");
                throw e;
            }

        }

    }
}
