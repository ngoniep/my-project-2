package com.camunda.demo;

import com.camunda.demo.Model.ProductApplication;
import com.camunda.demo.Service.GatewayServiceFeign;
import com.camunda.demo.Service.SMSAlertService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PolicyQuotationSMSDelegate implements JavaDelegate {
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

        }
        String firstName=delegateExecution.getVariable("firstName").toString();
        String[] names=firstName.split(" ");
        double premium =0.0;
        String insuranceType="";
        String qoutationNumber="";

        try{
            Double insuranceRate=Double.parseDouble(delegateExecution.getVariable("insuranceRate").toString());
            Double sumInsured=Double.parseDouble(delegateExecution.getVariable("sumInsured").toString());
            String term=delegateExecution.getVariable("term").toString();
             insuranceType=delegateExecution.getVariable("insuranceType").toString();
             qoutationNumber=delegateExecution.getProcessBusinessKey();

            premium=sumInsured*insuranceRate/100;

            switch (term) {
                case "B":
                    premium = (sumInsured * insuranceRate / 100) / 2;
                    break;
                case "T":
                    premium = (sumInsured * insuranceRate / 100) / 3;
                    break;
                case "Q":
                    premium = (sumInsured * insuranceRate / 100) / 4;
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        smsAlertService.sendSMS(mobileNumber, "Dear "+names[0].toUpperCase().charAt(0)+ names[0].substring(1).toLowerCase()+" You insurance for "+insuranceType+" Insurance, ("+qoutationNumber+")  premium Quote is " +premium+" kindly complete payment to activate policy");

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





