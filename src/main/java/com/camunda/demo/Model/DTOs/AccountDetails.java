package com.camunda.demo.Model.DTOs;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDetails {
    private String preferredAccountType;
    private String requiredAccountCurrency;
    private boolean linkMobileMoneyWallet;
    private boolean registerEmailAlerts;
    private boolean registerSMSAlerts;
    private boolean salaryAccount;
    private String phoneToLinkMobileMoney;
    private String phoneToRecieveSMSAlerts;
    private String emailToRecieveAlerts;


}
