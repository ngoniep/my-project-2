package com.camunda.demo.Model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LinkFlexAccountRequest {
    String idNumber;
    String nameOnCard;
    String mobile;
    String address;
    String dob;
    String flexAccount;
    String firstName;
    String lastName;
    String title;
    @Builder.Default
    String type="";

}
