package com.camunda.demo.Model.DTOs;

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
public class CustomerCreationResponse {
    private boolean exists;
    private String customerNumber;
    private String customerName;
    String firstName;
    String middleName;
    String lastName;
    String status;
    Customer customer;

}
