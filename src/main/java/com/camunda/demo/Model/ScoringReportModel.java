package com.camunda.demo.Model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoringReportModel {



    private String dateOfLastFraudRegistrationThirdParty;
    private Integer numberOfFraudAlertsPrimarySubject;
    private Integer numberOfFraudAlertsThirdParty;
    List<RiskModel> recordList = new ArrayList<>();
    //String recordList;
    int lostStolenRecordsFound;
    int numberOfSubscribersMadeInquiriesLast14Days;
    int numberOfCancelledClosedContracts;
    int numberOfSubscribersMadeInquiriesLast2Days;

    XMLGregorianCalendar dateOfLastFraudRegistration;
    CompanyModel companyModel;
    SimpleCompanyModel simpleCompanyModel;
    List<ContractModel> contractModelList = new ArrayList<>();//contractModelList
    List<InquiryModel> inquiryModelList = new ArrayList<>();
    //ContractSummary contractSummary;
    ContractSummaryModel contractSummaryModel;
    // CurrentRelations currentRelations;
    RiskModel dashboard;
    List<String> bouncedChequeList;
    //CIP2 cip;
    RiskModel riskModel;
    List<String> branches;
    //Individual individual;
    IndividualModel individualModel;
    List<RelationshipModel> contractRelations = new ArrayList<>();
    IndividualModel individualSimpleModel;
    //Involvements involvements;
    List<InvolvementModel> involvementModels = new ArrayList<>();
    List<LostAndStolenModel> lostAndStolenModelList = new ArrayList<>();
    List<IndividualModel> managers;
    List<ContractModel> negativeContractList = new ArrayList<>();
    List<OtherLiabilityModel> otherLiabilities = new ArrayList<>();
    CustomReportParamsModel customReportParams;
    List<PaymentIncidentModel> paymentIncidentList = new ArrayList<>();
    //ReportInfo reportInfo;
    List<ShareHolderModel> shareholders = new ArrayList<>();
   // SubjectInfoHistory subjectInfoHistory;
    List<IndividualModel> subjectHistory;
    List<SecurityModel> securities = new ArrayList<>();
    List<PaymentCalendarModel> paymentCalendarModelList=new ArrayList<>();




}
