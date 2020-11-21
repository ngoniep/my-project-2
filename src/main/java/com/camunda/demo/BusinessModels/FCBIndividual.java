package com.camunda.demo.BusinessModels;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FCBIndividual {
        private String id;
        private String Subscriber;
        private String User;
        private String Date;
        private String Full_Name;
        private String National_ID;
        private float Score;
        private String Status;
        private String DOB;
        private String Passport;
        private String License;
        private String Mobile;
        private String Phone;
        private String Email;
        private String Gender;
        private String Married;
        private String Nationality;


    }
