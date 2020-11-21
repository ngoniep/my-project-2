package com.camunda.demo.BusinessModels;


import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FCBSearchPurpose {

        private String search_purpose;
        private String date_searched;
        private String status;
        private float score;
        private String subscriber_name;
        private String branch_name;
        private String subscriber_short_name;


    }
