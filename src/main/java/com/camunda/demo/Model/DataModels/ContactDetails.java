package com.camunda.demo.Model.DataModels;

import com.camunda.demo.CamundaApplication;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "contact_details")
public class ContactDetails {
    @Id
    public Long id;
    private CamundaApplication.CONTACT_TYPE contact_type;
    private String phoneNumbers;
    private String emailAddresses;
    private String homeAddress;


}
