package com.camunda.demo.Model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.datatype.XMLGregorianCalendar;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IndividualModel {
    IndividualIdentityModel identityInformation;
    EmploymentInformation employmentInformation;
    String businessName;
    String classificationOfIndividual;
    XMLGregorianCalendar establishmentDate;
    String negativeStatusOfIndividual;
    int numberOfDependants;
    String residency;
    AddressModel primaryAddress;
    AddressModel secondaryAddress;
    ContactModel contactModel;
    IdentificationsModel identificationsModel;

}
