package com.camunda.demo.Service;


import com.camunda.demo.BusinessModels.FCBRequest;
import com.camunda.demo.BusinessModels.FCBResponse;
import kong.unirest.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;


@Service("fcbService")
@RefreshScope
public class FCBService {
    HttpResponse<FCBResponse> response=null;
    String fcbUrl;

    String sanctionsUrl;

    public void setFcbUrl(String fcbUrl) {
        this.fcbUrl = fcbUrl;
    }

    public void setSanctionsUrl(String sanctionsUrl) {
        this.sanctionsUrl = sanctionsUrl;
    }

    public  FCBResponse getPerson(String searchId){
        System.out.println("Method requiring id invoked");
        try {
            HttpResponse<FCBResponse> response = Unirest
                .post("https://www.fcbureau.co.zw/api/getIndividual?email=fbcapitest@fcbureau.co.zw&password=password&id=" + searchId)
                .asObject(FCBResponse.class);
            return response.getBody();
        }catch (Exception e){
            System.out.println(Unirest
                .post("https://www.fcbureau.co.zw/api/getIndividual?email=fbcapitest@fcbureau.co.zw&password=password&id=" + searchId)
                .asString()
            );
            System.out.println("Attempt to get response for the FCB Search ID "+searchId+" failed, retrying again in the next 20 Minutes, ther error message is"+response.getStatus());
            e.printStackTrace();
        }
        return null;
    }

    public FCBResponse getPerson(FCBRequest fcbRequest) throws Exception {
        System.out.println("Method not requiring id invoked");

    //SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd-mm-yyyy");
    String dateOfBirth=fcbRequest.getDob();
    fcbRequest.setStreetno(0);

    System.out.println(sanctionsUrl);
        String request="";
    try {

       Unirest.config().reset();
       Unirest.config().verifySsl(false);
       Unirest.config().connectTimeout(3000000);
        request="https://www.fcbureau.co.zw/api/newIndividual?dob="
               +dateOfBirth.replace(" ","")
               +"&names="+fcbRequest.getNames().replace(" ","%20")
               +"&surname="+fcbRequest.getSurname().replace(" ","%20")
               +"&national_id="+fcbRequest.getNational_id().replace(" ","%20")
               +"&gender="+fcbRequest.getGender().replace(" ","%20")
               +"&search_purpose="+fcbRequest.getSearch_purpose()
               +"&email="+fcbRequest.getEmail().replace(" ","")
               +"&password="+fcbRequest.getPassword().replace(" ","")
               +"&drivers_licence="+fcbRequest.getDrivers_licence().replace(" ","")
               +"&passport="+fcbRequest.getPassport().replace(" ","")
               +"&married="+fcbRequest.getMarried().replace(" ","%20")
               +"&nationality="+fcbRequest.getNationality()
               +"&streetno="+fcbRequest.getStreetno()
               +"&streetname="+fcbRequest.getStreetname().replace(" ","%20")
               +"&building="+fcbRequest.getBuilding().replace(" ","%20")
               +"&suburb="+fcbRequest.getSuburb().replace(" ","%20")
               +"&pbag="+fcbRequest.getPbag().replace(" ","%20")
               +"&city="+fcbRequest.getCity().replace(" ","%20")
               +"&telephone="+fcbRequest.getTelephone().replace(" ","%20")
               +"&mobile="+fcbRequest.getMobile().replace(" ","%20")
               +"&ind_email="+fcbRequest.getInd_email().replace(" ","")
               +"&property_density="+fcbRequest.getProperty_density()
               +"&property_status="+fcbRequest.getProperty_status()
               +"&occupation_class="+fcbRequest.getOccupation_class()
               +"&employer="+fcbRequest.getEmployer().replace(" ","%20")
               +"&employer_industry="+fcbRequest.getEmployer_industry()
                +"&salary_band="+fcbRequest.getSalary_band()
                +"&loan_purpose="+fcbRequest.getLoan_purpose()
               +"&loan_amount="+fcbRequest.getLoan_amount()
               ;
  System.out.println(request);
         response = Unirest.post(request)
                 .header("Content-Type", "application/json")
                 .header("Accept", "*/*")
                .asObject(FCBResponse.class);
//        System.out.println(" here is the response as a string "+Unirest.post(request)
//            .header("Content-Type", "application/json")
//            .header("Accept", "*/*")
//            .asString().getBody());

    } catch (
            UnirestException e) {
        e.printStackTrace();
        System.out.println();
    } catch (Exception e) {
        e.printStackTrace();
    }
   System.out.println("The response is "+response);
    if(response.getBody().getIndividual()==null){
         response.getBody().setIndividual(Unirest.post(request)
             .header("Content-Type", "application/json")
             .header("Accept", "*/*")
             .asString().getBody());
    }
         return response.getBody();




}


    static {
        disableSslVerification();
    }

    private static void disableSslVerification() {
        try
        {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
            };

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }
}
