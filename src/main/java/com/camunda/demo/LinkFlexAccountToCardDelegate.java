package com.camunda.demo;

import com.camunda.demo.CamundaApplication;
import com.camunda.demo.Model.*;
import com.camunda.demo.Service.GatewayServiceFeign;
import com.camunda.demo.Service.PostilionAccountFeign;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LinkFlexAccountToCardDelegate implements JavaDelegate {

    @Autowired
    GatewayServiceFeign.ProductApplicationClient productApplicationClient;

    @Autowired
    PostilionAccountFeign.AccountCreationClient linkCardCreationService;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        LinkFlexAccountResponse linkFlexAccountResponse = null;
        if(delegateExecution.getVariable("cardNumber")==null)
        delegateExecution.setVariable("cardNumber", "");
        if(delegateExecution.getVariable("cardAllocated")==null)
        delegateExecution.setVariable("cardAllocated", false);
        if(delegateExecution.getVariable("cardCreationMessage")==null)
        delegateExecution.setVariable("cardCreationMessage", "");
        if(delegateExecution.getVariable("cardCreationResponseCode")==null)
        delegateExecution.setVariable("cardCreationResponseCode", "");
        if(delegateExecution.getVariable("cardCreationRetrievalRef")==null)
        delegateExecution.setVariable("cardCreationRetrievalRef", "");
        if(delegateExecution.getVariable("productName")==null)
        delegateExecution.setVariable("productName", "");
        if(delegateExecution.getVariable("latestRiskScore")==null)
        delegateExecution.setVariable("latestRiskScore", 0);
        if(delegateExecution.getVariable("cardLinkingSuccessful")==null)
        delegateExecution.setVariable("cardLinkingSuccessful",false);



        LinkFlexAccountRequest instantAccountRequest;
        String middleName="";
        String firstName="";
        try {
            try {
                String[] names = delegateExecution.getVariable("registrarFirstName").toString().split(" ");
                if(names.length>1)
                middleName = names[1];
                firstName=names[0];
            }catch (Exception ignored){

            }
            String nameOnCard = delegateExecution.getVariable("registrarFirstName").toString() +" "+delegateExecution.getVariable("registrarSurname").toString();
            if (nameOnCard.length() > 23)
                nameOnCard = delegateExecution.getVariable("registrarFirstName").toString() + " " + delegateExecution.getVariable("registrarSurname").toString();
            if (nameOnCard.length() > 23 )
                nameOnCard = firstName.charAt(0) + " " + ((middleName.length()>0)?middleName.charAt(0):"") + " " + delegateExecution.getVariable("registrarSurname").toString();

            instantAccountRequest = LinkFlexAccountRequest.builder()
                .dob(delegateExecution.getVariable("dateOfBirthFlex").toString().replace("-", ""))
                .idNumber(delegateExecution.getVariable("personNo").toString())
                .flexAccount("032" + delegateExecution.getVariable("accountNumber").toString())
                .mobile(delegateExecution.getVariable("primaryPhoneNumber").toString())
                .nameOnCard(nameOnCard.replaceAll("  ", " ").toUpperCase())
                .firstName(delegateExecution.getVariable("registrarFirstName").toString())
                .lastName(delegateExecution.getVariable("registrarSurname").toString())
                .title(delegateExecution.getVariable("registrarGender").toString().equalsIgnoreCase("M") ? "Mr" : "Ms")
                .build();
            instantAccountRequest.setType("");

            String address = delegateExecution.getVariable("address").toString().toUpperCase();
            address = address.replace("@","").replace(".","").replace(",","");


            try {

                instantAccountRequest.setAddress(address);
            } catch (Exception e) {

                e.printStackTrace();
            }


            try{
                linkFlexAccountResponse= linkCardCreationService.linkFlexcubeAccount(instantAccountRequest);

                if(linkFlexAccountResponse.getResponseCode().equalsIgnoreCase("00"))
                {
                    if(linkFlexAccountResponse.getAccount().startsWith("6"))
                    delegateExecution.setVariable("cardLinkingSuccessful",true);

                }
            }catch (Exception e){

                e.printStackTrace();
            }


            ProductApplication productApplication = null;
            try {

                productApplication = productApplicationClient.trackApplication(delegateExecution.getProcessBusinessKey());
            } catch (Exception e) {
                try{
                    productApplication = productApplicationClient.trackApplication(delegateExecution.getProcessBusinessKey());

                }catch (Exception e2){
                    try{
                        productApplication = productApplicationClient.trackApplication(delegateExecution.getProcessBusinessKey());

                    }catch (Exception e3){
                        e3.printStackTrace();
                    }
                    e2.printStackTrace();
                }
                e.printStackTrace();
            }
            // productApplication.setApplicationStatus("Application 100% Complete");
            try {

                System.out.println("The Request before updating ----------------------> " + productApplication);
                if (productApplication != null) {
                    System.out.println("Started updating the application");

                    productApplication.setApplicationStatus(CamundaApplication.APPLICATION_STATUS.COMPLETE);
                    productApplication.setApplicationStatusDescription("Application Complete");
                    productApplication.setPercentageCompletion(100.00);
                    if (productApplication.getAccount() == null) {
                        Account account = new Account();
                        productApplication.setAccount(account);
                    }
                    productApplication.getAccount().setExpiryDate(linkFlexAccountResponse.getExpiryDate());
                    productApplication.getAccount().setAccountNumber(linkFlexAccountResponse.getAccount());
                    System.out.println("The Request After updating with Card Number----------------------> " + productApplication);

                    try {
                        if(linkFlexAccountResponse.getResponseCode().equalsIgnoreCase("00"))
                        productApplicationClient.updateProductApplication(productApplication);
                        // productApplicationClient.updateProductApplication(productApplication);
                    } catch (Exception e) {
                        try {
                            if(linkFlexAccountResponse.getResponseCode().equalsIgnoreCase("00"))
                            productApplicationClient.updateProductApplication(productApplication);
                        } catch (Exception e1) {
                            try {
                                if(linkFlexAccountResponse.getResponseCode().equalsIgnoreCase("00"))
                                productApplicationClient.updateProductApplication(productApplication);
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                        }
                    }
                    delegateExecution.setVariable("productName", productApplication.getNameOfProductAppliedFor());
                    System.out.println("Ended updating the application");
                }

            } catch (Exception e) {

                e.printStackTrace();
            }
            //System.out.println(account);
            System.out.print(linkFlexAccountResponse);
            if (!linkFlexAccountResponse.getAccount().equalsIgnoreCase(""))
                try {
                    delegateExecution.setVariable("cardNumber", linkFlexAccountResponse.getAccount());
                    delegateExecution.setVariable("cardCreationMessage", linkFlexAccountResponse.getMessage());
                    delegateExecution.setVariable("cardCreationResponseCode", linkFlexAccountResponse.getResponseCode());
                    delegateExecution.setVariable("cardCreationRetrievalRef", linkFlexAccountResponse.getRetrievalRef());
                    delegateExecution.setVariable("cardAllocated", true);
                } catch (Exception e) {
                    if(!linkFlexAccountResponse.getAccount().startsWith("6"))
                        delegateExecution.setVariable("cardLinkingSuccessful",false);
                    e.printStackTrace();
                }
            if (delegateExecution.getVariable("cardNumber").toString().startsWith("6")) {
                delegateExecution.setVariable("cardAllocated", true);
            }
            // anomalies.add("<br>Congratulations an account has been created for the client, account number is "+createAccountResponse.getAccountNumber());
        } catch (Exception e) {

            e.printStackTrace();
            // anomalies.add("There were anomalies in trying to create an account for the client");
        }
    }
}
