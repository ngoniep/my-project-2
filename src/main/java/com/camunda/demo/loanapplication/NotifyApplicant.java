package com.camunda.demo.loanapplication;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class NotifyApplicant implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
      System.out.println(delegateExecution.getVariable("reason"));
    }
}
