package com.camunda.demo.Model;


import lombok.*;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CompanyModel {
    String businessStatus;
    String companyName;
    String economicSector;
    XMLGregorianCalendar establishmentDate;
    String legalForm;
    Integer numberOfEmployees;
    String tradeName;
    ContactModel contact;
    List<IdentificationsModel> identificationsModels;
    AddressModel mainAddress;
    AddressModel secondaryAddress;


}
