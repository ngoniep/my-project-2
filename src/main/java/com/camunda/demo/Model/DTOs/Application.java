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
public class Application {
    private  AccountDetails accountDetails;
    private  ContactDetails contactDetails;
    private List<EmploymentDetails> employmentDetails;
    private  List<NextOfKin> nextOfKin;
    private PersonalDetails personalDetails;
    private PersonalDetails personalDetailsOcr;
    private boolean livePerson;
    private String applicationStatus;


}
