package com.camunda.demo.Model.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class NextOfKin {
    private String relationship;
    private String fullName;
    private String residentialAddress;
    private List<String> phoneNumbers;
    private List<String> emailAddresses;
    private String idNumber;


}
