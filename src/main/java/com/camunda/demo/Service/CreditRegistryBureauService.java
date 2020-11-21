package com.camunda.demo.Service;


import com.camunda.demo.Model.ReportRequestDTO;
import com.camunda.demo.Model.ScoringReportModel;
import com.camunda.demo.config.MyConfigurations;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.squareup.okhttp.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CreditRegistryBureauService {
    @Value("${configurations.gatewayUrl:http://10.170.3.46:8086/handler/api}")
    String gatewayUrl;
    @Autowired
    GatewayServiceFeign.ProductApplicationClient productApplicationClient;

    public ScoringReportModel getCreditRegistryReport(String idNumber){
        ScoringReportModel scoringReportModel=new ScoringReportModel();
        List<String> sections=new ArrayList<>();
        sections.add("Dashboard");
        sections.add("SubjectInfo");
        sections.add("ContractList");
        sections.add("CIP");
        sections.add("SubjectHistory");
        sections.add("ContractDetail");
        sections.add("ContractList");
        sections.add("ContractSummary");
        sections.add("Disputes");
        sections.add("Inquiries");
        sections.add("CIQ");
        sections.add("CurrentRelations");
        sections.add("ContractList");
        sections.add("CreditRegistryReport");
        sections.add("ScoringReport");
        sections.add("CreditRegistryReportPlus");
     //contractModelList
        ReportRequestDTO reportRequestDTO=ReportRequestDTO.builder()
            .idNumber(idNumber)
            .idType("NATIONAL_ID")
            .reasonText("")
            .customerConsent(true)
            .inquiryReasons("APPLICATION_FOR_CREDIT_OR_AMENDMENT_OF_CREDIT_TERMS")
            .sections(sections)
            .subjectType("INDIVIDUAL")
            .build();

        System.out.print("starting Credit registry attempt");
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        String json = null;
        try {
            json = mapper.writeValueAsString(reportRequestDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(json);
        RequestBody body = RequestBody.create(mediaType, json
            //"{\n \t\"idNumber\": \"1486/2005\",\n    \"customerConsent\": true,\n    \"idType\":\"REGISTRATION_NUMBER\",\n    \"reasonText\":\"\",\n    \"inquiryReasons\":\"APPLICATION_FOR_CREDIT_OR_AMENDMENT_OF_CREDIT_TERMS\",\n    \"sections\":[\n    \t\"Dashboard\",\n    \t\"ContractList\",\n         \"SubjectInfo\",\n         \"CIP\",\n         \"SubjectHistory\",\n          \"ContractDetail\",\n           \"ContractList\",\n            \"ContractSummary\",\n           \"Disputes\",\n            \"Inquiries\",\n            \"CIQ\",\n            \"CurrentRelations\",\n            \"CreditRegistryReport\",\n            \"ScoringReport\",\n            \"CreditRegistryReportPlus\"\n    \t],\n    \"subjectType\":\"COMPANY\",\n    \"reportName\":\"CreditinfoReportPlus\"\n }\n \n\n"
        );
        Request request = new Request.Builder()
            .url("http://localhost:9005/getCustomReport")
            .method("POST", body)
           // .addHeader("Authorization", "Bearer "+new MyConfigurations().getToken())
            .addHeader("Content-Type", "application/json")
            .build();
        Response response;

        {
            try {
                System.out.print("client about to be executed");
                response = client.newCall(request).execute();
                System.out.print("client executed");
                 scoringReportModel=mapper.readValue(response.body().string(), ScoringReportModel.class);
              //  System.out.print(scoringReportModel);
                return scoringReportModel;
            } catch (SocketTimeoutException e) {
                System.out.print("client executed and failed");
                e.printStackTrace();
                return scoringReportModel;
            } catch(IOException e){
                return scoringReportModel;
            }
        }

    }
}
