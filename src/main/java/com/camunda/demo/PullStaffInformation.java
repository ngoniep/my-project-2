package com.camunda.demo;


import com.camunda.demo.Model.DTOs.PersonalDetailsDTO;
import com.camunda.demo.Service.PersonFeignService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class PullStaffInformation implements JavaDelegate {
    @Autowired
    PersonFeignService.PersonClient personServiceFeign;



    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        PersonalDetailsDTO personalDetails=personServiceFeign.getPersonalDetailsByIdNumber(delegateExecution.getVariable("personNo").toString()).get();

        delegateExecution.setVariable("loanAccountNumber","");

        delegateExecution.setVariable("employeeName",personalDetails.getFirstName()+" "+((personalDetails.getMiddleName()==null)?"":personalDetails.getMiddleName()+" ")+personalDetails.getLastName());
        delegateExecution.setVariable("loanMaturityDate","");
        delegateExecution.setVariable("productCategory","");
        delegateExecution.setVariable("productDescription","");
        delegateExecution.setVariable("salary",10000);
        delegateExecution.setVariable("currentObligation",5000);

    }
}
