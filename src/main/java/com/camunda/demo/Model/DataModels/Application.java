package com.camunda.demo.Model.DataModels;


import lombok.*;

import javax.persistence.*;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "account_application")
public class Application {

    @Id
    @GeneratedValue
    private int id;
    @OneToOne
    private AccountDetails accountDetails;
    @OneToMany
    private List<ContactDetails> contactDetails;
    @OneToMany
    private List<EmploymentDetails> employmentDetails;
    @OneToMany
    private  List<NextOfKin> nextOfKin;
    @OneToOne
    private PersonalDetails personalDetails;
    @OneToOne
    private PersonalDetails personalDetailsOcr;
    private boolean livePerson;
    private String applicationStatus;


}
