package com.camunda.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomReportParamsModel {
    String idNumber;
    String idNumberType;
    String inquiryReason;
    String inquiryReasonText;
    XMLGregorianCalendar reportDate;
    List<String> sections;
    String subjectType;


}
