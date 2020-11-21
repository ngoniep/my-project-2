package com.camunda.demo.Service;


import com.camunda.demo.Model.LoanAccountRequest;
import com.camunda.demo.Model.LoanAccountResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class LoanApplicationService {

    @FeignClient(value = "jplaceholder", url = "http://localhost:9494/")
    public interface LoanApplicationClient {

        @RequestMapping(method = RequestMethod.POST, value = "/loan-application")
        LoanAccountResponse applyForLoan(@RequestBody LoanAccountRequest loanAccountRequest);


    }


}
