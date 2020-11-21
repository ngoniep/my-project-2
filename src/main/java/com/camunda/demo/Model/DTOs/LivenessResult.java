package com.camunda.demo.Model.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LivenessResult {

    ArrayList< LivenessFaces > faces = new ArrayList < LivenessFaces>() ;
    private String file;
    private float height;
    private String status;
    private float width;



}
