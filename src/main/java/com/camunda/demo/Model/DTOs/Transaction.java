package com.camunda.demo.Model.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction {
    private double confidence;

    private double eyeDistance;

    private String face_id;

    private String gallery_name;

    private double height;

    private double image_id;

    private double pitch;

    private double quality;

    private double roll;

    private String status;

    private String subject_id;

    private String timestamp;

    private double topLeftX;

    private double topLeftY;

    private double version;

    private double width;

    private double yaw;


}
