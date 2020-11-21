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
public class SecurityModel {
    protected XMLGregorianCalendar conditionDate;
    protected String contractCode;
    protected String currencyOfContract;
    protected XMLGregorianCalendar defaultDate;
    protected String defaultReason;
    protected String defaultReasonDescription;
    protected XMLGregorianCalendar delinquencyDate;
    protected AmountModel initialTotalAmount;
    protected BigDecimal interestRate;
    protected XMLGregorianCalendar issueDate;
    protected String issuerName;
    protected XMLGregorianCalendar lastUpdate;
    protected String marketListed;
    protected AmountModel marketValue;
    protected XMLGregorianCalendar maturityDate;
    protected String negativeStatus;
    protected AmountModel outstandingAmount;
    protected AmountModel principalArrears;
    protected String rating;
    protected XMLGregorianCalendar realEndDate;
    protected String status;
    protected String subscriber;
    protected AmountModel totalAmount;
    protected String type;
    protected Integer typeofInterestRate;
}
