package com.camunda.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.datatype.XMLGregorianCalendar;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtherLiabilityModel {
    protected XMLGregorianCalendar conditionDate;
     protected String contractCode;
    protected String currencyOfContract;
     protected XMLGregorianCalendar defaultDate;
    protected String defaultReason;
    protected String defaultReasonDescription;
    protected XMLGregorianCalendar delinquencyDate;
     protected AmountModel initialTotalAmount;
    protected XMLGregorianCalendar issueDate;
    protected XMLGregorianCalendar lastUpdate;
    protected AmountModel marketValue;
    protected XMLGregorianCalendar maturityDate;
    protected String negativeStatus;
    protected AmountModel outstandingAmount;
    protected AmountModel principalArrears;
    protected String rating;
    protected XMLGregorianCalendar realEndDate;
    protected String status;
    protected String subscriber;
    protected String type;


}
