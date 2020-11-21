package com.camunda.demo.Service;



import com.camunda.demo.BusinessModels.PersonDTO;
import com.camunda.demo.Model.DTOs.*;
import com.camunda.demo.Model.ProductApplication;
import com.camunda.demo.config.MyConfigurations;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Component
public class PersonFeignService {


    @FeignClient(value="PROFILE-SERVICE", configuration = MyConfigurations.class,decode404 = true)
    public interface PersonClient {

        @RequestLine("POST " + "/profile/api/profile/post-personal-details")
        @RequestMapping(method = RequestMethod.POST, value = "/profile/api/profile/post-personal-details")
        PersonalDetailsDTO addPerson(@RequestBody PersonalDetails personalDetails);

        @RequestLine("GET " + "/profile/api/profile/{idNumber}")
        @RequestMapping(method = RequestMethod.GET, value = "/profile/api/profile/{idNumber}",produces = {"application/json","text/html;charset=utf-8"})
        Optional<PersonalDetailsDTO> getPersonalDetailsByIdNumber(@PathVariable String idNumber);


        @RequestLine("GET " + "/profile/api/profile/user/{userName}")
        @RequestMapping(method = RequestMethod.GET, value = "/profile/api/profile/user/{userName}")
        PersonalDetailsDTO getPersonalDetailsByUsername(@PathVariable String userName);

        @RequestLine("PUT " + "/profile/api/upgrade-kyc-lite")
        @RequestMapping(method = RequestMethod.PUT, value = "/profile/api/upgrade-kyc-lite")
        PersonalDetailsDTO updateKycLiteToFullKyc(@RequestBody PersonalDetails personalDetails);

        @RequestLine("PUT " + "/profile/api/profile/update-identity")
        @RequestMapping(method = RequestMethod.PUT, value = "/profile/api/profile/update-identity")
        PersonalDetailsDTO updateIdentity(@RequestBody PersonDTO personalDetails);

        @RequestLine("DELETE " + "/profile/api/delete-address/{addressId}")
        @DeleteMapping( value = "/profile/api/delete-address/{addressId}")
        Optional<Address> deleteAddress(@PathVariable String addressId);


        @RequestLine("DELETE " + "/profile/api/delete-employment/{employmentId}")
        @DeleteMapping( value = "/profile/api/delete-employment/{employmentId}")
        Optional<EmploymentDetails> deleteEmployentDetails(@PathVariable String employmentId);


       // Optional<NextOfKin> deleteNextOfKins(@PathVariable String nextOfKinId);

        @RequestLine("PUT " + "/profile/api/add-application")
        @RequestMapping(method = RequestMethod.PUT, value = "/profile/api/add-application")
        PersonalDetailsDTO associateApplicationToPerson(@RequestBody ProductApplication productApplication);

        @RequestLine("POST " + "/profile/api/next-of-kin/{idNumber}")
        @RequestMapping(method = RequestMethod.POST, value = "/profile/api/next-of-kin/{idNumber}")
        NextOfKin addNextOfKin(@PathVariable String idNumber, @RequestBody NextOfKin nextOfKin); //NextOfKin nextOfKin, String userName

        @RequestLine("DELETE " + "/profile/api/delete-nextOfkin/{nextOfKinId}")
        @DeleteMapping("/profile/api/delete-nextOfkin/{nextOfKinId}")
        Optional<NextOfKin> deleteNextOfkin(@PathVariable String nextOfKinId);

        @RequestLine("PUT " + "/profile/api/address/{username}")
        @RequestMapping(method = RequestMethod.PUT, value = "/profile/api/address/{username}")
        Address addAddressDetails(@PathVariable String username, @RequestBody Address address);

        @RequestLine("PUT " + "/profile/api/update-contact/{username}")
        @RequestMapping(method = RequestMethod.PUT, value = "/profile/api/update-contact/{username}")
        ContactDetails addContactDetails(@PathVariable String username, @RequestBody ContactDetails contactDetails);

        @RequestLine("DELETE " + "/profile/api/delete-contact/{addressId}")
        @DeleteMapping("/profile/api/delete-contact/{addressId}")
        Optional<ContactDetails> deleteContact(@PathVariable String addressId);

        @RequestLine("PUT " + "/profile/api/employment-details/{idNumber}")
        @RequestMapping(method = RequestMethod.PUT, value = "/profile/api/employment-details/{idNumber}")
        EmploymentDetails addEmploymentDetails(@PathVariable String idNumber, @RequestBody EmploymentDetails employmentDetails);

        @RequestLine("DELETE " + "/profile/api/delete-employment/{employmentId}")
        @DeleteMapping("/profile/api/delete-employment/{employmentId}")
        Optional<EmploymentDetails> deleteEmploymentDetail(@PathVariable String employmentId);

        @RequestLine("POST " + "/profile/api/add-application")
        @RequestMapping(method = RequestMethod.POST, value = "/profile/api/add-application")
        PersonalDetailsDTO updateProductApplication(@RequestBody ProductApplication productApplication);


    }


}
