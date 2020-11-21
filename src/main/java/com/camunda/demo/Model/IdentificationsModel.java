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
public class IdentificationsModel implements IdentificationModelInterface {
    String rgistrationNumberIssuerCountry;
    String businessLicenseNumber;
    String drivingLicenseIssuerAuthority;
    String driversLicenseNumber;
    String nationalId;
    XMLGregorianCalendar nationalIDExpiryDate;
    String nationalIDIssuingAuthority;
    XMLGregorianCalendar nationalIDIssueDate;
    XMLGregorianCalendar passportExpirationDate;
    String passportIssueAuthority;
    XMLGregorianCalendar passportIssueDate;
    String passportIssuerCountry;
    String passportNumber;
    String previousPassport;
    String idCardIssuingAuthority;
    String customIdNumber1;
    String customIdNumber2;
    String businessLicenseExpirationDate;
    String idCardIssueLocation;
    String taxNumber;
    String idCardIssueDate;
    String registrationNumber;


}
