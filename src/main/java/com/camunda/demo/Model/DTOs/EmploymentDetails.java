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
public class EmploymentDetails {
 /*   private String nameOfEmployer;
    private String currentJobTitle;
    private String employmentType;
    private Date dateJoined;
    private int lengthOfEmployment;
    private String averageMonthlyIncome;
    private ContactDetails contactDetails;
    private String workPrimaryContactId ;
    private String workPrimaryFullName ;*/


    String jobTitle;
    String company;
    String companyLogo;
    String description;
    String shortDescription;
    String status;
    String startDate;
    String endYear;
    String employmentType;
    double averageMonthlyIncome;
    String typeOfOrganisation;
    String industryOfOrganisation;
    boolean employmentDetailsVerified;
    String payslipImageUrl;
    boolean isPrimary;
    ContactDetails contactDetails;


}
