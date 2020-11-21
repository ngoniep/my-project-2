package com.camunda.demo.Service;


import com.camunda.demo.Model.DTOs.*;
import com.camunda.demo.config.MyConfigurations;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class KairosServices_bk2 {

    @FeignClient(value = "GATEWAY-SERVICE", configuration = MyConfigurations.class,decode404 = true)
    public interface EnrollmentClient {

        @RequestLine("POST " + "/handler/api/facial-recognition/enroll-selfie")
        @RequestMapping(method = RequestMethod.POST, value = "/handler/api/facial-recognition/enroll-selfie")
        KairosEnrolResponse enroll(@RequestBody KairosEnrollObject kairosEnrollObject);

        @RequestLine("POST " + "/handler/api/facial-recognition/verify-id")
        @RequestMapping(method = RequestMethod.POST, value = "/handler/api/facial-recognition/verify-id")
        KairosRecognizeResponse recognize(@RequestBody KairosRecognizeObject kairosRecognizeObject);


    }


}
