package com.camunda.demo.Service;

import com.camunda.demo.Model.DTOs.FileSaveRequest;
import com.camunda.demo.Model.InstantAccountCreationResponse;
import com.camunda.demo.Model.InstantAccountRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.squareup.okhttp.*;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class FileCreationService {


    public Boolean createFile(FileSaveRequest fileSaveRequest) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String json = null;
        try {
            json = mapper.writeValueAsString(fileSaveRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("Started the attempt to write file on server");


        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
            .url("http://10.170.3.46:9999/")
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
            Boolean fileCreationResponse = mapper.readValue(rs, Boolean.class);
            System.out.print(fileCreationResponse);
            return fileCreationResponse;
            //productApplicationClient.trackApplication();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

}
