package com.camunda.demo;

import com.camunda.demo.BusinessModels.CreateCustomerRequest;
import com.camunda.demo.Model.DTOs.*;
import com.camunda.demo.Service.*;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerCreationDelegate implements JavaDelegate {
    @Autowired
    GatewayServiceFeign.ProductApplicationClient productApplicationClient;

    @Autowired
    PersonFeignService.PersonClient personServiceFeign;

    @Autowired
    FlexcubeFeignServices.customerCreationClient createCustomerService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        try{
            String customerNumber = delegateExecution.getVariable("customerNumber").toString();
        }catch (Exception e){
            delegateExecution.setVariable("customerNumber","");
        }


        if (delegateExecution.getVariable("customerNumber").toString().equalsIgnoreCase("")||delegateExecution.getVariable("customerNumber").toString().contains("customerNumber")) {
            String firstName = delegateExecution.getVariable("registrarFirstName").toString();
            String lastName = delegateExecution.getVariable("registrarSurname").toString();
            //List<String> anomalies = new ArrayList<>();
            PersonalDetailsDTO personalDetails = personServiceFeign.getPersonalDetailsByIdNumber(delegateExecution.getVariable("personNo").toString()).get();
            List<Address> addressList = personalDetails.getAddresses();
          //  Address address = null;
            String addressLine1 = "Address Line 1";
            String addressLine2 = "Address Line 2";
            String addressLine3 = "Address Line 3";
            String mobileNumber = "";
            try {
                if (addressList != null) {

                    addressLine1 = addressList.get(0).getStreetName();
                    addressLine2 = addressList.get(0).getCity();
                    addressLine3 = addressList.get(0).getCountry();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            delegateExecution.setVariable("address",addressLine1+" "+addressLine2+" "+addressLine3);
            List<ContactDetails> contactDetails = personalDetails.getContactDetails();
            if (contactDetails != null) {
                for (ContactDetails contactDetails1 : contactDetails) {
                    if (contactDetails1.getContactType().equalsIgnoreCase("mobile")) {
                        mobileNumber = contactDetails1.getContactValue();
                        delegateExecution.setVariable("mobileNumber", mobileNumber);
                        break;
                    }
                }
            }

            String midname = "";
            String emailAddress="";
            try{
                emailAddress=delegateExecution.getVariable("emailAddress").toString();
            }catch (Exception ignored){

            }
            delegateExecution.setVariable("mobileNumber", personalDetails.getPrimaryPhoneNumber());
            try {
                if (firstName.split(" ").length > 1)
                    midname = firstName.split(" ")[1];
            }catch (Exception ignored){

            }
            CustomerCreationResponse customerCreationResponse = null;
            try {
                CreateCustomerRequest customer = CreateCustomerRequest.builder()
                    .customerType("I")
                    .addressLine1(addressLine1)
                    .addressLine2(addressLine2)
                    .addressLine3(addressLine3)
                    .mobileNumber(mobileNumber)
                    .country("ZWD")
                    .nationality("ZWD")
                    .location("HRE")
                    .media("MAIL")
                    .uidName("NATIONAL_ID")
                    .idNumber(delegateExecution.getVariable("personNo").toString())
                    .shortName(delegateExecution.getVariable("personNo").toString())
                    .name(firstName)
                    .middleName(midname)
                    .surname(lastName)
                    .citizenship("ZWD")
                    .creditRating("2")
                    //.((TimeUnit.DAYS.convert(Math.abs(new Date().getTime() - new SimpleDateFormat("yyyy-MM-dd").parse(delegateExecution.getVariable("dateOfBirthFlex").toString()).getTime()), TimeUnit.MICROSECONDS) / 365 > 18 ? "M" : ""))
                    .gender(delegateExecution.getVariable("registrarGender").toString())
                    .nationalId(delegateExecution.getVariable("personNo").toString())
                    .lang("ENG")
                    .dateOfBirth(delegateExecution.getVariable("dateOfBirthFlex").toString())
                    .currentDesignation("Others")
                    //.currentEmployer("2 to 5 years")
                    .workAddress1("My Work address")
                    .workEmail("my@workemail.com")
                    .emailAddress(emailAddress)
                    .employerDescription(delegateExecution.getVariable("nameOfEmployer").toString())
                    .placeOfBirth(delegateExecution.getVariable("placeOfBirth").toString())
                    .birthCountry("ZWD")
                    //  .salary((application.getEmploymentDetails().size()>0)?(int)Math.round(Double.parseDouble(application.getEmploymentDetails().get(0).getAverageMonthlyIncome())):0)
                    //  .employmentTenure((application.getEmploymentDetails().size()>0)?application.getEmploymentDetails().get(0).getLengthOfEmployment():0)
                    .build();
                System.out.println("Here is the customer that is being passed for customer creation" + customer);

                customerCreationResponse = createCustomerService.createCustomer(customer);
                delegateExecution.setVariable("customerNumber", customerCreationResponse.getCustomerNumber());
                System.out.println(customerCreationResponse);
                System.out.println(customerCreationResponse.isExists());
                // if(customerCreationResponse.isExists())
                //    anomalies.add("This customer already exists under the customer Number: "+customerCreationResponse.getCustomerDetails().getFullName()+" the customer number is "+customerCreationResponse.getCustomerNumber());
                ////else
                //    anomalies.add("A customer recpord has been successfully create for "+customerCreationResponse.getCustomerDetails().getFullName()+" the customer number is "+customerCreationResponse.getCustomerNumber());

                System.out.println(customerCreationResponse);
            } catch (Exception e) {
                e.printStackTrace();
              //  anomalies.add("There are errors in creating the customer record for the client");
            }

        }
    }
}
