package com.camunda.demo;

import com.camunda.demo.BusinessModels.CreateAccountRequest;
import com.camunda.demo.Model.DTOs.CreateAccountResponse;
import com.camunda.demo.Model.LoanAccountRequest;
import com.camunda.demo.Model.LoanAccountResponse;
import com.camunda.demo.Model.ProductApplication;
import com.camunda.demo.Service.LoanApplicationService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoanApplicationDelegate implements JavaDelegate {

    @Autowired
    LoanApplicationService.LoanApplicationClient loanApplicationClient;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

      String accountNumber=delegateExecution.getVariable("accountToCredit").toString();
      String cif=accountNumber.substring(2,9);
        try {
            LoanAccountRequest loanAccountRequest=LoanAccountRequest.builder()
                .branchCode("020")
                .currency("ZWL")
                .customerCif(cif)
                .loanAmount(Double.parseDouble(delegateExecution.getVariable("loanAmount").toString()))
                .loanProduct("ADSH")
                .build();
            LoanAccountResponse loanAccountResponse=loanApplicationClient.applyForLoan(loanAccountRequest);
            System.out.println(loanAccountResponse);

            delegateExecution.setVariable("loanAccountNumber",loanAccountResponse.getLoanAccountNumber());
            delegateExecution.setVariable("loanMaturityDate",loanAccountResponse.getMaturityDate());
            delegateExecution.setVariable("productCategory",loanAccountResponse.getProductCategory());
            delegateExecution.setVariable("productDescription",loanAccountResponse.getProductDescription());


        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
