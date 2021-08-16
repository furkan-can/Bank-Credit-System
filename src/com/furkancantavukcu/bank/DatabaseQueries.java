package com.furkancantavukcu.bank;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseQueries extends DatabaseConnect {

	public DatabaseQueries() {
		super();
	}

	PreparedStatement ps;

	public void insertData(Customer customer) {

		String query = "INSERT INTO customer VALUES ('" + customer.getPersonalID() + "','" + customer.getName() + "','"
				+ customer.getLastName() + "','" + customer.getHometown() + "')";
		try {

			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertData(Account account) {
		String query = "";

		Date accountOpenDateSQL = new Date(account.getAccountOpenDate().getTime());

		if (account.getAccountType() == 0) {
			query = "INSERT INTO `account` (`AccountID`, `PersonelID`, `BranchNo`, `AccountNo`, `AccountType`, `AccountOpenDate`) VALUES ('"
					+ account.getAccountID() + "','" + account.getCustomer().getPersonalID() + "','"
					+ account.getBranchNo() + "','" + account.getAccountNo() + "','" + account.getAccountType() + "','"
					+ accountOpenDateSQL + "');";
		} else {
			query = "INSERT INTO `account` (`AccountID`, `PersonelID`, `BranchNo`, `AccountNo`, `AccountLetter`, `AccountType`, `AccountOpenDate`) VALUES ('"
					+ account.getAccountID() + "','" + account.getCustomer().getPersonalID() + "','"
					+ account.getBranchNo() + "','" + account.getAccountNo() + "','" + account.getAccountLetter()
					+ "','" + account.getAccountType() + "','" + accountOpenDateSQL + "')";
		}

		try {

			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertData(AccountInfo accountInfo) {

		Date maturityStartDateSQL = new Date(accountInfo.getMaturityStartDate().getTime());
		Date maturityDeadlineSQL = new Date(accountInfo.getMaturityDeadline().getTime());
		Date balanceUsageDateSQL = new Date(accountInfo.getBalanceUsageDate().getTime());

		String query = "INSERT INTO `accountinfo`(`FileNo`, `AccountID`, `MaturityStartDate`, `MaturityDeadline`, `MaxLimit`, `InterestRate`, `InterestPeriod`,`BalanceUsageDate`) VALUES ('"
				+ accountInfo.getFileNo() + "','" + accountInfo.getAccount().getAccountID() + "','"
				+ maturityStartDateSQL + "','" + maturityDeadlineSQL + "','" + accountInfo.getLimit() + "','"
				+ accountInfo.getInterestRate() + "','" + accountInfo.getInterestPeriod() + "','" + balanceUsageDateSQL
				+ "')";
		try {

			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateData(String fileNo, int newBalance) {
		String query = "UPDATE `accountinfo` SET `BalanceUsed`='" + newBalance + "' WHERE accountinfo.FileNo='" + fileNo
				+ "'";
		try {

			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			ps.close();
			

		} catch (SQLException l) {
			l.printStackTrace();
		}
	}

	public void updateData(double interest, double tax, String fileNo) {
		
		String query = "UPDATE `accountinfo` SET `UsageInterest`='" + Math.ceil(interest) + "', `Tax`='" + Math.ceil(tax)
				+ "' WHERE accountinfo.FileNo='" + fileNo + "'";
		try {

			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			ps.close();

		} catch (SQLException l) {
			l.printStackTrace();
		}
	}
}
