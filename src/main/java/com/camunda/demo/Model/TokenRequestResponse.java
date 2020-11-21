package com.camunda.demo.Model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenRequestResponse {

     String access_token;
     String token_type;
     String refresh_token;
     int expires_in;
      String scope;
}
