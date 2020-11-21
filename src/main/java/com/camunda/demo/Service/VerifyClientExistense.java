package com.camunda.demo.Service;

import com.camunda.demo.config.Connections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class VerifyClientExistense {

    @Value("${configurations.flexDRDBUserName:FCPREPROD}")
    private String flexDBUserName;
    @Value("${configurations.flexDRDBPword:welcome1*}")
    private String flexDBPword;
    @Value("${configurations.flexDRDBPort:1521}")
    private String flexDBPort;
    @Value("${configurations.flexDRDBSID:FBCLIVE1}")
    private String flexDBSID;
    @Value("${configurations.flexDRDBsName:10.170.5.200}")
    private String flexDBsName;

    @Value("${configurations.postilionServerName:10.170.5.131}")
    private String postilionServerName;
    @Value("${configurations.postilionUserName:sa}")
    private String postilionUserName;
    @Value("${configurations.postilionPortNumber:1433}")
    private String postilionPortNumber;
    @Value("${configurations.postilionDatabaseName:postcard}")
    private String postilionDatabaseName;
    @Value("${configurations.postilionPassword:Password123}")
    private String postilionPassword;

    /*
    *
    * 10.170.5.105
    FBCLIVE
    1522*/
    public Optional<String> verifyClientExists(String idNumber, String phoneNumber, String accountRequested) throws Exception {


        if (postilionServerName == null) {
            postilionServerName = "10.170.5.131";
        }
        if (postilionUserName == null) {
            postilionUserName = "sa";
        }
        if (postilionPortNumber == null) {
            postilionPortNumber = "1433";
        }
        if (postilionPassword == null) {
            postilionPassword = "Password123";
        }

        if (postilionDatabaseName == null) {
            postilionDatabaseName = "postcard";
        }
        String twelveDigitIDNumber = "";
        String elevenDigitIdNumber = "";
        idNumber = idNumber.replaceAll(" ", "").replaceAll("-", "");
        String actualId = idNumber.substring(2, idNumber.length() - 2);

        Connections connections = new Connections();
        if (idNumber.length() == 11) {

            elevenDigitIdNumber = idNumber;
            twelveDigitIDNumber = idNumber.substring(0, 2) + "0" + idNumber.substring(3);
        } else {
            twelveDigitIDNumber = idNumber;
            if (idNumber.charAt(2) == '0') {
                elevenDigitIdNumber = idNumber.substring(0, 2) + idNumber.substring(4);
            }
        }
        if (actualId.charAt(0) == '0') {
            actualId = actualId.substring(1);
        }
        try {
            System.out.println("Original ID Number -> " + idNumber + " Twelve Digit ID Number is -> " + twelveDigitIDNumber + " Eleven Digit ID Number -> " + elevenDigitIdNumber);
            Connection connections1 = connections.getSQLServerConnection(postilionServerName, postilionPortNumber, postilionDatabaseName, postilionUserName, postilionPassword);
            Statement statement = connections1.createStatement();
            String sqlStatement = "select  c.account_id account_number,a.*,b.hold_rsp_code,b.*,c.* from pc_customers_2_A a\n" +
                "join\n" +
                "pc_cards_2_A b (nolock) on a.customer_id=b.customer_id\n" +
                "join  pc_card_accounts_2_A c (nolock) on b.pan=c.pan\n" +
                "where ((upper(replace(replace(national_id_nr,' ',''),'-',''))='" + twelveDigitIDNumber + "' " +
                "or(upper(replace(replace(national_id_nr,' ',''),'-','')) like '%" + actualId + "%' " +
                "or mobile_nr='" + phoneNumber + "')))\n" +
                " and hold_rsp_code is null";
            System.out.println(sqlStatement);
            ResultSet rs = statement.executeQuery(sqlStatement);
            int i = 0;
            while (rs.next()) {
                //To Specify the Name of the
                if (accountRequested.contains("KycLiteAccount") && i == 0)
                    return Optional.of(rs.getString("account_number"));
                if (accountRequested.contains("wallet") && i != 0)
                    return Optional.of(rs.getString("account_number"));
                i++;

            }
        }catch(Exception ignored){

        }
        //connections1.
        return Optional.empty();
    }

    public Optional<String> existingCustomerNumber(String idNumber, String phoneNumber, String accountRequested, String accountCurrency) throws Exception {
        if (flexDBUserName == null) {
            flexDBUserName = "FCPREPROD";
        }
        if (flexDBPword == null) {
            flexDBPword = "welcome1*";
        }
        if (flexDBPort == null) {
            flexDBPort = "1522";
        }
        if (flexDBsName == null) {
            flexDBsName = "10.170.5.105";
        }
        if (flexDBSID == null) {
            flexDBSID = "FBCLIVE1";
        }


        String actualId = idNumber.substring(2, idNumber.length() - 2);
        if (actualId.startsWith("0")) {
            actualId = actualId.replaceFirst("0", "");
        }

        System.out.println(actualId);

        try {
            System.out.println("Flexcube connections ->  " + flexDBsName + " " + flexDBPort + " " + flexDBSID + " " + flexDBUserName + " " + flexDBPword);
            Connections connections = new Connections();
            Connection connection = connections.getOracleConnection(flexDBsName, flexDBPort, flexDBSID, flexDBUserName, flexDBPword);
            Statement statement = connection.createStatement();
            String sqlStatement = "SELECT * FROM STTM_CUSTOMER A\n" +
                "    JOIN STTM_CUST_PERSONAL B ON  A.CUSTOMER_NO=B.CUSTOMER_NO\n" +
                "WHERE REPLACE(P_NATIONAL_ID,' ','') LIKE '%" + actualId + "%'\n" +
                "AND RECORD_STAT='O'\n" +
                "AND LOCAL_BRANCH LIKE '0%' AND LOCAL_BRANCH !='000'";
            System.out.println(sqlStatement);
            ResultSet rs = statement.executeQuery(sqlStatement);
            int i = 0;
            while (rs.next()) {
                //To Specify the Name of the
                return Optional.of(rs.getString("CUSTOMER_NO"));

            }
        } catch (Exception e) {

        }
        //connections1.
        return Optional.empty();
    }

    public Optional<String> flexcube(String idNumber, String phoneNumber, String accountRequested, String accountCurrency) throws Exception {
        if (flexDBUserName == null) {
            flexDBUserName = "FCPREPROD";
        }
        if (flexDBPword == null) {
            flexDBPword = "welcome1*";
        }
        if (flexDBPort == null) {
            flexDBPort = "1522";
        }
        if (flexDBsName == null) {
            flexDBsName = "10.170.5.105";
        }
        if (flexDBSID == null) {
            flexDBSID = "FBCLIVE1";
        }


        if (accountRequested.toUpperCase().contains("CURRENT")) {
            accountRequested = "U";
        }
        if (accountRequested.toUpperCase().contains("SAVINGS")) {
            accountRequested = "S";
        }
        String actualId = idNumber.substring(2, idNumber.length() - 2);
        if (actualId.charAt(0) == '0') {
            actualId = actualId.substring(1);
        }

        if (accountRequested.equalsIgnoreCase("U") || accountRequested.equalsIgnoreCase("S")) {
            System.out.println("Flexcube connections ->  " + flexDBsName + " " + flexDBPort + " " + flexDBSID + " " + flexDBUserName + " " + flexDBPword);
            Connections connections = new Connections();
            Connection connection = connections.getOracleConnection(flexDBsName, flexDBPort, flexDBSID, flexDBUserName, flexDBPword);
            Statement statement = connection.createStatement();
            String sqlStatement = "SELECT * FROM STTM_CUST_ACCOUNT A JOIN\n" +
                "                    STTM_CUST_PERSONAL B ON  CUST_NO=CUSTOMER_NO\n" +
                "                        JOIN STTM_ACCOUNT_CLASS C ON A.ACCOUNT_CLASS=C.ACCOUNT_CLASS\n" +
                "                        WHERE A.RECORD_STAT!='C' " +
                "AND C.AC_CLASS_TYPE ='" + accountRequested + "' " +
                "AND A.CCY='" + accountCurrency + "' " +
                "AND (UPPER(REPLACE(REPLACE(P_NATIONAL_ID,' ',''),'-','')) like '%" + actualId + "%' " +
                "OR UPPER(REPLACE(REPLACE(TELEPHONE,' ',''),'+',''))='" + phoneNumber + "' " +

                "OR UPPER(REPLACE(REPLACE(MOBILE_NUMBER,' ',''),'+',''))='" + phoneNumber + "')";
            System.out.println(sqlStatement);
            ResultSet rs = statement.executeQuery(sqlStatement);
            int i = 0;
            while (rs.next()) {
                //To Specify the Name of the
                return Optional.of(rs.getString("CUST_AC_NO"));

            }
            //connections1.
            return Optional.empty();
        }
        return Optional.empty();
    }

}
