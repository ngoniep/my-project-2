package com.camunda.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.datatype.XMLGregorianCalendar;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentIncidentModel {

    String additionalInformation;
    String code;
    AmountModel dueAmount;
    XMLGregorianCalendar dueDate;
    XMLGregorianCalendar dueReportingDate;
    String negativeInformationType;
    AmountModel outstandingAmount;
    String paymentStatus;
    String referenceContractCode;
    String paymentType;
    XMLGregorianCalendar reportDate;
    String sector;
    XMLGregorianCalendar settledDate;
    String subscriber;
}
