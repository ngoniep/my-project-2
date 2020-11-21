package com.camunda.demo.Model.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonalDetails {
    private String idNumber;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String gender;
    private String maritalStatus;
    @ToString.Exclude
    private String idImageBase64String;
    @ToString.Exclude
    private String selfieBase64String;

}
