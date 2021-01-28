package com.camunda.demo.Service;

import com.camunda.demo.BusinessModels.PersonDTO;
import com.camunda.demo.config.MyConfigurations;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public class RegistrarGeneralFeign {


    @FeignClient(value="REGISTRAR-GENERAL-SERVICE", configuration = MyConfigurations.class,decode404 = true)
    public interface RegistrarGeneralClient {

        @RequestLine("GET " + "/registrar-service/person/{id}")
        @RequestMapping(method = RequestMethod.GET, value = "/registrar-service/person/{id}")
        PersonDTO getPersonalDetails(@PathVariable String id);


        @RequestLine("GET " + "/registrar-service/person")
        @RequestMapping(method = RequestMethod.POST, value = "/registrar-service/person/")
        PersonDTO getPersonalDetailsV1(@RequestParam String id);

    }
}
