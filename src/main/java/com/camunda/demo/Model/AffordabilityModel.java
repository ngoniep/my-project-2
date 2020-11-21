package com.camunda.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AffordabilityModel extends AffordabilityHistoryModel {
    BigDecimal monthlyInstallmentToIncomeRation;
}
