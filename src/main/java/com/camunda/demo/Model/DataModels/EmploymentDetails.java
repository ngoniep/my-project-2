package com.camunda.demo.Model.DataModels;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "employment_details")
public class EmploymentDetails {
    @Id
    private Long id;
    private String nameOfEmployer;
    private String currentJobTitle;
    private String employmentType;
    private Date dateJoined;
    private int lengthOfEmployment;
    private String averageMonthlyIncome;
    @OneToMany
    private List<ContactDetails> contactDetails;
    private String workPrimaryContactId ;
    private String workPrimaryFullName ;


}
