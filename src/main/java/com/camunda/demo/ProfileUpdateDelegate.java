package com.camunda.demo;

import com.camunda.demo.BusinessModels.PersonDTO;
import com.camunda.demo.Service.PersonFeignService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;


@Component
public class ProfileUpdateDelegate implements JavaDelegate {
    @Autowired
    PersonFeignService.PersonClient identityUpdateClient;



    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setPersonNo(delegateExecution.getVariable("personNo").toString());
        try {
            personDTO.setBirthPlace(delegateExecution.getVariable("placeOfBirth").toString());
        }catch(Exception e){

            e.printStackTrace();
        }
        try {
            personDTO.setFirstName(delegateExecution.getVariable("registrarFirstName").toString());
        }catch(Exception e){

            e.printStackTrace();
        }
        try {
            personDTO.setDateOfBirth(new SimpleDateFormat("dd-MM-yyyy").parse(delegateExecution.getVariable("dateOfBirthFcb").toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            personDTO.setSurname(delegateExecution.getVariable("registrarSurname").toString());
        }catch (Exception e){

            e.printStackTrace();
        }
        try {
            personDTO.setSex(delegateExecution.getVariable("registrarGender").toString());
        }catch(Exception e){

            e.printStackTrace();
        }
        try {
            personDTO.setStatus(delegateExecution.getVariable("registrarStatus").toString());
        }catch(Exception e){

            e.printStackTrace();
        }

        try {
            identityUpdateClient.updateIdentity(personDTO);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
