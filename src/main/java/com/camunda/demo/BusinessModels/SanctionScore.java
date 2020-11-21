package com.camunda.demo.BusinessModels;


import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SanctionScore {
    String nameRequested;
    String surnameRequested;
    Date dateOfBirthRequested;
    List<String> matchDetails;
    Double matchScore;
    Integer typeScore;
}
