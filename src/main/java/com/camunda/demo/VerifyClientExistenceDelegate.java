package com.camunda.demo;

import com.camunda.demo.Service.VerifyClientExistense;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class VerifyClientExistenceDelegate implements JavaDelegate {

    @Autowired
    VerifyClientExistense verifyClientExistense;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
       String accountType="";
       delegateExecution.setVariable("customerNumber","");
       try {
           if (delegateExecution.getVariable("preferredAccountType").toString().equalsIgnoreCase("72")) {
               accountType = "SAVINGS";
           }
           else if (delegateExecution.getVariable("preferredAccountType").toString().equalsIgnoreCase("30")) {
               accountType = "CURRENT";
           }
       }catch (Exception e){
           e.printStackTrace();
       }

        delegateExecution.setVariable("customerExists",false);
        delegateExecution.setVariable("existingCustomerAccount","");
        VerifyClientExistense verifyClientExistense=new VerifyClientExistense();
        Optional<String> existingAccount;
        String idNumber=delegateExecution.getVariable("personNo").toString();
        System.out.println("Business Process is -> "+delegateExecution.getProcessDefinitionId());

       Optional<String> existingCustomer=verifyClientExistense.existingCustomerNumber(delegateExecution.getVariable("personNo").toString(),delegateExecution.getVariable("primaryPhoneNumber").toString(),accountType,delegateExecution.getVariable("accountCurrency").toString());
        existingCustomer.ifPresent(s -> delegateExecution.setVariable("customerNumber", s));

        // String delegateExecution.getVariable("primaryPhoneNumber").toString()
        if(delegateExecution.getProcessDefinitionId().toUpperCase().contains("FLEXCUBE"))
            existingAccount=verifyClientExistense.flexcube(delegateExecution.getVariable("personNo").toString(),delegateExecution.getVariable("primaryPhoneNumber").toString(),accountType,delegateExecution.getVariable("accountCurrency").toString());
        else
            existingAccount=verifyClientExistense.verifyClientExists(idNumber,delegateExecution.getVariable("primaryPhoneNumber").toString(),delegateExecution.getProcessDefinitionId());
        if(existingAccount.isPresent()){
            delegateExecution.setVariable("customerExists",true);
            delegateExecution.setVariable("existingCustomerAccount",existingAccount.get());
        }
    }
}
