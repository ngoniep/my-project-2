package com.camunda.demo.Model;


import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReasonModel {
    String code;
    String description;
}
