package com.camunda.demo.Service;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.net.Proxy;


@Service
public class SMSAlertService {

    @Value("${configurations.zssSMSUrl: https://secure1.zss.co.zw/vportal/cnm/vsms/plain}")
    String zssSmsUrl;

    @Value("${configurations.userName: sbaera}")
    String userName;

    @Value("${configurations.password: sharon12*}")
    String password;

    @Value("${configurations.afrosoftSmsUrl:https://smsportal.vas.co.zw/teleoss/sendsms.jsp}")
    String afrosoftSmsUrl;


    @Value("${configurations.afrosoftUserName:fbcbank}")
    String afrosoftUserName;

    @Value("${configurations.afrosoftPassword:fbc5678}")
    String afrosoftPassword;




    public boolean sendSMS(String phoneNumber,String message) {
       // System.out.println("https://secure.zss.co.zw/vportal/cnm/vsms/plain?user=sbaera&password=sharon12*&msisdn=" +phoneNumber+"&message="+message);
        OkHttpClient client = new OkHttpClient();
        //Proxy proxyTest = new Proxy(Proxy.Type.HTTP,new InetSocketAddress("10.170.100.100", 8080));
        //client.setProxy(proxyTest);

       // message=message.replace(" ","");
        //https://smsportal.vas.co.zw/teleoss/sendsms.jsp?user=fbcbank&password=fbc5678&mobiles=263712514512&sms=Peter_Gwisai

        System.out.println("SMS Url "+zssSmsUrl+"?user="+userName+"&password="+password+"&msisdn=" +phoneNumber+"&message="+message);
        Request request = new Request.Builder()
                .url(zssSmsUrl+"?user="+userName+"&password="+password+"&msisdn=" +phoneNumber+"&message="+message)
                .get()
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "736f7f7f-bd6d-80c8-d387-505796e1f932")
                .build();
        Response response;
        try {
            response=client.newCall(request).execute();
            System.out.println(response.message());
            System.out.println(phoneNumber);
            System.out.println(response.isSuccessful());
            System.out.println("Result of SMS Alert send "+response.message());

            if(response.body().string().contains("-1"))
            {
                request = new Request.Builder()
                    .url(afrosoftSmsUrl+"?user="+afrosoftUserName+"&password="+afrosoftPassword+"&mobiles=" +phoneNumber+"&sms="+message)
                    .get()
                    .addHeader("cache-control", "no-cache")
                    .addHeader("postman-token", "736f7f7f-bd6d-80c8-d387-505796e1f932")
                    .build();
                client.newCall(request).execute();
            }



            return true;
        }catch(Exception e){

            System.out.println("SMS Url "+afrosoftSmsUrl+"?user="+afrosoftUserName+"&password="+afrosoftPassword+"&mobiles=" +phoneNumber+"&sms="+message);
             request = new Request.Builder()
                    .url(afrosoftSmsUrl+"?user="+afrosoftUserName+"&password="+afrosoftPassword+"&mobiles=" +phoneNumber+"&sms="+message)
                    .get()
                    .addHeader("cache-control", "no-cache")
                    .addHeader("postman-token", "736f7f7f-bd6d-80c8-d387-505796e1f932")
                    .build();

            try {
                response=client.newCall(request).execute();
                System.out.println(response.message());
                System.out.println(phoneNumber);
                System.out.println(response.isSuccessful());
                System.out.println("Result of SMS Alert send "+response.message());

                return true;
            }catch(Exception e2){

                System.out.println("Result of SMS Alert there was an error ");
                return false;
            }



        }


    }
}
