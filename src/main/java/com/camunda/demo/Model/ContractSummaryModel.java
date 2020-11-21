package com.camunda.demo.Model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractSummaryModel {
    List<AffordabilityHistoryModel> affordabilityHistoryModelList;
    AffordabilityModel affordabilityModel;
    DebtorOrGuarantorModel debtorModelList;
    XMLGregorianCalendar lastDelinquency90PlusDays;
    int maxInstallmentsClosedContracts;
    int maxDueInstallmentsOpenContracts;
    AmountModel worstPastDueAmount;
    int worstPastDueDays;
    List<PaymentCalendarModel> paymentCalendarModelList;

}
