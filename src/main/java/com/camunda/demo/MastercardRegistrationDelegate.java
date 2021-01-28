package com.camunda.demo;

import com.camunda.demo.Model.MastercardCustomer;
import com.camunda.demo.Model.MastercardRegistrationResponseDTO;
import com.camunda.demo.Service.MasterCardRegistrationService;
import com.camunda.demo.Service.PersonFeignService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * This is an easy adapter implementation
 * illustrating how a Java Delegate can be used
 * from within a BPMN 2.0 Service Task.
 */
@Component
public class MastercardRegistrationDelegate implements JavaDelegate {

  @Autowired
  MasterCardRegistrationService.MastercardRegistrationClient masterCardRegistrationService;

    @Autowired
    PersonFeignService.PersonClient personClient;

  public void execute(DelegateExecution execution) throws Exception {



      String dob="";
      String firstName="";
      String phoneNumber="";
      String idNumber="";
      String lastName="";
      String email="";
      String gender="";
      String initial="";

      try{
          dob=execution.getVariable("dateOfBirthFlex").toString();
      }catch (Exception e){
          e.printStackTrace();
      }
      try{
          firstName=execution.getVariable("firstName").toString();
      }catch (Exception e){
          e.printStackTrace();
      }
      try{
          phoneNumber=execution.getVariable("primaryPhoneNumber").toString();
      }catch (Exception e){
          e.printStackTrace();
      }
      try{
          idNumber=execution.getVariable("identityNumber").toString();
      }catch (Exception e){
          e.printStackTrace();
      }
      try{
          lastName=execution.getVariable("lastName").toString();
      }catch (Exception e){
          e.printStackTrace();
      }
      try{
          email=execution.getVariable("emailAddress").toString();
      }catch (Exception e){
          e.printStackTrace();
      }
      try{
          gender=(execution.getVariable("gender").toString());
      }catch (Exception e){
          e.printStackTrace();
      }
      try {
          initial =firstName.charAt(0)+"";
      }catch (Exception e){
          e.printStackTrace();
      }
      MastercardRegistrationResponseDTO mastercardRegistrationResponseDTO= MastercardRegistrationResponseDTO.builder().build();
      try {
          MastercardCustomer mastercardCustomer= MastercardCustomer.builder()
              .add1("address1")
              .add2("address2")
              .add3("address3")
              .idType("P")
              .postal("address1")
              .dob(dob)
              .fname(firstName)
              .mob("+" + phoneNumber)
              .tel("+" + phoneNumber)
              .nationality("Zimbabwean")
              .idNumber(idNumber)
              .lname(lastName)
              .email(email)
              .motherMName(firstName)
              .title(gender.equalsIgnoreCase("M") ? "Mr" : "Ms")
              .initial(initial)
              .areacode("263")
              .gender(gender)
              .branch("032")
              .country("ZW")
              .ban("")
              .idExpiry("")
              .add4("")
              .build();
          mastercardRegistrationResponseDTO = masterCardRegistrationService.register(mastercardCustomer);
          System.out.println(mastercardCustomer);
          System.out.println("Here is the response -> "+mastercardRegistrationResponseDTO);
      }catch (Exception e){
          e.printStackTrace();
      }

      System.out.println("Now setting variables");
     execution.setVariable("responseType",mastercardRegistrationResponseDTO.getResponseType());
     execution.setVariable("response",mastercardRegistrationResponseDTO.getResponseValue());
     try {
         execution.setVariable("regSuccess", mastercardRegistrationResponseDTO.getResponseType().equals("SUCCESS"));
     }catch (Exception e){
         e.printStackTrace();
     }
      System.out.println("done setting variables");

  }


}
