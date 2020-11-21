package com.camunda.demo.Service;

import com.camunda.demo.Model.DTOs.PersonalDetails;
import com.camunda.demo.Model.DTOs.PersonalDetailsDTO;
import com.camunda.demo.Model.InstantAccountCreationResponse;
import com.camunda.demo.Model.InstantAccountRequest;
import com.camunda.demo.Model.LinkFlexAccountRequest;
import com.camunda.demo.Model.LinkFlexAccountResponse;
import com.camunda.demo.config.MyConfigurations;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Component
public class PostilionAccountFeign {

    @FeignClient(value="CARD-ALLOCATION-SERVICE", configuration = MyConfigurations.class,decode404 = true)
    public interface AccountCreationClient {

        @RequestLine("POST " + "/card-allocation/api/v1/accountcreation")
        @RequestMapping(method = RequestMethod.POST, value = "/card-allocation/api/v1/accountcreation")
        LinkFlexAccountResponse linkFlexcubeAccount(@RequestBody LinkFlexAccountRequest linkFlexAccountRequest);


        @RequestLine("POST " + "/card-allocation/api/v1/accountcreation")
        @RequestMapping(method = RequestMethod.POST, value = "/card-allocation/api/v1/accountcreation")
        InstantAccountCreationResponse creatPostlionAccount(@RequestBody InstantAccountRequest instantAccountRequest);

    }

    }
