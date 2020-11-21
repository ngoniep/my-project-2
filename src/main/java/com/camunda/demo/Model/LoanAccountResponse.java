package com.camunda.demo.Model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanAccountResponse {

    String loanAccountNumber;
    Date maturityDate;
    String customerName;
    String productCategory;
    String productDescription;
}
