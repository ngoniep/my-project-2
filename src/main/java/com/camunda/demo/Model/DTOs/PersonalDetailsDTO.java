package com.camunda.demo.Model.DTOs;


import com.camunda.demo.Model.ProductApplication;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Builder
//@Entity
//@Table(schema = "digital_onboarding",name = "personal_details")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonalDetailsDTO {

    @Id
    @GeneratedValue
    String id;
    String idNumber;
    String userName;
    String firstName;
    String lastName;
    String dateOfBirth;
    String middleName;
    String gender;
    boolean isLivePerson;
    String idImageBase64String;
    String selfieBase64String;
    String backOfIdImageBase64String;
    String proofOfResidenceBase64String;
    String signatureBase64String;
    String customerIdNumber;
    String primaryPhoneNumber;

    @OneToMany
    List<ContactDetails> contactDetails;

    @OneToMany
    List<EmploymentDetails> employmentDetails;



    @OneToMany
    List<Address> addresses;


    @OneToMany
    List<NextOfKin> nextOfKins;

    @OneToMany
    List<ProductApplication> applications;


}
