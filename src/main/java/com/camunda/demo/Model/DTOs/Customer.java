package com.camunda.demo.Model.DTOs;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {

    private String customerType;
    private String firstName;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String country;
    private String surname;
    private String nationality;
    private String branch;
    private String customerCategory;
    private String fullName;
    private String uidName;
    private String uidValue;
    private String media;
    private String location;
    private String middleName;
    private String gender;
    private String nationalId;
    private String language;
    private String workAddress1;
    private String workAddress2;
    private String workAddress3;
    private String currentDesignation;
    private String currentEmployer;
    private int salary;
    private String workEmail;
    private String employerDescription;
    private String employmentStatus;
    private int employmentTenure;
    private String shortName;
    private String minor;
    private String dateOfBirth;



}
