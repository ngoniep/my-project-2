package com.camunda.demo.loanapplication;

//import com.sun.org.apache.xpath.internal.operations.Bool;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class EligibilityDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Boolean eligible=false;


           Double salary = Double.parseDouble(delegateExecution.getVariable("salary").toString());

           Double currentObligations = Double.parseDouble(delegateExecution.getVariable("currentObligation").toString());
           Double loanAmount = Double.parseDouble(delegateExecution.getVariable("loanAmount").toString());

        if(salary*0.4>(currentObligations+loanAmount)/12) {
            delegateExecution.setVariable("reason","All Criteria Met");
            eligible = true;
        }
        else{
            delegateExecution.setVariable("reason","The loan causes you to exceeds 40% of salary");
        }


        delegateExecution.setVariable("eligible",eligible);
    }
}
