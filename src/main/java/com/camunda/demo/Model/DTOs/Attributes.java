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
public class Attributes {
    private float age;
    private float asian;
    private float black;
    Gender gender;
    private String glasses;
    private float hispanic;
    private String lips;
    private float liveness;
    private float other;
    private float white;


}
