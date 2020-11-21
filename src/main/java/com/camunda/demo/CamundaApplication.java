package com.camunda.demo;

import com.camunda.demo.Model.TokenRequestResponse;
import com.camunda.demo.Service.VerifyClientExistense;
import com.squareup.okhttp.*;
import feign.RequestInterceptor;
import kong.unirest.ContentType;
import kong.unirest.HttpResponse;
import kong.unirest.MultipartBody;
import kong.unirest.Unirest;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableProcessApplication("/SimpleDemo")
@EnableFeignClients
@EnableDiscoveryClient
public class CamundaApplication {


    public static String token;
    public enum CONTACT_TYPE {PRIMARY, SECONDARY}
    public enum APPLICATION_STATUS {APPLIED,IN_PROGRESS,COMPLETE,FAILED};
  public static void main(String... args) {

System.out.println(new VerifyClientExistense());


      SpringApplication.run(CamundaApplication.class, args);

  }



}
