package com.camunda.demo.BusinessModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FCBResponse {

    private float code;
    private String individual;
   // ArrayList < FCBReport > report = new ArrayList < FCBReport > ();
    ArrayList<FCBAddresses> addresses = new ArrayList<FCBAddresses>();
    ArrayList<FCBSearchPurpose> searches = new ArrayList<FCBSearchPurpose>();
    ArrayList<Object> active_credit_events = new ArrayList<Object>();
    ArrayList<Object> settled_credit_events = new ArrayList<Object>();
    ArrayList<Object> directorships = new ArrayList<Object>();
    ArrayList<Object> exposures = new ArrayList<Object>();
    ArrayList<Object> additional_info = new ArrayList<Object>();


}




