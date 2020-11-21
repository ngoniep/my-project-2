package com.camunda.demo.Model.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactDetails {
    @Id
    @GeneratedValue
    String id;
    String contactValue;
    String contactType;
    boolean isPrimary;


}
