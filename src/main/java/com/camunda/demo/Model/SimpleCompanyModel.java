package com.camunda.demo.Model;


import lombok.Data;

@Data
public class SimpleCompanyModel extends CompanyModel {


    String registrationCountry;
    String establishmentLocation;
    String companyNameLocal;

}
