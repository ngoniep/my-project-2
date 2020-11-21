package com.camunda.demo.Service;


import com.camunda.demo.BusinessModels.CreateAccountRequest;
import com.camunda.demo.BusinessModels.CreateCustomerRequest;
import com.camunda.demo.Model.DTOs.CreateAccountResponse;
import com.camunda.demo.Model.DTOs.CustomerCreationResponse;
import com.camunda.demo.config.MyConfigurations;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
public class FlexcubeFeignServices {
    @FeignClient(value="FLEXCUBE-CUSTOMER-CREATION-SERVICE", configuration = MyConfigurations.class,decode404 = true)
    public interface customerCreationClient {

        @RequestLine("POST " + "/flex-cusomer/api/create-customer")
        @RequestMapping(method = RequestMethod.POST, value = "/flex-cusomer/api/create-customer")
        CustomerCreationResponse createCustomer(@RequestBody CreateCustomerRequest createCustomerRequest);
    }

    @FeignClient(value="FLEXCUBE-ACCOUNT-CREATION-SERVICE", configuration = MyConfigurations.class,decode404 = true)
    public interface accountCreationClient {

        @RequestLine("POST " + "/flex-account/api/create-account")
        @RequestMapping(method = RequestMethod.POST, value = "/flex-account/api/create-account")
        CreateAccountResponse createAccount(@RequestBody CreateAccountRequest createAccountRequest);
    }

    }
