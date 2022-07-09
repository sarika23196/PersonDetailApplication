package com.sarika.validate;

import com.sarika.model.Country;
import com.sarika.model.Person;
import com.sarika.model.Persons;
import com.sarika.model.ValidAndRejectedPersons;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class PersonValidator {

    public ValidAndRejectedPersons validatePerson(Persons persons) {

        List<Person> validPersons = new ArrayList<>();
        List<Person> rejectedPersons = new ArrayList<>();
        List<Person> personList = persons.getPersons();

        for (Person person: personList) {

            String phoneNumber = person.getPhone();

            if(phoneNumber.length() > 10) {

                if((person.getCountry() == Country.USA  && phoneNumber.substring(0, 2).equals(Country.USA.getCountryCode())
                && phoneNumber.substring(2).length() == 10 ) ||
                        (person.getCountry() == Country.INDIA && phoneNumber.substring(0,3).equals(Country.INDIA.getCountryCode())
                               &&  phoneNumber.substring(3).length() == 10 ) ) {

                    if(StringUtils.isNotBlank(person.getFirstName()) && StringUtils.isNotBlank(person.getLastName())
                    && StringUtils.isNotBlank(person.getEmail())) {

                        validPersons.add(person);
                    } else {
                        rejectedPersons.add(person);
                    }
                } else {
                    rejectedPersons.add(person);
                }
            } else {
                rejectedPersons.add(person);
            }
        }

        return new ValidAndRejectedPersons(rejectedPersons, validPersons);
    }





}
