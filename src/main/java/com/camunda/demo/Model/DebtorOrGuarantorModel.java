package com.camunda.demo.Model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
public class DebtorOrGuarantorModel {

    Integer closedContracts;
    AmountModel monthlyPayment;
    AmountModel pastMonthlyPayment;
    Integer openContracts;
    AmountModel totalAmount;
    AmountModel outstandingAmount;


}
