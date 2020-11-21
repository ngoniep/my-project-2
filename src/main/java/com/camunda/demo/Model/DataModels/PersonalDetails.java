package com.camunda.demo.Model.DataModels;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "personal_details")
public class PersonalDetails {
    @Id
    private Long id;
    private String idNumber;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String gender;
    private String maritalStatus;
    @ToString.Exclude
    private String idDocumentBase64String;
    @ToString.Exclude
    private String selfieBase64String;

}
