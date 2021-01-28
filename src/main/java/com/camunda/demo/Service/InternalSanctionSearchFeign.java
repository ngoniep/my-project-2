package com.camunda.demo.Service;

import com.camunda.demo.BusinessModels.SanctionScore;
import com.camunda.demo.config.MyConfigurations;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public class InternalSanctionSearchFeign {
//sanctions-service
    @FeignClient(value="sanctions-service", configuration = MyConfigurations.class,decode404 = true)
    public interface InternalSanctionsClient {

        @RequestLine("GET " + "/sanctions/api/screen")
        @RequestMapping(method = RequestMethod.GET, value = "/sanctions/api/screen")
        SanctionScore getPersonalDetailsV1(@RequestParam String firstname, @RequestParam String surname, @RequestParam String middlename, @RequestParam(required = false) String dateOfBirth);

    }
}
