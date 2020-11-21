package com.camunda.demo.BusinessModels;

import lombok.*;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PersonDTO {
    String personNo;
    String sex;
    String status;
    String firstName;
    String surname;
    Date dateOfBirth;
    Date dateOfDeath;
    String birthPlace;
    String source;
    Date lastRefreshedTime;

}
