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
public class ContractModel implements ContractModelInterface {

    String contractStatus;
    AmountModel outstandingAmount;
    AmountModel pastAmount;
    AmountModel totalAmount;
    int pastDueDays;
    String phaseOfContract;
    XMLGregorianCalendar startDate;
    String roleOfClient;
    String sector;
    String typeOfContract;
    String contractSubRType;
    XMLGregorianCalendar dueDate;
    XMLGregorianCalendar lastUpdatedDate;

}
