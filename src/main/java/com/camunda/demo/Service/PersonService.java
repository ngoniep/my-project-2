package com.camunda.demo.Service;

import com.camunda.demo.BusinessModels.PersonDTO;
import com.camunda.demo.config.MyConfigurations;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import kong.unirest.UnirestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class PersonService {
    Response response=null;
    PersonDTO personDTO=null;
//    @Value("${configurations.registrarApi:http://10.170.3.46:8086/registrar-service/person/}")
//    String regGeneralUrl;

    @Value("${configurations.oauthUser:andrew1}")
    String oauthUser;
    @Value("${configurations.outhPassword:password}")
    String outhPassword;
//    @Value("${configurations.oauthUrl:http://10.170.4.67:9193/auth/api/oauth/token}")
//    String oauthUrl;
public PersonDTO getPerson(String idNumber) {



    try {
        com.fasterxml.jackson.databind.ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);


        OkHttpClient client = new OkHttpClient();
//        System.out.println("Registrar general Ip -> "+regGeneralUrl);
//        if(!regGeneralUrl.endsWith("/")){
//            regGeneralUrl+="/";
//        }

        Request request = new Request.Builder()
                .url("http://10.170.3.40:8080/registrar-service/person/"+idNumber.replace("-","").replace(" ",""))
                .get()
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "63da98ef-8102-9a0b-e3f6-f6f559e438ab")
                //.addHeader("Authorization","Bearer "+new MyConfigurations(oauthUrl,"bW9iaWxlOnBpbg==",oauthUser,outhPassword).getToken())
                .build();

        try {
             response = client.newCall(request).execute();
             String result=response.body().string();
             System.out.println("here is the detail from Registrar General"+result);
             personDTO= mapper.readValue(result,PersonDTO.class);
             return personDTO;

        } catch (IOException e) {
            e.printStackTrace();
        }

    } catch (
            UnirestException e) {
        e.printStackTrace();
        System.out.println();
    }
    return personDTO;




}



}
