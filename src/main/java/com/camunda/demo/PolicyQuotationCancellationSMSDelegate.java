package com.camunda.demo;

import com.camunda.demo.Service.SMSAlertService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PolicyQuotationCancellationSMSDelegate implements JavaDelegate {
 /*   @Autowired
    GatewayServiceFeign.ProductApplicationClient productApplicationClient;
*/
    @Autowired
    SMSAlertService smsAlertService;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {


        String mobileNumber="";
        try{ //
            mobileNumber=delegateExecution.getVariable("primaryPhoneNumber").toString();
        }catch (Exception ignored){

        }
        try{ //
            if(mobileNumber.equalsIgnoreCase(""))
                mobileNumber=delegateExecution.getVariable("mobileNumber1").toString();
        }catch (Exception ignored){
//HOME00178
        }
        String firstName=delegateExecution.getVariable("firstName").toString();
        String[] names=firstName.split(" ");

        String insuranceType="";
        String qoutationNumber="";

        try{
             insuranceType=delegateExecution.getVariable("insuranceType").toString();
             qoutationNumber=delegateExecution.getProcessBusinessKey();


        }catch (Exception e){
            e.printStackTrace();
        }

        smsAlertService.sendSMS(mobileNumber, "Dear "+names[0].toUpperCase().charAt(0)+ names[0].substring(1).toLowerCase()+" You insurance for "+insuranceType+" Insurance, ("+qoutationNumber+")  Quote has expired. You may apply for a fresh quotation if you desire");

   /*     ProductApplication productApplication=productApplicationClient.trackApplication(delegateExecution.getBusinessKey());
        productApplication.setApplicationStatus(CamundaApplication.APPLICATION_STATUS.COMPLETE);
        productApplication.setApplicationStatusDescription("Application in Progress");
        productApplication.setPercentageCompletion(100);
        try {
            productApplicationClient.updateProductApplication(productApplication);
        }catch (Exception e){

        }*/





    }
}





