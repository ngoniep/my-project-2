package com.camunda.demo.Model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashBoardModel {

    RiskModel cip;
    List<CollateralModel> collateralModels;
    List<DisputesModel> disputesModels;
    List<IndividualModel> individualModels;
    Integer numberOfInvolvement;
    Integer numberOfRelations;
    AmountModel installmentSum;
    Integer numberOfDifferentSubscribers;
    AmountModel outstandingAmountSum;
    AmountModel pastDueAmountSum;
    Integer worstDueDaysCurrent;
    Integer worstPastDueDaysForLast12Months;


}
