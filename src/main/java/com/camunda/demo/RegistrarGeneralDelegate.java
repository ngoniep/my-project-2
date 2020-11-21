package com.camunda.demo;

import com.camunda.demo.BusinessModels.PersonDTO;
import com.camunda.demo.Model.DTOs.PersonalDetailsDTO;
import com.camunda.demo.Model.ProductApplication;
import com.camunda.demo.Service.*;
import com.google.gson.Gson;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.regex.Pattern;


@Component
public class RegistrarGeneralDelegate implements JavaDelegate {
    @Autowired
    GatewayServiceFeign.ProductApplicationClient productApplicationClient;

    @Autowired
    PersonFeignService.PersonClient personDetailsClient;

    @Autowired
    FileCreationService fileCreationService;


    @Autowired
    PersonFeignService.PersonClient identityUpdate;


//    @Value("${configurations.getGatewayUrl:http://10.170.3.46:8086/handler/api/}")
//    String getGatewayUrl;
    @Value("${configurations.oauthUser:andrew1}")
    String oauthUser;
    @Value("${configurations.outhPassword:password}")
    String outhPassword;
//    @Value("${configurations.oauthUrl:http://10.170.4.67:9193/auth/api/oauth/token}")
//    String oauthUrl;



    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        delegateExecution.setVariable("registrarFirstName","");
        delegateExecution.setVariable("registrarSurname", "");
        delegateExecution.setVariable("registrarGender", "");
        delegateExecution.setVariable("firstNameValid", false);
        delegateExecution.setVariable("genderValid", false);
        delegateExecution.setVariable("surnameValid", false);
        delegateExecution.setVariable("dateOfBirthValid", false);
        delegateExecution.setVariable("registrarDateOfBirth", "");
        delegateExecution.setVariable("registrarStatus", "");
        PersonalDetailsDTO personalDetails;
        try {


             personalDetails = personDetailsClient.getPersonalDetailsByIdNumber(delegateExecution.getVariable("personNo").toString()).get();
        }catch (Exception e){
            personalDetails = personDetailsClient.getPersonalDetailsByIdNumber(delegateExecution.getVariable("primaryPhoneNumber").toString()).get();


            e.printStackTrace();
        }

        delegateExecution.setVariable("mobileNumber",personalDetails.getPrimaryPhoneNumber());
        delegateExecution.setVariable("mobileNumber1",personalDetails.getPrimaryPhoneNumber());

        System.out.println("The mobile number set is "+personalDetails.getPrimaryPhoneNumber());



        if(personalDetails.getSelfieBase64String()!=null)
        delegateExecution.setVariable("isUSSD", personalDetails.getSelfieBase64String().length() <= 0);

        ProductApplication productApplication=productApplicationClient.trackApplication(delegateExecution.getBusinessKey());
        productApplication.setApplicationStatus(CamundaApplication.APPLICATION_STATUS.IN_PROGRESS);
        productApplication.setApplicationStatusDescription("Application in Progress");
        if(productApplication.getPercentageCompletion()<(200.00/13.00))
            productApplication.setPercentageCompletion(200.00/13.00);
        try {
            productApplicationClient.updateProductApplication(productApplication);
        }catch (Exception e){

        }
        try {
            productApplicationClient.updateProductApplication(productApplication);
        }catch (Exception e){

        }
        System.out.println("Starting registrar general delegate");
        String personNo=delegateExecution.getVariable("personNo").toString();
        String gender=personalDetails.getGender();
        String dateOfBirth=personalDetails.getDateOfBirth();
        delegateExecution.setVariable("firstName", personalDetails.getFirstName());
        delegateExecution.setVariable("surname", personalDetails.getLastName());
        delegateExecution.setVariable("gender", gender);
        delegateExecution.setVariable("firstNameValid", false);
        delegateExecution.setVariable("genderValid", true);
        delegateExecution.setVariable("surnameValid", false);
        delegateExecution.setVariable("dateOfBirthValid", true);
        delegateExecution.setVariable("dateOfBirth", dateOfBirth);
        delegateExecution.setVariable("rgStatusValid", false);
        delegateExecution.setVariable("registrarGeneralDetailsValid", false);
        delegateExecution.setVariable("dateOfBirthInstant","");
        delegateExecution.setVariable("placeOfBirth","Place of Birth..");
        delegateExecution.setVariable("address","");
        String regExpr="[0-9]{7,9}[a-zA-Z][0-9]{2}";
        Pattern pattern=Pattern.compile(regExpr);
        boolean personNoValidId=false;
        if(personNo.replaceAll(" ","").replace("-","").matches(regExpr))
            personNoValidId=true;

            try {
            if(!personNoValidId)
                personNo=personalDetails.getCustomerIdNumber();
            HttpResponse<String> response = Unirest.get("http://10.170.3.40:8080/registrar-service/person/" + personNo)
                //.header("Authorization", "Bearer "+new MyConfigurations(oauthUrl,"bW9iaWxlOnBpbg==",oauthUser,outhPassword).getToken())
                    .asString();

            String response1=response.getBody();

            Gson gson = new Gson();
            PersonDTO personDTO = gson.fromJson(response1, PersonDTO.class);
            delegateExecution.setVariable("registrarFirstName", personDTO.getFirstName());
            delegateExecution.setVariable("isUSSD",true);
            delegateExecution.setVariable("registrarSurname", personDTO.getSurname());
            delegateExecution.setVariable("registrarGender", personDTO.getSex());//registrarGender
            delegateExecution.setVariable("registrarDateOfBirth", personDTO.getDateOfBirth());
            delegateExecution.setVariable("registrarStatus", personDTO.getStatus());
            if(personDTO.getBirthPlace()!=null)
             delegateExecution.setVariable("placeOfBirth",personDTO.getBirthPlace());
            delegateExecution.setVariable("dateOfBirthInstant",new SimpleDateFormat("yyyyMMdd").format(personDTO.getDateOfBirth()));
            delegateExecution.setVariable("dateOfBirthFlex",new SimpleDateFormat("yyyy-MM-dd").format(personDTO.getDateOfBirth()));
                delegateExecution.setVariable("dateOfBirthFcb",new SimpleDateFormat("dd-MM-yyyy").format(personDTO.getDateOfBirth()));

            if (personalDetails.getFirstName().equalsIgnoreCase(personDTO.getFirstName())) {
                delegateExecution.setVariable("firstNameValid", true);
            }
            try {
                if (gender.equalsIgnoreCase(personDTO.getSex())) {
                    delegateExecution.setVariable("genderValid", true);
                }
            }catch (Exception e){
                e.printStackTrace();

            }
            if (personalDetails.getLastName().toUpperCase().replace(" ","").equalsIgnoreCase(personDTO.getSurname().toUpperCase().replace(" ",""))) {
                delegateExecution.setVariable("surnameValid", true);
            }
            try{
            if (dateOfBirth.equalsIgnoreCase(new SimpleDateFormat("yyyy-MM-dd").format(personDTO.getDateOfBirth()))) ;
            {
                delegateExecution.setVariable("dateOfBirthValid", true);
            }
            }catch (Exception e1){
                e1.printStackTrace();

            }
            if (personDTO.getStatus().equalsIgnoreCase("A")) {
                delegateExecution.setVariable("rgStatusValid", true);

            }
            String name="";
            String secondName="";
            try {
                String[] names=personDTO.getFirstName().split(" ");
                if(names.length > 0){
                    name=names[0];
                }
                if(names.length > 1){
                    secondName=names[1];
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            System.out.println("first name "+personalDetails.getFirstName()+" name "+name);

            try {

                System.out.println("Checking First names ->"+personalDetails.getFirstName().toUpperCase().contains(name.toUpperCase()));
                System.out.println("Checking surnames -> "+personalDetails.getLastName().toUpperCase().equalsIgnoreCase(personDTO.getSurname().toUpperCase()));
                System.out.println("Checking Status -> "+personDTO.getStatus().equalsIgnoreCase("A"));

                if (
                    personalDetails.getFirstName().toUpperCase().contains(name.toUpperCase())
                        && personalDetails.getLastName().replaceAll(" ","").toUpperCase().equalsIgnoreCase(personDTO.getSurname().toUpperCase())
                            && personDTO.getStatus().equalsIgnoreCase("A")
                ) {
                    System.out.println("All the values provide by client similar to those at RG");
                    delegateExecution.setVariable("registrarGeneralDetailsValid", true);

                   try{
                       System.out.println("Update started");
                       PersonalDetailsDTO personalDetailsDTO= personDetailsClient.updateIdentity(personDTO);
                       System.out.println(personalDetailsDTO.getAddresses());
                       System.out.println("Update Completed");
                   }catch (Exception e){
                       e.printStackTrace();
                   }
                   // System.out.println(personalDetailsDTO);

                    try {
                        System.out.println("Update started using method 2");
                        identityUpdate.updateIdentity(personDTO);
                        System.out.println("Update Completed using method 2");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }



            else{
              delegateExecution.setVariable("registrarGeneralDetailsValid",false);
            }

            }catch (Exception e){
                e.printStackTrace();

            }

        }catch (Exception e){
            e.printStackTrace();
        }

       // System.out.println("Ended registrar general delegate");
        delegateExecution.setVariable("documentValid",false);

    }
}
