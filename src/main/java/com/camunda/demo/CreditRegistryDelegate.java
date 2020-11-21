package com.camunda.demo;

import com.camunda.demo.BusinessModels.PersonDTO;
import com.camunda.demo.Model.*;
import com.camunda.demo.Model.DTOs.CreateAccountResponse;
import com.camunda.demo.Service.CreditRegistryBureauService;
import com.camunda.demo.Service.GatewayServiceFeign;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
public class CreditRegistryDelegate implements JavaDelegate {
    @Autowired
    GatewayServiceFeign.ProductApplicationClient productApplicationClient;


    CreditRegistryBureauService creditRegistryBureauService = new CreditRegistryBureauService();

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        System.out.print("Credit Registry attempted here");
        ScoringReportModel scoringReportModel=ScoringReportModel.builder().build();
        try {
             scoringReportModel = creditRegistryBureauService.getCreditRegistryReport(delegateExecution.getVariable("requestNationalID").toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        Date latestRiskGradingDate=new Date();
        String latestRiskGrade="";
        BigDecimal latestProbabilityToDefault=BigDecimal.valueOf(0);
        String listOfReasons = "";
        BigDecimal latestRiskScore=BigDecimal.valueOf(0);
        String latestTrend="";
        String crbSurname="";
        String crbCitizenship="";
        String crbCountryOfBirth="";
        Date crbDateOfBirth=new Date();
        String crbFirstName="";
        String crbFullName="";
        String crbGender="";
        String crbMaritalStatus="";
        String crbMiddleName="";
        String crbPresentSurname="";
        String crbCurrentEmployer="";
        String crbHighestEducationAchieved="";
        String crbIncomeCurrency="";
        BigDecimal crbIncomeLocalCurrency=BigDecimal.valueOf(0);
        BigDecimal crbIncome=BigDecimal.valueOf(0);
        String crbJobPosition="";
        BigDecimal crbMonthlyExpenses=BigDecimal.valueOf(0);
        String crbPrimaryAddress="";
        String mobileNumber="";
        String businessLicenseNumber="";
        String driversLicenceNumber="";
        String nationalIDExpiryDate="";
        String nationalIDIssuingAuthority="";
        String nationalIDIssueDate="";
        String passportExpirationDate="";
        String passportIssueAuthority="";
        String passportIssueDate="";
        String passportIssuerCountry="";
        String passportNumber="";
        String previousPassport="";
        String email="";
        String drivingLicenseIssuerAuthority="";

        BigDecimal outstandingAmount = BigDecimal.valueOf(0);
try {
    for (ContractModel contractModel : scoringReportModel.getContractModelList()) {
        if (contractModel.getPhaseOfContract().equals("Open"))
            outstandingAmount = outstandingAmount.add(contractModel.getOutstandingAmount().getAmount());
    }
    delegateExecution.setVariable("outstandingAmount", outstandingAmount);
    for (RiskModel riskModel : scoringReportModel.getRecordList()) {
        System.out.print("started setting variables");
        //"2020-04-18T22:55:50.132+0000"
        try {
            latestRiskGradingDate = new SimpleDateFormat("yyyy-MM-dd").parse(riskModel.getDate().toString().substring(0, 10));
        } catch (Exception e) {
            latestRiskGradingDate = new Date();/**/
        }



        latestRiskGrade = riskModel.getRiskGrade();
        businessLicenseNumber=scoringReportModel.getIndividualModel().getEmploymentInformation().getCurrentEmployer();
        mobileNumber=scoringReportModel.getIndividualModel().getContactModel().getMobileNumber();
        crbCitizenship = scoringReportModel.getIndividualModel().getIdentityInformation().getCitizenship();
        latestRiskScore = riskModel.getRiskScore();
        latestTrend = riskModel.getTrend();
        email=scoringReportModel.getIndividualModel().getContactModel().getEmail();
        crbFirstName = scoringReportModel.getIndividualModel().getIdentityInformation().getFirstName();
        crbSurname = scoringReportModel.getIndividualModel().getIdentityInformation().getSurname();
        crbCountryOfBirth = scoringReportModel.getIndividualModel().getIdentityInformation().getCountryOfBirth();
        crbDateOfBirth = new SimpleDateFormat("yyyy-mm-dd").parse(scoringReportModel.getIndividualModel().getIdentityInformation().getDateOfBirth().toString().substring(0, 10));
        crbFullName = scoringReportModel.getIndividualModel().getIdentityInformation().getFullName();
        crbGender = scoringReportModel.getIndividualModel().getIdentityInformation().getGender();
        crbMaritalStatus = scoringReportModel.getIndividualModel().getIdentityInformation().getMaritalStatus();
        crbMiddleName = scoringReportModel.getIndividualModel().getIdentityInformation().getMiddleName();
        crbPresentSurname = scoringReportModel.getIndividualModel().getIdentityInformation().getPresentSurname();
        crbCurrentEmployer = scoringReportModel.getIndividualModel().getEmploymentInformation().getCurrentEmployer();
        crbHighestEducationAchieved = scoringReportModel.getIndividualModel().getEmploymentInformation().getHighestEducationAchieved();
        crbIncomeCurrency = scoringReportModel.getIndividualModel().getEmploymentInformation().getIncomeAvailable().getCurrency();
        crbIncomeLocalCurrency = scoringReportModel.getIndividualModel().getEmploymentInformation().getIncomeAvailable().getAmountLocalCurrency();
        crbIncome = scoringReportModel.getIndividualModel().getEmploymentInformation().getIncomeAvailable().getAmount();
        crbJobPosition = scoringReportModel.getIndividualModel().getEmploymentInformation().getJobPosition();
        crbMonthlyExpenses = scoringReportModel.getIndividualModel().getEmploymentInformation().getMonthlyExpenses().getAmountLocalCurrency();
        crbPrimaryAddress = scoringReportModel.getIndividualModel().getPrimaryAddress().toString();
        driversLicenceNumber=scoringReportModel.getIndividualModel().getIdentificationsModel().getDriversLicenseNumber();
        latestProbabilityToDefault = riskModel.getProbabilityToDefault();
      for (ReasonModel reasonModel : riskModel.getReasonModelList()) {
            listOfReasons += " " + reasonModel.getDescription();
        }
      break;

    }
    delegateExecution.setVariable("latestRiskGradingDate", latestRiskGradingDate);
    delegateExecution.setVariable("businessLicenseNumber",businessLicenseNumber);
    delegateExecution.setVariable("mobileNumber",mobileNumber);
    delegateExecution.setVariable("latestRiskGrade", latestRiskGrade);
    delegateExecution.setVariable("latestRiskScore", latestRiskScore);
    delegateExecution.setVariable("latestProbabilityToDefault", latestProbabilityToDefault);
    delegateExecution.setVariable("reasonsToDefault", listOfReasons);
    delegateExecution.setVariable("latestTrend", latestTrend);
    delegateExecution.setVariable("crbSurname", crbSurname);
    delegateExecution.setVariable("crbFirstName", crbFirstName);
    delegateExecution.setVariable("crbCitizenship", crbCitizenship);
    delegateExecution.setVariable("crbCountryOfBirth", crbCountryOfBirth);
    delegateExecution.setVariable("crbDateOfBirth", crbDateOfBirth);
    delegateExecution.setVariable("crbFullName", crbFullName);
    delegateExecution.setVariable("crbGender", crbGender);
    delegateExecution.setVariable("crbMaritalStatus", crbMaritalStatus);
    delegateExecution.setVariable("crbMiddleName", crbMiddleName);
    delegateExecution.setVariable("crbPresentSurname", crbPresentSurname);
    delegateExecution.setVariable("crbCurrentEmployer", crbCurrentEmployer);
    delegateExecution.setVariable("crbHighestEducationAchieved", crbHighestEducationAchieved);
    delegateExecution.setVariable("crbIncomeCurrency", crbIncomeCurrency);
    delegateExecution.setVariable("crbIncomeLocalCurrency", crbIncomeLocalCurrency);
    delegateExecution.setVariable("crbIncome", crbIncome);
    delegateExecution.setVariable("crbJobPosition", crbJobPosition);
    delegateExecution.setVariable("crbMonthlyExpenses", crbMonthlyExpenses);
    delegateExecution.setVariable("crbPrimaryAddress", crbPrimaryAddress);
    delegateExecution.setVariable("email", email);
    delegateExecution.setVariable("mobileNumber", mobileNumber);
    delegateExecution.setVariable("businessLicenseNumber", businessLicenseNumber);
    delegateExecution.setVariable("drivingLicenseIssuerAuthority", driversLicenceNumber);
    delegateExecution.setVariable("nationalId", "");
    delegateExecution.setVariable("nationalIDExpiryDate", nationalIDExpiryDate);
    delegateExecution.setVariable("nationalIDIssuingAuthority", nationalIDIssuingAuthority);
    delegateExecution.setVariable("nationalIDIssueDate", nationalIDIssueDate);
    delegateExecution.setVariable("passportExpirationDate", passportExpirationDate);
    delegateExecution.setVariable("passportIssueAuthority", passportIssueAuthority);
    delegateExecution.setVariable("passportIssueDate", passportIssueDate);
    delegateExecution.setVariable("passportIssuerCountry", passportIssuerCountry);
    delegateExecution.setVariable("passportNumber", passportNumber);
    delegateExecution.setVariable("previousPassport", previousPassport);
    delegateExecution.setVariable("drivingLicenseIssuerAuthority",drivingLicenseIssuerAuthority);


}catch (Exception e){

}



    }
}
