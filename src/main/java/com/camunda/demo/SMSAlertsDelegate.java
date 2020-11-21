package com.camunda.demo;

import com.camunda.demo.BusinessModels.SanctionScore;
import com.camunda.demo.CamundaApplication;
import com.camunda.demo.Model.ProductApplication;
import com.camunda.demo.Service.GatewayServiceFeign;
import com.camunda.demo.Service.SMSAlertService;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SMSAlertsDelegate implements JavaDelegate {
    @Autowired
    GatewayServiceFeign.ProductApplicationClient productApplicationClient;

    @Autowired
    SMSAlertService smsAlertService;
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {


        String mobileNumber="";
        try{ //
            mobileNumber=delegateExecution.getVariable("mobileNumber").toString();
        }catch (Exception e){

            e.printStackTrace();
        }
        try{ //
            if(mobileNumber.equalsIgnoreCase(""))
                mobileNumber=delegateExecution.getVariable("mobileNumber1").toString();
        }catch (Exception e){

            e.printStackTrace();
        }
        try{
            if(mobileNumber.equalsIgnoreCase(""))
                mobileNumber=delegateExecution.getVariable("mobile").toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        String accountNumber=delegateExecution.getVariable("accountNumber").toString();
        String firstName=delegateExecution.getVariable("firstName").toString();
        String cardNumber="";
        try {
            cardNumber = delegateExecution.getVariable("cardNumber").toString();
        }catch(Exception e){
            e.printStackTrace();
        }

        String[] names=firstName.split(" ");
        if(!cardNumber.startsWith("6")) {
            if(delegateExecution.getVariable("productName")!=null)
            {
                String msg="Welcome " +names[0].toUpperCase().charAt(0)+ names[0].substring(1).toLowerCase() + "! " +
                    "We are excited to have you, your " + delegateExecution.getVariable("productName").toString().replace("account","") + " account is " + accountNumber + " you can now start transacting.";

                if(msg.toUpperCase().contains("WALLET"))
                    msg.replace(" account ","");
                if(accountNumber!=null&&accountNumber.length()!=0)
                     smsAlertService.sendSMS(mobileNumber, msg);

            }
            else if(accountNumber!=null&&accountNumber.length()!=0)
                smsAlertService.sendSMS(mobileNumber, "Welcome " + names[0].toUpperCase().charAt(0)+ names[0].substring(1).toLowerCase() + "! " +
                    "We are excited to have you, your " + delegateExecution.getVariable("accountCurrency").toString().replace("account","") + " account is " + accountNumber + " you can now start transacting."

                );
        }
        else {
            if(accountNumber!=null&&accountNumber.length()!=0)
            smsAlertService.sendSMS(mobileNumber, "Welcome " + names[0].toUpperCase().charAt(0)+ names[0].substring(1).toLowerCase() + "! " +
                "We are excited to have you, your " + delegateExecution.getVariable("productName").toString().replace("account","") + " account is " + accountNumber + " you can now start transacting."
            );

            if(cardNumber!=null&&cardNumber.length()!=0)
            smsAlertService.sendSMS(mobileNumber, "Welcome " + names[0].toUpperCase().charAt(0)+ names[0].substring(1).toLowerCase() + "! " +
                "Your Card Number is " + cardNumber
            );
        }

        try {

            ProductApplication productApplication = productApplicationClient.trackApplication(delegateExecution.getBusinessKey());
            if ((!cardNumber.equalsIgnoreCase("")) && (!productApplication.getAccount().getAccountNumber().startsWith("6")))
                productApplication.getAccount().setAccountNumber(cardNumber);
            productApplication.setApplicationStatus(CamundaApplication.APPLICATION_STATUS.COMPLETE);
            productApplication.setApplicationStatusDescription("Account No. " + accountNumber);
            productApplication.setPercentageCompletion(100);
        }catch (Exception ignored){

        }
        try {
         //   productApplicationClient.updateProductApplication(productApplication);
        }catch (Exception e){

            e.printStackTrace();
        }



    }
}
