package com.sarika.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
@DynamoDBTable(tableName = "Person")
public class PersonDto {
    @DynamoDBRangeKey(attributeName = "firstName_lastName")
    private String firstName_lastName;
    @DynamoDBAttribute
    private String email;
    @DynamoDBHashKey
    private String phone;
    @DynamoDBAttribute
    private String country;
}
