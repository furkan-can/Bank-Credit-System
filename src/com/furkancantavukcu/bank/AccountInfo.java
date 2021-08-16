package com.furkancantavukcu.bank;

import java.util.Date;

public class AccountInfo {

	private String fileNo;
	private Account account;
	private Date maturityStartDate;
	private Date maturityDeadline;
	private int balanceUsed;
	private int limit;
	private double interestRate;
	private Date balanceUsageDate;
	private double usageInterest;
	private double tax;
	private String interestPeriod;

	public AccountInfo(String fileNo, Account account, Date maturityStartDate, Date maturityDeadline, int limit,
			double interestRate, String interestPeriod , Date balanceUsageDate) {
		this.fileNo = fileNo;
		this.account = account;
		this.maturityStartDate = maturityStartDate;
		this.maturityDeadline = maturityDeadline;
		this.balanceUsed = 0;
		this.limit = limit;
		this.interestRate = interestRate;
		this.balanceUsageDate = balanceUsageDate;
		this.usageInterest = 0;
		this.tax = 0;
		this.interestPeriod = interestPeriod;
		
		account.setAccountInfo(this);	
		account.getCustomer().setAccountInfo(this);
	}
	
	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Date getMaturityStartDate() {
		return maturityStartDate;
	}

	public void setMaturityStartDate(Date maturityStartDate) {
		this.maturityStartDate = maturityStartDate;
	}

	public Date getMaturityDeadline() {
		return maturityDeadline;
	}

	public void setMaturityDeadline(Date maturityDeadline) {
		this.maturityDeadline = maturityDeadline;
	}

	public int getBalanceUsed() {
		return balanceUsed;
	}

	public void setBalanceUsed(int balanceUsed) {
		this.balanceUsed = balanceUsed;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public Date getBalanceUsageDate() {
		return balanceUsageDate;
	}

	public void setBalanceUsageDate(Date balanceUsageDate) {
		this.balanceUsageDate = balanceUsageDate;
	}

	public double getUsageInterest() {
		return usageInterest;
	}

	public void setUsageInterest(double usageInterest) {
		this.usageInterest = usageInterest;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public String getInterestPeriod() {
		return interestPeriod;
	}

	public void setInterestPeriod(String interestPeriod) {
		this.interestPeriod = interestPeriod;
	}

}
