package com.camunda.demo.Model.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecognizeTransaction
{
    private double confidence;

    private String enrollment_timestamp;

    private double eyeDistance;

    private String face_id;

    private String gallery_name;

    private double height;

    private double liveness;

    private double pitch;

    private double quality;

    private double roll;

    private String status;

    private String subject_id;

    private double topLeftX;

    private double topLeftY;

    private double width;

    private double yaw;


}


