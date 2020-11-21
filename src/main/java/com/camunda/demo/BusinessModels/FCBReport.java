package com.camunda.demo.BusinessModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FCBReport {
        ArrayList< FCBIndividual > Report = new ArrayList < FCBIndividual > ();



    }
