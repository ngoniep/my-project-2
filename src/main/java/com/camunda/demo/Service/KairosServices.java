package com.camunda.demo.Service;


import com.camunda.demo.Model.DTOs.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.exceptions.UnirestException;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class KairosServices {

    @Autowired
    PersonFeignService.PersonClient personalDetailsSerive;

   public KairosEnrolResponse enrollUser(KairosEnrollObject kairosEnrollObject){

       ObjectMapper mapper = new ObjectMapper();
       HttpResponse<KairosEnrolResponse> response = null;
       String myresponse=null;
       try {
            myresponse = Unirest.post("https://api.kairos.com/enroll")
               .header("app_id", "a1ed6777")
               .header("app_key", "18a994d4415185af47e8d81ed1ce2505")
               .header("Content-Type", "application/json")
               .body(mapper.writeValueAsString(kairosEnrollObject))
               .asString().getBody();

       } catch (JsonProcessingException e) {
           e.printStackTrace();
       }

       try {
           //System.out.println(myresponse);
           if(myresponse.contains("Errors"))
           {
               String[] errorDetail=myresponse.split("\"Message\":\"");
               return KairosEnrolResponse.builder().face_id(errorDetail[1].replace("\"","")).build();
           }
           return mapper.readValue(myresponse,KairosEnrolResponse.class);

       } catch (JsonProcessingException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }


       return response.getBody();

   }

   public KairosEnrolResponse enrollByIdNumber(String idNumber) throws IOException, UnirestException {
       String base64StringOfSelfie=personalDetailsSerive.getPersonalDetailsByIdNumber(idNumber).get().getSelfieBase64String();
       KairosEnrollObject kairosEnrollObject=KairosEnrollObject.builder()
           .image(base64StringOfSelfie)
           .subject_id(idNumber)
           .build();
       return enrollUser(kairosEnrollObject);
   }

    public KairosRecognizeResponse recognizeByIdNumber(String idNumber) throws IOException, UnirestException {
        String base64StringIdNumber=personalDetailsSerive.getPersonalDetailsByIdNumber(idNumber).get().getIdImageBase64String();
        KairosRecognizeObject kairosRecognizeObject=KairosRecognizeObject.builder()
            .gallery_name("MyGallery")
            .image(base64StringIdNumber)
            .build();
        return recognize(kairosRecognizeObject);
    }

    public KairosRecognizeResponse recognizeByIdNumber(String idNumber, String base64String){
       // String base64StringIdNumber=personalDetailsSerive.getPersonalDetailsByIdNumber(idNumber).getIdImageBase64String();
        KairosRecognizeObject kairosRecognizeObject=KairosRecognizeObject.builder()
            .gallery_name("MyGallery")
            .image(base64String)
            .build();
        return recognize(kairosRecognizeObject);
    }


    public KairosRecognizeResponse recognize(KairosRecognizeObject kairosRecognizeObject){

        ObjectMapper mapper = new ObjectMapper();
        HttpResponse<KairosEnrolResponse>  response = null;
        String myresponse=null;
        try {
            myresponse = Unirest.post("https://api.kairos.com/recognize")
                .header("app_id", "a1ed6777")
                .header("app_key", "18a994d4415185af47e8d81ed1ce2505")
                .header("Content-Type", "application/json")
                .body(mapper.writeValueAsString(kairosRecognizeObject))
                .asString().getBody();

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try {

            if(myresponse.contains("Errors"))
            {
                String errorDetail=myresponse.split("\"Message\":\"")[1].replace("\"","").replace("}]}","").replace("\\","");
                List<RecognizeImages> images=new ArrayList<>();

                images.add(RecognizeImages.builder()
                    .transaction(RecognizeTransaction.builder()
                        .status(errorDetail)
                        .build())
                    .build());
                return KairosRecognizeResponse.builder()
                    .images(images)
                .build();
            }
            System.out.println(myresponse);
            return mapper.readValue(myresponse,KairosRecognizeResponse.class);


        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;

    }
}
