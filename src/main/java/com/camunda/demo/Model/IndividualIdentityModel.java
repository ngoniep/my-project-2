package com.camunda.demo.Model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.datatype.XMLGregorianCalendar;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IndividualIdentityModel {

    String surname;
    String citizenship;
    String countryOfBirth;
    XMLGregorianCalendar dateOfBirth;
    String firstName;
    String fullName;
    String gender;
    String maritalStatus;
    String middleName;
    String presentSurname;
}
