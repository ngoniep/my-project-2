package com.camunda.demo;

import com.camunda.demo.Model.DTOs.LivenessCheck;
import com.camunda.demo.Model.ProductApplication;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LivenessDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        delegateExecution.setVariable("isLivePerson",false);
         if(Double.parseDouble(delegateExecution.getVariable("selfieLivenessScore").toString())>0.89)
           delegateExecution.setVariable("isLivePerson",true);

    }
}
