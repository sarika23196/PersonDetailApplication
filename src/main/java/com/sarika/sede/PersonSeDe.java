package com.sarika.sede;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sarika.model.Person;
import com.sarika.model.Persons;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.IOException;

@Component
public class PersonSeDe {

    private Logger logger = LogManager.getLogger(PersonSeDe.class);

    @Autowired
    private ObjectMapper objectMapper;

    public Persons deserialization(String inputStringFile) throws IOException {

        logger.info("Started deserialization string to JSON");

        Persons persons;
        try {
            persons = objectMapper.readValue(inputStringFile, Persons.class);
        }catch (JsonProcessingException e) {
            logger.error("Error occurred while deserializing", e);
            throw e;
        }

        logger.info("Successfully deserialized JSON from string");

        return persons;
    }

    public String serialization(Persons persons) throws JsonProcessingException {

        String fileContent;

        try {
            fileContent = objectMapper.writeValueAsString(persons);
        } catch (JsonProcessingException e) {
            logger.error("Error occurred while serializing", e);
            throw e;
        }

        return fileContent;
    }

}
