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
public class CalendarItemModel {
    String classification;
    String delinquencyStatus;
    Integer contractsSubmitted;
    AmountModel monthlyPayment;
    AmountModel outstandingAmount;
    Integer pastDueDays;
    Integer pastDueInstallments;
    XMLGregorianCalendar date;
    AmountModel pastDueAmount;

}
