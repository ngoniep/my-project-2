package com.camunda.demo.Model.DTOs;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id","isActive"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {

    @Id
    @GeneratedValue
    String id;
    Integer streetNumber;
    String streetName;
    String city;
    String country;
    String addressType;
    boolean isActive;
    String proofOfResidence;
}
