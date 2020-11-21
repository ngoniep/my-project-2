package com.camunda.demo.config;

import com.camunda.demo.CamundaApplication;
import com.camunda.demo.Model.TokenRequestResponse;
import feign.RequestInterceptor;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.springframework.context.annotation.Bean;

public class KairosConfigurations {

public KairosConfigurations(String uri){

}

    @Bean
    public RequestInterceptor requestInterceptor() {
        System.out.println(CamundaApplication.token);
       // String encoding = Base64Encoder.encode(user + ":" + pwd);
        return requestTemplate -> {
            requestTemplate.header("Content-Type", "application/json");
            requestTemplate.header("app_id","a1ed6777");
            requestTemplate.header("app_key","18a994d4415185af47e8d81ed1ce2505");
            requestTemplate.header("Authorization","Bearer "+ getToken());
        };


    }

    public String getToken(){
        HttpResponse<TokenRequestResponse> response = Unirest.post("http://10.170.3.46:8086/auth/api/oauth/token")
            .header("Authorization", "Basic bW9iaWxlOnBpbg==")
            .header("Cookie", "JSESSIONID=09B5C45EE297D152393BB596960EDC7E")
            .multiPartContent()  .field("grant_type", "password")
            .field("username", "andrew1")
            .field("password", "password")
            .asObject(TokenRequestResponse.class);

        return  response.getBody().getAccess_token();
    }


}
