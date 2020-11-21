package com.camunda.demo.Service;

import com.camunda.demo.BusinessModels.FCBReport;
import com.camunda.demo.BusinessModels.SanctionScore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class InternalSanctionsService {
    HttpResponse<SanctionScore> response=null;
    @Value("${configurations.sanctionsScreening: http://10.170.3.40:9781/kyc-screening-service/screen}")
    String sanctionsUrl;
public SanctionScore getPerson(String firstName, String secondName, String lastName, String dateOfBirth) {
    Unirest.config().reset();
    HttpResponse<String> response = Unirest.get(sanctionsUrl+"?firstname="+firstName.replace(" ","%20")+"&middlename="+secondName.replace(" ","%20")+"&surname="+lastName.replace(" ","%20")+"&datOfBirth="+dateOfBirth.replace(" ","%20"))
            .asString();
    Gson gson = new Gson();
 //   System.out.println(response.getBody());
    SanctionScore sanctionScore = gson.fromJson(response.getBody(), SanctionScore.class);

    return sanctionScore;
    }




}
