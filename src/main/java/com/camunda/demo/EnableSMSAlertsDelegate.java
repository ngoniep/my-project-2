package com.camunda.demo;

import com.camunda.demo.Service.EnableSMSAlerts;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnableSMSAlertsDelegate implements JavaDelegate {

    @Autowired
    EnableSMSAlerts enableSMSAlerts;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
       String accountNumber = delegateExecution.getVariable("accountNumber").toString();
       String primaryPhoneNumber = delegateExecution.getVariable("primaryPhoneNumber").toString();

       enableSMSAlerts.enableSMSAlerts(accountNumber,primaryPhoneNumber);

    }
}
