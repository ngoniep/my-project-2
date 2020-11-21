package com.camunda.demo;

import com.camunda.demo.CamundaApplication;
import com.camunda.demo.Model.*;
import com.camunda.demo.Model.DTOs.*;
import com.camunda.demo.Service.*;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class InstantCardCreationDelegate implements JavaDelegate {

    @Autowired
    GatewayServiceFeign.ProductApplicationClient productApplicationClient;

    @Autowired
    PostilionAccountFeign.AccountCreationClient instantCardCreationService;

    @Autowired
    PersonFeignService.PersonClient personClient;



    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {


        if(delegateExecution.getVariable("cardNumber")==null)
        delegateExecution.setVariable("cardNumber", "");
        if(delegateExecution.getVariable("accountNumber")==null)
            delegateExecution.setVariable("accountNumber", "");
        if(delegateExecution.getVariable("cardAllocated")==null)
        delegateExecution.setVariable("cardAllocated",false);
        if(delegateExecution.getVariable("instantCardCreationMessage")==null)
        delegateExecution.setVariable("instantCardCreationMessage", "");
        if(delegateExecution.getVariable("instantCardCreationResponseCode")==null)
        delegateExecution.setVariable("instantCardCreationResponseCode", "");
        if(delegateExecution.getVariable("instantCardCreationRetrievalRef")==null)
        delegateExecution.setVariable("instantCardCreationRetrievalRef", "");
        delegateExecution.setVariable("isUSSD",false);
        delegateExecution.setVariable("isUSSD",false);
        System.out.println("now extracting profile of client");
        if(delegateExecution.getVariable("productName")==null)
        delegateExecution.setVariable("productName","");
        if(delegateExecution.getVariable("registrarGender")==null)
        delegateExecution.setVariable("registrarGender","");
        if(delegateExecution.getVariable("cardLinkingSuccessful")==null)
        delegateExecution.setVariable("cardLinkingSuccessful",false);
        PersonalDetailsDTO personalDetails = personClient.getPersonalDetailsByIdNumber(delegateExecution.getVariable("personNo").toString()).get();

        Date dateOfBirth=null;
        try{
            dateOfBirth=new SimpleDateFormat("dd/MM/yyyy").parse(personalDetails.getDateOfBirth());
        }catch (Exception exception){

        }
        try{
            delegateExecution.getVariable("dateOfBirthInstant").toString();
        }catch(Exception e){
            try {
                delegateExecution.setVariable("dateOfBirthInstant", new SimpleDateFormat("yyyyMMdd").format(dateOfBirth));
            }catch (Exception d){
                try{
                    delegateExecution.setVariable("dateOfBirthInstant", new SimpleDateFormat("yyyyMMdd").format(new SimpleDateFormat("yyyy-MM-dd").format(dateOfBirth)));
                }catch(Exception e2) {
                    delegateExecution.setVariable("dateOfBirthInstant", new SimpleDateFormat("yyyyMMdd").format(personClient.getPersonalDetailsByIdNumber(personalDetails.getIdNumber()).get().getDateOfBirth()));
                }
                }


        }

        try{
            delegateExecution.setVariable("registrarGender",personalDetails.getGender());
        }catch (Exception e){

            delegateExecution.setVariable("registrarGender","");
        }

        try{

            delegateExecution.setVariable("mobileNumber",personalDetails.getPrimaryPhoneNumber());
        }catch(Exception e){

        }
        delegateExecution.setVariable("latestRiskScore", 0);
        String mobile = "";
        System.out.println("now populating contact details");
        try {
            List<ContactDetails> contactDetails = personalDetails.getContactDetails();
            if (contactDetails != null) {
                for (ContactDetails contactDetails1 : contactDetails) {
                    if (contactDetails1.getContactType().equalsIgnoreCase("mobile")) {
                        mobile = contactDetails1.getContactValue();
                        break;
                    }
                }
            }
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
        }

        InstantAccountRequest instantAccountRequest;
        System.out.println("now extracting bulding request");
        String title="";
        try{
             title=delegateExecution.getVariable("registrarGender").toString().equalsIgnoreCase("M") ? "Mr" : "Ms";
        }catch (Exception e){

            e.printStackTrace();
        }
        String middleName="";

        try{
            middleName = (personalDetails.getMiddleName() == null) ? "" : personalDetails.getMiddleName();
        }catch(NullPointerException ignored){
        }
        try {

            String nameOnCard=personalDetails.getFirstName() + " " + middleName + " " + personalDetails.getLastName();
            if(nameOnCard.length()>26)
                nameOnCard=personalDetails.getFirstName() + " " + personalDetails.getLastName();
            try {
                if (nameOnCard.length() > 26)
                    nameOnCard = personalDetails.getFirstName().charAt(0) + " " + middleName.charAt(0) + " " + personalDetails.getLastName();
            }catch (Exception e){
                    nameOnCard = personalDetails.getFirstName().charAt(0) + " " + personalDetails.getLastName();
            }

            instantAccountRequest = InstantAccountRequest.builder()
                .dob(delegateExecution.getVariable("dateOfBirthInstant").toString())
                .idNumber(delegateExecution.getVariable("personNo").toString())
                .mobile(mobile)
                .flexAccount("")
                .nameOnCard(nameOnCard.replaceAll("  "," ").toUpperCase())
                .firstName(personalDetails.getFirstName() + " " + middleName)
                .lastName(personalDetails.getLastName())
                .title(title)
                .build();
            if(delegateExecution.getProcessDefinitionId().toUpperCase().contains("WALLET"))
                instantAccountRequest.setType("Bank_Digi_Wallet");
            else
                instantAccountRequest.setType("");
            String address = "";
            if (personalDetails.getAddresses() != null) {
                for (Address add : personalDetails.getAddresses()) {
                    address = add.getStreetName() + " " + add.getCity() + " " + add.getCountry();
                    address = address.replace("@","").replace(".","").replace(",","");
                }
            }

            instantAccountRequest.setAddress(address);
            delegateExecution.setVariable("address",address);

            System.out.println("now pushing the call");
            InstantAccountCreationResponse instantAccountCreationResponse = null;

            try{
                if(delegateExecution.getVariable("accountNumber")==null||!delegateExecution.getVariable("accountNumber").toString().startsWith("6"))
                        instantAccountCreationResponse=instantCardCreationService.creatPostlionAccount(instantAccountRequest);
                if((instantAccountCreationResponse!=null&&instantAccountCreationResponse.getResponseCode().equalsIgnoreCase("00"))||delegateExecution.getVariable("accountNumber").toString().startsWith("6"))
                {
                    //if(instantAccountCreationResponse.getAccount().startsWith("6"))
                    delegateExecution.setVariable("cardLinkingSuccessful",true);

                }
            }catch (Exception e){
                if(instantAccountCreationResponse==null||instantAccountCreationResponse.getAccount()==null||!instantAccountCreationResponse.getAccount().startsWith("6"))
                    delegateExecution.setVariable("cardLinkingSuccessful",false);
                e.printStackTrace();
            }



            System.out.println("Creating Card Successful");
            ProductApplication productApplication = productApplicationClient.trackApplication(delegateExecution.getProcessBusinessKey());
            System.out.println(productApplication);
            // productApplication.setApplicationStatus("Application 100% Complete");
            if(instantAccountCreationResponse.getAccount()!=null&&instantAccountCreationResponse.getAccount().length()>0) {
                productApplication.setApplicationStatus(CamundaApplication.APPLICATION_STATUS.COMPLETE);
                productApplication.setApplicationStatusDescription("Application Complete");
                productApplication.setPercentageCompletion(100.00);
                if(productApplication.getAccount()==null){
                    Account account=new Account();
                    productApplication.setAccount(account);
                }
                productApplication.getAccount().setAccountNumber(instantAccountCreationResponse.getAccount());
                productApplication.getAccount().setExpiryDate(instantAccountCreationResponse.getExpiryDate());
                try {
                    productApplicationClient.updateProductApplication(productApplication);
                }catch (Exception e){
                    try{
                        productApplicationClient.updateProductApplication(productApplication);
                    }catch (Exception e1){
                        try{
                            productApplicationClient.updateProductApplication(productApplication);
                        }catch (Exception e2){
                            e2.printStackTrace();
                        }
                    }
                }
                delegateExecution.setVariable("productName",productApplication.getNameOfProductAppliedFor());

            }
            assert instantAccountCreationResponse.getAccount() != null;
            if(!instantAccountCreationResponse.getAccount().equalsIgnoreCase(""))
            try {
                delegateExecution.setVariable("cardNumber", "");
                delegateExecution.setVariable("accountNumber", instantAccountCreationResponse.getAccount());
                delegateExecution.setVariable("instantCardCreationMessage", instantAccountCreationResponse.getMessage());
                delegateExecution.setVariable("instantCardCreationResponseCode", instantAccountCreationResponse.getResponseCode());
                delegateExecution.setVariable("instantCardCreationRetrievalRef", instantAccountCreationResponse.getRetrievalRef());
                delegateExecution.setVariable("cardAllocated",true);
            }catch (Exception e){
                e.printStackTrace();
            }
            if(delegateExecution.getVariable("cardNumber").toString().startsWith("6")){
                delegateExecution.setVariable("cardAllocated",true);
            }
            // anomalies.add("<br>Congratulations an account has been created for the client, account number is "+createAccountResponse.getAccountNumber());
        } catch (Exception e) {
            if(!delegateExecution.getVariable("accountNumber").toString().startsWith("6"))
                delegateExecution.setVariable("cardLinkingSuccessful",false);
            e.printStackTrace();
            // anomalies.add("There were anomalies in trying to create an account for the client");
        }
    }
}
