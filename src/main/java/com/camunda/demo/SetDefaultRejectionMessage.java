package com.camunda.demo;

import com.camunda.demo.Model.ProductApplication;
import com.camunda.demo.Service.GatewayServiceFeign;
import com.camunda.demo.Service.SMSAlertService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SetDefaultRejectionMessage implements JavaDelegate {
    @Autowired
    GatewayServiceFeign.ProductApplicationClient productApplicationClient;

    @Autowired
    SMSAlertService smsAlertService;
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {


        String firstName = delegateExecution.getVariable("firstName").toString();
        String[] names = firstName.split(" ");


        delegateExecution.setVariable("custom_message", "Unfortunately " + names[0].toUpperCase().charAt(0) + names[0].substring(1).toLowerCase() + " we were not able to create an account for you.");


    }
}





