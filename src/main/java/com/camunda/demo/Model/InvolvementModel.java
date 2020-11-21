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
public class InvolvementModel {
    XMLGregorianCalendar conditionDate;
    String contractCode;
    AmountModel totalAmount;
    String currencyOfContract;
    XMLGregorianCalendar defaultDate;
    String defaultReason;
    String defaultReasonDescription;
    String inclusionPurpose;
    AmountModel initialTotalAmount;
    XMLGregorianCalendar lastUpdatedDate;
    String negativeStatus;
    XMLGregorianCalendar realEndDate;
    XMLGregorianCalendar startDate;
    String status;
    String subscriber;

}
