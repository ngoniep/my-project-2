package com.camunda.demo;

import com.camunda.demo.BusinessModels.CreateAccountRequest;
import com.camunda.demo.CamundaApplication;
import com.camunda.demo.Model.DTOs.CreateAccountResponse;
import com.camunda.demo.Model.ProductApplication;
import com.camunda.demo.Service.FlexcubeFeignServices;
import com.camunda.demo.Service.GatewayServiceFeign;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountCreationDelegate implements JavaDelegate {

    @Autowired
    GatewayServiceFeign.ProductApplicationClient productApplicationClient;

    @Autowired
    FlexcubeFeignServices.accountCreationClient accountApplicationClient;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {



        delegateExecution.setVariable("latestRiskScore",0);
        delegateExecution.setVariable("accountCardEligible",false);
        delegateExecution.setVariable("accountCreationSucceed",false);
        delegateExecution.setVariable("cardNumber", "");
        delegateExecution.setVariable("cardAllocated",false);
        delegateExecution.setVariable("cardCreationMessage", "");
        delegateExecution.setVariable("cardCreationResponseCode", "");
        delegateExecution.setVariable("cardCreationRetrievalRef", "");
        CreateAccountResponse createAccountResponse=new CreateAccountResponse();
        try {
            CreateAccountRequest createAccountRequest=CreateAccountRequest.builder()
                .accountClass(delegateExecution.getVariable("accountClass").toString())
                .accountCurrency(delegateExecution.getVariable("accountCurrency").toString())
                .accountDescription(delegateExecution.getVariable("registrarFirstName").toString()+" "+delegateExecution.getVariable("registrarSurname").toString())
                .customerIDNumber(delegateExecution.getVariable("personNo").toString())
                .customerName(delegateExecution.getVariable("registrarFirstName").toString()+" "+delegateExecution.getVariable("registrarSurname").toString())
                .customerNumber(delegateExecution.getVariable("customerNumber").toString())
                .firstName(delegateExecution.getVariable("registrarFirstName").toString())
                .lastName(delegateExecution.getVariable("registrarSurname").toString())
                .build();
            if(delegateExecution.getVariable("accountCurrency").toString().equalsIgnoreCase("ZWL"))
                delegateExecution.setVariable("accountCardEligible",true);
            createAccountResponse=accountApplicationClient.createAccount(createAccountRequest);

            if(createAccountResponse.getAccountNumber().length()>10) {
                ProductApplication productApplication = productApplicationClient.trackApplication(delegateExecution.getProcessBusinessKey());
                productApplication.setApplicationStatus(CamundaApplication.APPLICATION_STATUS.IN_PROGRESS);
                productApplication.setApplicationStatusDescription("Application Complete");
                if(productApplication.getPercentageCompletion()<90.00)
                    productApplication.setPercentageCompletion(90.00);
               // productApplicationClient.updateProductApplication(productApplication);
                delegateExecution.setVariable("accountNumber", createAccountResponse.getAccountNumber());
                delegateExecution.setVariable("accountCreationSucceed",true);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
