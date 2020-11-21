package com.camunda.demo.config;

import feign.Contract;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FooConfiguration {
    @Bean
    public Contract feignContract() {

        System.out.println("here ");
        return  new SpringMvcContract();


    }

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("mobile", "pin");
    }
}
