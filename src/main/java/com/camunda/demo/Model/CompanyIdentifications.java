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
public class CompanyIdentifications  implements IdentificationModelInterface {
     String taxNumber;
     String rgistrationNumberIssuerCountry;
     String registrationNumber;
     XMLGregorianCalendar businessLicenseExpirationDate;

}
