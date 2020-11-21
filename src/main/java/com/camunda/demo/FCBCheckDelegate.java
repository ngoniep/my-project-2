package com.camunda.demo;

import com.camunda.demo.BusinessModels.FCBReport;
import com.camunda.demo.BusinessModels.FCBRequest;
import com.camunda.demo.BusinessModels.FCBResponse;
import com.camunda.demo.Model.DTOs.*;
import com.camunda.demo.Service.FCBService;
import com.camunda.demo.Service.PersonFeignService;
import com.google.gson.Gson;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class FCBCheckDelegate implements JavaDelegate {

    PersonFeignService.PersonClient personClient;

    @Value("${configurations.fcbUrl: https://www.fcbureau.co.zw/api/newIndividual}")
    String fcbUrl;

    @Value("${configurations.fcbEmail:fbcapitest@fcbureau.co.zw}")
    String fcbEmail;

    @Value("${configurations.fcbPassword:password}")
    String fcbPassword;

    @Value("${configurations.sanctionsScreening: http://10.170.3.40:9781/kyc-screening-service/screen}")
    String sanctionsUrl;

    @Value("${configurations.fcbSalaryBand1:150}")
    String fcbSalaryBand1;
    @Value("${configurations.fcbSalaryBand2:250}")
    String fcbSalaryBand2;
    @Value("${configurations.fcbSalaryBand3:500}")
    String fcbSalaryBand3;
    @Value("${configurations.fcbSalaryBand4:1000}")
    String fcbSalaryBand4;
    @Value("${configurations.fcbSalaryBand5:2000}")
    String fcbSalaryBand5;
    @Value("${configurations.fcbSalaryBand6:5000}")
    String fcbSalaryBand6;

    @Override
    public void execute(DelegateExecution delegateExecution) {

        delegateExecution.setVariable("resultsPending", false);
        delegateExecution.setVariable("fcbId", "");
        delegateExecution.setVariable("errorCheckingFcb", false);
        delegateExecution.setVariable("fcbScoreValue",0);

        FCBService fcbService = new FCBService();
        fcbService.setFcbUrl(fcbUrl);
        fcbService.setSanctionsUrl(sanctionsUrl);
        delegateExecution.setVariable("fcbScore", 0);
        delegateExecution.setVariable("fcbStatus", 0);
        delegateExecution.setVariable("fcbScoreGood", false);
        delegateExecution.setVariable("fcbResponse", "");
        try {
            PersonalDetailsDTO personalDetailsDTO = personClient.getPersonalDetailsByIdNumber(delegateExecution.getVariable("personNo").toString()).get();
            String streetName = "Street Name";
            String surburb = "surburb";
            Integer streetNumber = 1;
            String employer = "self";
            String city = "harare";
            String pbag = "";
            String mobileNumber = "";
            String maritalStatus = "S";
            String email = "test@fbc.co.zw";
            Integer employerIndustry=13;
            Integer salaryBand=8;

            try {
                mobileNumber = personalDetailsDTO.getPrimaryPhoneNumber();
                List<ContactDetails> contactDetails = personalDetailsDTO.getContactDetails();
                List<Address> addressModels = personalDetailsDTO.getAddresses();
                List<EmploymentDetails> employmentDetails=personalDetailsDTO.getEmploymentDetails();
                try {

                    if (addressModels != null) {
                        for (Address address : addressModels) {
                            if (address.getAddressType().equalsIgnoreCase("HOME")) {
                                streetName = address.getStreetName();
                                city = address.getCity();
                                if (address.getStreetNumber() != null) {
                                    streetNumber = address.getStreetNumber();
                                }
                                pbag = streetNumber + " " + streetName + " " + city;
                            }
                        }
                    }

                } catch (Exception e) {

                }
                try{
                    for(EmploymentDetails employmentDetails1:employmentDetails){
                        employer=employmentDetails1.getCompany();
                        try {
                            if (employmentDetails1.getAverageMonthlyIncome() < Double.parseDouble(fcbSalaryBand1)) {
                                salaryBand = 1;
                            } else if (employmentDetails1.getAverageMonthlyIncome() < Double.parseDouble(fcbSalaryBand2)) {
                                salaryBand = 2;
                            } else if (employmentDetails1.getAverageMonthlyIncome() < Double.parseDouble(fcbSalaryBand3)) {
                                salaryBand = 3;
                            } else if (employmentDetails1.getAverageMonthlyIncome() < Double.parseDouble(fcbSalaryBand4)) {
                                salaryBand = 4;
                            } else if (employmentDetails1.getAverageMonthlyIncome() < Double.parseDouble(fcbSalaryBand5)) {
                                salaryBand = 5;
                            } else if (employmentDetails1.getAverageMonthlyIncome() < Double.parseDouble(fcbSalaryBand6)) {
                                salaryBand = 6;
                            } else if (employmentDetails1.getAverageMonthlyIncome() > Double.parseDouble(fcbSalaryBand6)) {
                                salaryBand = 7;
                            }
                        }catch (Exception e){

                        }try {
                            if (employmentDetails1.getIndustryOfOrganisation().toLowerCase().contains("agriculture")) {
                                employerIndustry = 1;
                            } else if (employmentDetails1.getIndustryOfOrganisation().toLowerCase().contains("manufacturing")) {
                                employerIndustry = 2;
                            } else if (employmentDetails1.getIndustryOfOrganisation().toLowerCase().contains("mining")) {
                                employerIndustry = 3;
                            } else if (employmentDetails1.getIndustryOfOrganisation().toLowerCase().contains("quarrying")) {
                                employerIndustry = 3;
                            } else if (employmentDetails1.getIndustryOfOrganisation().toLowerCase().contains("energy")) {
                                employerIndustry = 4;
                            } else if (employmentDetails1.getIndustryOfOrganisation().toLowerCase().contains("tourism")) {
                                employerIndustry = 6;
                            } else if (employmentDetails1.getIndustryOfOrganisation().toLowerCase().contains("transport")) {
                                employerIndustry = 7;
                            } else if (employmentDetails1.getIndustryOfOrganisation().toLowerCase().contains("trade")) {
                                employerIndustry = 5;
                            } else if (employmentDetails1.getIndustryOfOrganisation().toLowerCase().contains("estate")) {
                                employerIndustry = 8;
                            } else if (employmentDetails1.getIndustryOfOrganisation().toLowerCase().contains("finance")) {
                                employerIndustry = 9;
                            } else if (employmentDetails1.getIndustryOfOrganisation().toLowerCase().contains("gorvenment")) {
                                employerIndustry = 10;
                            } else if (employmentDetails1.getIndustryOfOrganisation().toLowerCase().contains("health")) {
                                employerIndustry = 14;
                            } else if (employmentDetails1.getIndustryOfOrganisation().toLowerCase().contains("security")) {
                                employerIndustry = 15;
                            } else if (employmentDetails1.getIndustryOfOrganisation().toLowerCase().contains("police")) {
                                employerIndustry = 16;
                            } else if (employmentDetails1.getIndustryOfOrganisation().toLowerCase().contains("army")) {
                                employerIndustry = 17;
                            } else if (employmentDetails1.getIndustryOfOrganisation().toLowerCase().contains("prisons")) {
                                employerIndustry = 18;
                            } else if (employmentDetails1.getIndustryOfOrganisation().toLowerCase().contains("ict")) {
                                employerIndustry = 19;
                            } else if (employmentDetails1.getIndustryOfOrganisation().toLowerCase().contains("communications")) {
                                employerIndustry = 19;
                            } else if (employmentDetails1.getIndustryOfOrganisation().toLowerCase().contains("retail")) {
                                employerIndustry = 20;
                            }
                        }catch(Exception e){

                        }

                    }
                }catch(Exception e){

                }

                try {
                    List<NextOfKin> nextOfKins = personalDetailsDTO.getNextOfKins();
                    if (nextOfKins != null)
                        for (NextOfKin nextOfKin : nextOfKins) {
                            if (nextOfKin.getRelationship().equalsIgnoreCase("Spouse") || nextOfKin.getRelationship().equalsIgnoreCase("wife") || nextOfKin.getRelationship().equalsIgnoreCase("husband")) {
                                maritalStatus = "M";
                            }

                        }
                } catch (Exception e) {

                }
                try {

                } catch (Exception e) {

                }
                try {
                    if (contactDetails != null)
                        for (ContactDetails contactDetails1 : contactDetails) {
                            if (contactDetails1.getContactType().equalsIgnoreCase("email")) {
                                email = contactDetails1.getContactValue();
                                break;
                            }
                        }
                } catch (Exception e) {

                }

            } catch (Exception e) {

            }
            //Changed to revert to 11 Digit ID Numbers which are recognised
            String idNumber=delegateExecution.getVariable("personNo").toString().replace(" ", "%20");
            if(idNumber.charAt(2)=='0'&&idNumber.length()==12){
                idNumber=idNumber.substring(0,2)+idNumber.substring(3);
            }
            FCBRequest fcbRequest = FCBRequest.builder()
                    .dob(delegateExecution.getVariable("dateOfBirthFcb").toString())
                    .surname(delegateExecution.getVariable("lastName").toString().replace(" ", "%20"))
                    .national_id(idNumber)
                    .gender(delegateExecution.getVariable("registrarGender").toString())
                    .search_purpose(3)
                    .email(fcbEmail)
                    .password(fcbPassword)
                    .drivers_licence("drv")
                    .passport("pp")
                    .married(maritalStatus)
                    .nationality(3)
                    .streetno(streetNumber)
                    .streetname(streetName)
                    .building("fcb mansions")
                    .suburb(surburb)
                    .pbag(pbag)
                    .city(city)
                    .telephone("794367-9")
                    .mobile(mobileNumber)
                    .ind_email(email)
                    .property_density(1)
                    .property_status(2)
                    .occupation_class(1)
                    .employer(employer)
                    .employer_industry(employerIndustry)
                    .salary_band(salaryBand)
                    .loan_purpose(3)
                    .loan_amount(10)
                    .names((delegateExecution.getVariable("firstName").toString()).replace(" ", "+"))
                    .build();
            FCBResponse fcbResponse = fcbService.getPerson(fcbRequest);
            System.out.println(fcbResponse);
            if(fcbResponse.getIndividual()==null){
                delegateExecution.setVariable("resultsPending", true);
            }
            Gson gson = new Gson();
            FCBReport fcbReport = gson.fromJson(fcbResponse.getIndividual(), FCBReport.class);
            try {
                delegateExecution.setVariable("fcbScoreValue", fcbReport.getReport().get(0).getScore());
                delegateExecution.setVariable("fcbScore", fcbReport.getReport().get(0).getScore());
                delegateExecution.setVariable("fcbStatus", fcbReport.getReport().get(0).getStatus());
                System.out.println("score ->"+fcbReport.getReport().get(0).getScore());
                if(!(fcbReport.getReport().get(0).getStatus().equalsIgnoreCase("ADVERSE")||fcbReport.getReport().get(0).getStatus().equalsIgnoreCase("PEP")))
                   delegateExecution.setVariable("fcbScoreGood", true);
            } catch (Exception e) {
                delegateExecution.setVariable("fcbResponse", fcbResponse);
                if (fcbResponse.getIndividual()!=null&&fcbResponse.getIndividual().contains("OPEN")) {
                    String[] splitResponse = fcbResponse.getIndividual().split(",");
                    for (String res : splitResponse) {
                        if (res.contains("id")) {
                            delegateExecution.setVariable("fcbId", res.replace("id:", ""));
                        }
                    }
                    delegateExecution.setVariable("resultsPending", true);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            delegateExecution.setVariable("resultsPending", true);
            delegateExecution.setVariable("errorCheckingFcb", true);

        }
    }
}
