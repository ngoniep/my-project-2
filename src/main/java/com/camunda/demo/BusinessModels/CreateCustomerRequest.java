package com.camunda.demo.BusinessModels;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateCustomerRequest {

    String customerType;
    String name;
    String workAddress1;
    String workEmail;
    String surname;
    String middleName;
    @Builder.Default
    String addressLine1="Not Provided";
    String addressLine2;
    String addressLine3;
    @Builder.Default
    String country="ZWD";
    String shortName;
    @Builder.Default
    String nationality="ZWD";
    @Builder.Default
    String uidName="NATIONAL_ID";
    @Builder.Default
    String loc="HRE";
    @Builder.Default
    String trackLimits="Y";
    String creditRating="2";
    String po;
    String street;
    String dateOfBirth;
    String gender;
    @Builder.Default
    private String nationalId="ZWD";
    @Builder.Default
    String lang="ENG";
    @Builder.Default
    String individualType="Individual";
    @Builder.Default
    String sourceOfIncome="SALARY";
    @Builder.Default
    String citizenship="ZWD";
    String idNumber;
    @Builder.Default
    String media="MAIL";
    @Builder.Default
    String location="HRE";
    @Builder.Default
    String maritalStatus="S";
    @Builder.Default
    String employmentStatus="U";
    @Builder.Default
    String branch="032";
    @Builder.Default
    String currentDesignation="Not Applicable";
    @Builder.Default
    String employerDescription="Not Applicable";
    @Builder.Default
    String category="INDIVIDUAL";
    @Builder.Default
    Double monthlySalary=0.00;
    String mobileNumber;
    @Builder.Default
    String birthCountry="ZWD";
    String placeOfBirth;
    String districtOfBirth;
    String emailAddress;



}
