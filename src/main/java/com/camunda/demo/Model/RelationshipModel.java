package com.camunda.demo.Model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RelationshipModel {
    ContactModel contactModel;
    String fullName;
    String idIssuerCountry;
    String idNumber;
    String idNumberType;
    AddressModel mainAddress;
    AddressModel secondaryAddress;
    String customerRole;
    String subjectType;
}
