package com.camunda.demo;

import com.camunda.demo.BusinessModels.FCBRequest;
import com.camunda.demo.BusinessModels.FCBResponse;
import com.camunda.demo.BusinessModels.PersonDTO;
import com.camunda.demo.Service.FCBService;
import com.camunda.demo.Service.PersonFeignService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class FCBTest {


   /* @Autowired
    PersonFeignService.PersonClient identityUpdateFeign;

    @Test
    public void testFCB() {

      *//*  FCBService fcbService=new FCBService();
        FCBRequest fcbRequest = FCBRequest.builder()
            .dob("06-06-1990")
            .surname("White no")
            .names("tawanda munongi")
            .national_id("42 - 246347R71")
            .gender("M")
            .search_purpose(1)
            .email("fbcapitest@fcbureau.co.zw")
            .password("password ")
            .drivers_licence("drv")
            .passport("pp")
            .married("M")
            .nationality(3)
            .streetno(24)
            .streetname("harvey brown")
            .building("fcb mansions")
            .suburb("milton park")
            .pbag("p.o. highlands")
            .city("harare")
            .telephone("794367 - 9")
            .mobile("0772495573 ")
            .ind_email("forbes@fcbureau.co.zw ")
            .property_density(1)
            .property_status(2)
            .occupation_class(1)
            .employer("fcb")
            .employer_industry(6)
            .salary_band(3)
            .loan_purpose(3)
            .loan_amount(334)
            .names("Pardon ")
            .build();

        try {
            System.out.println(fcbService.getPerson(fcbRequest));
        } catch (Exception e) {
            e.printStackTrace();
        }*//*
    }

    @Test
    public void testIdentityUpdate(){
        *//*try {
            identityUpdateFeign.updateIdentity(PersonDTO.builder()
                .birthPlace("Mukamba Clinic")
                .dateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse("1990-06-06"))
                .firstName("PARDON NGONI")
                .surname("WHITE")
                .sex("M")
                .status("A")
                .build());
        } catch (Exception e) {
            e.printStackTrace();
        }*//*
    }

    @Test
    public void testIDNumber(){
      *//*  String idNumber="420246347R71";
        idNumber=idNumber.substring(0,2)+idNumber.substring(3);
        System.out.println(idNumber);
       // Assertions.assertTr*//*
    }*/

}
