package com.camunda.demo.Model;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MastercardCustomer {

    @Builder.Default
    private String username="";

    @Builder.Default
    private String password="";

    @Builder.Default
    private String idType="";

    @Builder.Default
    private String idNumber="";

    @Builder.Default
    private String idExpiry="";

    @Builder.Default
    private String title="";

    @Builder.Default
    private String fname="";

    @Builder.Default
    private String lname="";

    @Builder.Default
    private String initial="";

    @Builder.Default
    private String nationality="";

    @Builder.Default
    private String add1="";

    @Builder.Default
    private String add2="";

    @Builder.Default
    private String add3="";

    @Builder.Default
    private String add4="";

    @Builder.Default
    private String postal="";

    @Builder.Default
    private String country="";

    @Builder.Default
    private String tel="";

    @Builder.Default
    private String mob="";

    @Builder.Default
    private String motherMName="";

    @Builder.Default
    private String email="";

    @Builder.Default
    private String areacode="";

    @Builder.Default
    private String dob="";

    @Builder.Default
    private String gender="";

    @Builder.Default
    private String ban="";

    @Builder.Default
    private String branch="";

    @Builder.Default
    private String agency="";
}

