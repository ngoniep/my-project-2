package com.camunda.demo.Model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {
    @javax.persistence.Id
    @GeneratedValue
    String Id;
    double loanAmount;
    String currency;
    String loanType;
    String loanAccountNumber;
    String repaymentPeriod;
    String purposeOfFunds;
}
