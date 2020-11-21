package com.camunda.demo.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OCRPerson {
    private String firstNames;
    private String lastName;
    private String dateOfBirth;
    private String idNumber;
    private String villageOfOrigin;
    private String placeOfBirth;
    private String dateOfIssue;
    private String gender;
    private String status;
}
