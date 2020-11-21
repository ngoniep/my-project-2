package com.camunda.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyContractModel implements ContractModelInterface {
    String branch;
    List<CollateralModel> collateralModelList;
    String contractCode;
    String creditClassification;
    String currencyOfContract;
    XMLGregorianCalendar dateOfLastPayment;
    XMLGregorianCalendar defaultDate;
    List<DisputesModel> disputesModel;
    String economicSector;
    XMLGregorianCalendar expectedEndDate;
    String negativeStatusOfContract;
    Integer numberOfInstallments;
    AmountModel outstandingAmount;
    AmountModel pastAmount;
    AmountModel pastDueInterest;
    List<CalendarItemModel> paymentCalendarItemModels;
    String paymentPeriodicity;
    String phaseOfContract;
    String purposeOfFinancing;
    XMLGregorianCalendar realEndDate;
    //Related Subject List here
    XMLGregorianCalendar restructuringDate;
    String restructuringReason;
    String roleOfClient;
    XMLGregorianCalendar startDate;
    String subscriber;
    String subscriberType;
    AmountModel totalAmount;
    AmountModel totalMonthlyPayment;
    AmountModel totalAmountTaken;
    String transferStatus;
    String typeOfContract;
    Integer worstPastDueDays;
    //List Of CurrentRelations
    DashBoardModel dashBoardModel;
    List<DisputesModel> disputesModels;
    List<PaymentIncidentModel> paymentIncidentModels;



}
