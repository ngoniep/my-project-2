package com.camunda.demo.Service;

import com.camunda.demo.Model.MastercardCustomer;
import com.camunda.demo.Model.MastercardRegistrationResponseDTO;
import com.camunda.demo.config.MyConfigurations;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class MasterCardRegistrationService {

    @FeignClient(value="MASTERCARD-REGISTRATION-SERVICE", configuration = MyConfigurations.class,decode404 = true)
    public interface MastercardRegistrationClient {

        @RequestLine("POST " + "/mastercard/api/register")
        @RequestMapping(method = RequestMethod.POST, value = "/mastercard/api/register",consumes = "application/json",produces = "application/json" )
        MastercardRegistrationResponseDTO register(@RequestBody MastercardCustomer mastercardCustomer);

    }
}
