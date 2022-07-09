package com.sarika.model;

import lombok.Data;

@Data
public class Person {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Country country;
}
