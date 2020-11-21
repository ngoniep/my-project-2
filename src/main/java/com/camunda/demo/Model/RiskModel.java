package com.camunda.demo.Model;

import lombok.*;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RiskModel {

    XMLGregorianCalendar date;
    String riskGrade;
    BigDecimal probabilityToDefault;
    List<ReasonModel> reasonModelList;
    BigDecimal riskScore;
    String trend;

}
