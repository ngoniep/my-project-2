package com.camunda.demo.config;


import com.camunda.demo.Model.TokenRequestResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.squareup.okhttp.*;
import feign.RequestInterceptor;
import org.apache.http.entity.ContentType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/*import kong.unirest.HttpResponse;
import kong.unirest.Unirest;*/

@Configuration
public class MyConfigurations {



    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Content-Type", "application/json");
           // requestTemplate.header("Accept", "application/json");
            requestTemplate.header("Accept", ContentType.APPLICATION_JSON.getMimeType());

            try {
                requestTemplate.header("Authorization","Bearer "+ getToken());
            } catch (UnirestException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        };
    }




   public String getToken() throws UnirestException, IOException {
       OkHttpClient client = new OkHttpClient();
       MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
       RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials");
       Request request = new Request.Builder()
           .url("http://10.170.3.46:8080/auth/api/oauth/token")
           .method("POST", body)
           .addHeader("Authorization", "Basic bW9iaWxlOnBpbg==")
           .addHeader("Content-Type", "application/x-www-form-urlencoded")
           .addHeader("Cookie", "JSESSIONID=B1D94684CD82635B0C8BCED79ACD6BF1")
           .build();
       Response response = null;
       try {
           response = client.newCall(request).execute();
       } catch (IOException e) {
          return e.getMessage();
       }

       ObjectMapper mapper = new ObjectMapper();
       mapper.enable(SerializationFeature.INDENT_OUTPUT);

       TokenRequestResponse tokenRequestResponse=mapper.readValue(response.body().string(), TokenRequestResponse.class);
      // System.out.println(tokenRequestResponse);
       return tokenRequestResponse.getAccess_token();

    }
}
