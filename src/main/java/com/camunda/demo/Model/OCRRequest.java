package com.camunda.demo.Model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OCRRequest {

    String idImageBase64String;
    @Builder.Default
    String imageType="jpeg";
    @Builder.Default
    String documentType="NationalID";
}
