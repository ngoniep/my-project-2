package com.camunda.demo.Model.DataModels;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "account_details")
public class AccountDetails {
    @Id
    private Long id;
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
