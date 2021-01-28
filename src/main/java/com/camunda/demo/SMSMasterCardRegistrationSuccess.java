package com.camunda.demo;

import com.camunda.demo.Model.ProductApplication;
import com.camunda.demo.Service.GatewayServiceFeign;
import com.camunda.demo.Service.SMSAlertService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SMSMasterCardRegistrationSuccess implements JavaDelegate {
    @Autowired
    GatewayServiceFeign.ProductApplicationClient productApplicationClient;

    @Autowired
    SMSAlertService smsAlertService;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {


        String mobileNumber="";
        try{ //
            mobileNumber=delegateExecution.getVariable("primaryPhoneNumber").toString();
        }catch (Exception e){

        }
        try{ //
            if(mobileNumber.equalsIgnoreCase(""))
                mobileNumber=delegateExecution.getVariable("mobileNumber1").toString();
        }catch (Exception e){

        }
        String firstName=delegateExecution.getVariable("firstName").toString();
        String[] names=firstName.split(" ");

        smsAlertService.sendSMS(mobileNumber, "Dear "+names[0].toUpperCase().charAt(0)+ names[0].substring(1).toLowerCase()+" Your Mastercard registration was successful. ");

        ProductApplication productApplication=productApplicationClient.trackApplication(delegateExecution.getBusinessKey());
        productApplication.setApplicationStatus(CamundaApplication.APPLICATION_STATUS.COMPLETE);
        productApplication.setApplicationStatusDescription("Application in Progress");
        productApplication.setPercentageCompletion(100);
        try {
            productApplicationClient.updateProductApplication(productApplication);
        }catch (Exception e){

        }





    }
}





