package com.camunda.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {
    @javax.persistence.Id
    @GeneratedValue
    String Id;
    String accountNumber;
    String accountDescription;
    String accountClass;
    String accountClassDescription;
    String accountCurrency;
    String expiryDate;
}
