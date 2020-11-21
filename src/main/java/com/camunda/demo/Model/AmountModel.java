package com.camunda.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AmountModel {
    String currency;
    BigDecimal amountLocalCurrency;
    BigDecimal amount;
}
