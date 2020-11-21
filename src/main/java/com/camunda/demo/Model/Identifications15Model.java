package com.camunda.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.datatype.XMLGregorianCalendar;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Identifications15Model implements IdentificationModelInterface {
    protected XMLGregorianCalendar businessLicenseExpirationDate;
    protected String businessLicenseNumber;
    protected Long creditinfoId;
    protected String customIdNumber1;
    protected String customIdNumber2;
    protected String drivingLicenseNumber;
    protected String foreignUniqueID;
    protected XMLGregorianCalendar idCardIssueDate;
    protected String idCardIssueLocation;
    protected String idCardIssuingAuthority;
    protected String idCardNumber;
    protected String nationalID;
    protected String passportIssuerCountry;
    protected String passportNumber;
    protected String previousPassport;
    protected String registrationNumber;
    protected String socialSecurityNumber;
    protected String taxNumber;
    protected String votersID;
}
