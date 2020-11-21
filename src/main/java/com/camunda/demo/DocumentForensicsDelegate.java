package com.camunda.demo;

import com.camunda.demo.Model.ProductApplication;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DocumentForensicsDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {


        System.out.println("Starting document forensics delegate");
          delegateExecution.setVariable("documentValid",true);
        System.out.println("Ending document forensics delegate");
    }
}
