package com.camunda.demo.Model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanAccountRequest {

    String branchCode;
    String loanProduct;
    String customerCif;
    @Builder.Default
    String currency="ZWL";
    Double loanAmount;

}
