package com.camunda.demo.Model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public  class ReportRequestDTO {
    String idNumber;
    boolean customerConsent;
    String idType;
    String reasonText;
    String inquiryReasons;
    String subjectType;
    List<String> sections;
}
