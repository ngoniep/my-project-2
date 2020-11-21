package com.camunda.demo.Model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmploymentInformation {
    String currentEmployer;
    String highestEducationAchieved;
    AmountModel incomeAvailable;
    String jobPosition;
    AmountModel monthlyExpenses;

}
