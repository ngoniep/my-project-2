package com.camunda.demo.Model.DTOs;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateAccountResponse {

    String status;
    String customerNumber;
    String accountNumber;
    String accountDescription;
    String accountCurrency;
    String customerName;
    String firstName;
    String middleName;
    String lastName;


}
