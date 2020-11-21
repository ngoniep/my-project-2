package com.camunda.demo.Service;

import com.camunda.demo.BusinessModels.PersonDTO;
import com.camunda.demo.Model.DTOs.CreateAccountResponse;
import com.camunda.demo.Model.DTOs.ResposeList;
import com.camunda.demo.Model.InstantAccountCreationResponse;
import com.camunda.demo.Model.InstantAccountRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.squareup.okhttp.*;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;


@Service
public class InstantCardCreationService {

    @Value("${configurations.postilionUrl:http://10.170.3.21:9090/v1/accountcreation}")
    String postilionUrl;

    @Autowired
    PostilionAccountFeign.AccountCreationClient accountCreationClient;

    @Autowired
    VerifyClientExistense verifyClientExistense;

    public InstantAccountCreationResponse createAccount(InstantAccountRequest instantAccountRequest) {
        String accountType=(instantAccountRequest.getType().equalsIgnoreCase("")?"Instant":"wallet");

        try {
            Optional<String> account=verifyClientExistense.verifyClientExists(instantAccountRequest.getIdNumber(),instantAccountRequest.getMobile(),accountType);
            if(account.isPresent()&&account.get().startsWith("6")){
                return InstantAccountCreationResponse.builder()
                    .account(account.get())
                    .message("Account Already Exists")
                    .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accountCreationClient.creatPostlionAccount(instantAccountRequest);
       /* ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String json = null;
        try {
            json = mapper.writeValueAsString(instantAccountRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("Started the attempt to create an account in flexcube "+json);


        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
            .url(postilionUrl)
            .post(body)
            .addHeader("content-type", "application/json")
            .addHeader("cache-control", "no-cache")
            .addHeader("postman-token", "d2d489cd-5628-4665-acf4-44efffcf8773")
            .build();
        //System.out.print(request.headers().);

        try {
            Response response1 = client.newCall(request).execute();
            String rs = response1.body().string();
            System.out.println(rs);
            rs = rs.replace("[", "").replace("]", "");
            InstantAccountCreationResponse instantAccountCreationResponse = mapper.readValue(rs, InstantAccountCreationResponse.class);
            System.out.print(instantAccountCreationResponse);
            return instantAccountCreationResponse;
            //productApplicationClient.trackApplication();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }*/

    }

}
