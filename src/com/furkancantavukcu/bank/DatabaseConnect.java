package com.furkancantavukcu.bank;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class DatabaseConnect {

	Connection conn = null;
	private String dbUserName;
	private String dbPassword;
	private String databaseName;

	public DatabaseConnect() {
		this.databaseName = "db";
		this.dbPassword = "";
		this.dbUserName = "root";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/" + databaseName, dbUserName, dbPassword);
			System.err.println("Connected ->" + databaseName);

		} catch (ClassNotFoundException e) {
			System.out.println("Not found Connector.");
			System.err.println("Connection Failed\nClassNotFoundException");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(OpeningPage.panel, "Database Connection Failed");
			System.exit(1);
		}
	}

}
