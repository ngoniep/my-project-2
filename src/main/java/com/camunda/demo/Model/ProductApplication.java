package com.camunda.demo.Model;


import com.camunda.demo.CamundaApplication;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductApplication {
    @Id
    @GeneratedValue
    String id;
    String applicantUsername;
    String productAppliedFor;
    String nameOfProductAppliedFor;
    Loan loan;
    Account account;
    CamundaApplication.APPLICATION_STATUS applicationStatus;
    String applicationStatusDescription;
    double percentageCompletion;
    String applicantIdNumber;
    Timestamp dateCreated;
    Map<String,String> customFields;
    Map<String,String> customValues;
    String productId;
    String productCode;
    private boolean isDeleted;

}
