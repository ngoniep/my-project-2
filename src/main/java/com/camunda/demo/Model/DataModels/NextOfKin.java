package com.camunda.demo.Model.DataModels;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "next_of_kin")
public class NextOfKin {
    @Id
    private Long id;
    private String relationship;
    private String fullName;
    private String residentialAddress;
    @OneToMany
    private List<ContactDetails> contactDetails;
    private String idNumber;


}
