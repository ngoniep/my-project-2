package com.camunda.demo.BusinessModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.text.SimpleDateFormat;


@Data
@AllArgsConstructor
@Builder
public class FCBRequest {

    String dob;
    String names;
    String surname;
    String national_id;
    String gender;
    int search_purpose;
    String email;
    String password;
    String drivers_licence;
    String passport;
    String married;
    int nationality;
    int streetno;
    String streetname;
    String building;
    String suburb;
    String pbag;
    String city;
    String telephone;
    String mobile;
    String ind_email;
    int property_density;
    int property_status;
    int occupation_class;
    String employer;
    int employer_industry;
    int salary_band;
    int loan_purpose;
    double loan_amount;

    public FCBRequest() throws Exception{
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/mm/yyyy");
        dob="06-06-1990";
        names="forbes";
        surname="karinda";
        national_id="42246347R71";
        gender="M";
        search_purpose=1;
        email="fbcapitest@fcbureau.co.zw";
        password="password";
        drivers_licence="drv";
        passport="pp";
        married="M";
        nationality=3;
        streetno=24;
        streetname="harvey brown";
        building="fcb mansions";
        suburb="milton park";
        pbag="p.o. highlands";
        city="harare";
        telephone="794367-9";
        mobile="0772495573";
        ind_email="forbes@fcbureau.co.zw";
        property_density=1;
        property_status=2;
        occupation_class=1;
        employer="fcb";
        employer_industry=6;
        salary_band=3;
        loan_purpose=3;
        loan_amount=334;
    }


}
