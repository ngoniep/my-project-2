package com.camunda.demo.BusinessModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateAccountRequest {
    String status;
    String customerNumber;
    String accountDescription;
    String accountCurrency;
    String customerName;
    String firstName;
    String middleName;
    String lastName;
    String customerIDNumber;
    String branch;
    String accountClass;
}
