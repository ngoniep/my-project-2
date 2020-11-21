package com.camunda.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShareHolderModel {
   protected String birthSurname;
    protected String companyName;
    protected XMLGregorianCalendar dateOfBirth;
    protected XMLGregorianCalendar establishmentDate;
    protected String firstName;
    protected String fullName;
    protected Identifications15Model identifications;
    protected Integer numberOfShares;
    protected String relationType;
    protected XMLGregorianCalendar sharesAcquisitionDate;
    protected AmountModel sharesAmount;
    protected BigDecimal sharesPercentage;
    protected XMLGregorianCalendar subjectDate;
    protected String subjectName;
    protected String subjectType;
    protected String surname;
}
