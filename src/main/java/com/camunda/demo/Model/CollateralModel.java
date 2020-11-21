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
public class CollateralModel {
    String collateralCode;
    String collateralDescription;
    String collateralType;
    AmountModel collateralValue;
    XMLGregorianCalendar valuationDate;
}
