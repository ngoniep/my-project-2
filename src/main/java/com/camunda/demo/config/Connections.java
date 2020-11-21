package com.camunda.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Connections {





	public void executeSQLStatement(Connection conMe, String sql)
			throws Exception {
		Statement stmt = null;
		int rows;

		stmt = conMe.createStatement();
		rows = stmt.executeUpdate(sql);
		stmt.close();

	}

	public Connection getSQLServerConnection(String postilionServerName,String postilionPortNumber,String postilionDatabaseName,String postilionUserName,String postilionPassword) {

		Connection con = null;
		String url = "jdbc:sqlserver://";

		// test
		//final String serverName = "10.170.4.12";
		//final String portNumber = "1433";

		// live
		final String serverName = postilionServerName;
		final String portNumber = postilionPortNumber;

		System.out.println("The following are the postilion parametres -> "+postilionServerName+" "+postilionPortNumber+" "+postilionDatabaseName+" "+postilionPassword);

		final String databaseName = postilionDatabaseName;
		final String userName = postilionUserName;
		final String password = postilionPassword;
		// Informs the driver to use server a side-cursor,
		// which permits more than one active statement
		// on a connection.
		final String selectMethod = "cursor";

		url = url + serverName + ":" + portNumber + ";databaseName="
				+ databaseName + ";selectMethod=" + selectMethod + ";";
		;

		try {

			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(url, userName, password);
			// if (con != null)
			// System.out.println("Connection Successful!");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Trace in getConnection() : "
					+ e.getMessage());
		}
		return con;
	}



	public Connection getOracleConnection(String flexDBsName,String flexDBPort,String flexDBSID,String flexDBUserName,String flexDBPword) throws Exception {

		Connection connection = null;

//        //jdbc:oracle:thin:@10.170.5.105:1522:FBCLIVE
//        flexDBsName="10.170.5.105";
//        flexDBPort="1522";
//        flexDBSID="FBCLIVE1";
		try {
			// Load the JDBC driver
			String driverName = "oracle.jdbc.driver.OracleDriver";
			Class.forName(driverName);

			System.out.println("flexDBsName-> "+flexDBsName+" flexDBPort ->  "+flexDBPort+" flexDBSID + ->"+" flexDBUserName ->"+flexDBUserName+" flexDBPword -> "+flexDBPword);

            // FBCRPTS
            String url = "jdbc:oracle:thin:@" + flexDBsName + ":" + flexDBPort
			 + ":" + flexDBSID;
            System.out.println("connection url -> "+url);
			 //String username = "FBCUBSPROD";
			 //String password = "FBCRPTS";

			connection = DriverManager.getConnection(url, flexDBUserName, flexDBPword);

		} catch (ClassNotFoundException e) {
			// Could not find the database driver
			System.out.println("Could not find the database driver");
		} catch (SQLException e) {
			// Could not connect to the database
			System.out.println(e.getMessage());
			System.out.println("Could not connect to the database");
		}
		return connection;

	}



}
