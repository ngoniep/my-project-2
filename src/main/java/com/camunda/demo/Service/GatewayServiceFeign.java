package com.camunda.demo.Service;

import com.camunda.demo.BusinessModels.PersonDTO;
import com.camunda.demo.Model.*;
import com.camunda.demo.Model.DTOs.PersonalDetailsDTO;
import com.camunda.demo.config.MyConfigurations;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Component
public class GatewayServiceFeign {

    @FeignClient(value="GATEWAY-SERVICE", configuration = MyConfigurations.class,decode404 = true)
    public interface ProductApplicationClient {

        @RequestLine("POST " + "/handler/api/update-application-status/")
        @RequestMapping(method = RequestMethod.POST, value = "/handler/api/update-application-status/")
        ProductApplication updateProductApplication(@RequestBody ProductApplication productApplication);

        @RequestLine("GET " + "/handler/api/track-application/{applicationId}")
        @RequestMapping(method = RequestMethod.GET, value = "/handler/api/track-application/{applicationId}")
        ProductApplication trackApplication(@PathVariable String applicationId);

        @RequestLine("GET " + "/handler/api/ocr")
        @RequestMapping(method = RequestMethod.GET, value = "/handler/api/track-application/{applicationId}")
        OCRPerson doOCR(@RequestBody OCRRequest ocrRequest);

    }

    }
