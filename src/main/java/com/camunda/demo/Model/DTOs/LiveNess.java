package com.camunda.demo.Model.DTOs;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LiveNess {
    String image;
    String selector="liveness";
}
