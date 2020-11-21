package com.camunda.demo.Model.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LivenessFaces {

    Attributes attributes;
    private float chinTipX;
    private float chinTipY;
    private float confidence;
    private float eyeDistance;
    private float face_id;
    private float height;
    private float leftEyeCenterX;
    private float leftEyeCenterY;
    private float pitch;
    private float quality;
    private float rightEyeCenterX;
    private float rightEyeCenterY;
    private float roll;
    private float topLeftX;
    private float topLeftY;
    private float width;
    private float yaw;



}
