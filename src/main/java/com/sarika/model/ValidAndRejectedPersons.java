package com.sarika.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ValidAndRejectedPersons {

    private List<Person> rejectedPersonList;
    private List<Person> ValidPersonList;

}
