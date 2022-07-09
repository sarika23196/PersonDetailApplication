package com.sarika.config;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sarika.com.sarika.wrapper.DynamoDBWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.sarika")
public class AppConfig {
    @Bean
    public DynamoDBMapper getDynamoDBMapper(){
         return new DynamoDBMapper(AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.US_WEST_2).build());
    }

    @Bean
    public AmazonS3 getAmazonS3() {
        return AmazonS3ClientBuilder.standard()
                .withRegion(Regions.US_WEST_2).build();
    }

    @Bean
    public AmazonSNS getAmazonSNSClient() {
       return AmazonSNSClient.builder().withRegion(Regions.US_WEST_2).build();
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }


}
