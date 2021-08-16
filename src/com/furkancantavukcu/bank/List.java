package com.furkancantavukcu.bank;

import java.util.Date;

public class List{
	
	private String personalID;
	private String name;
	private String lastName;
	private String hometown;
	private String branchNo;
	private String accountNo;
	private String accountLetter;
	private int accountType;
	private Date accountOpenDate;
	private String fileNo;
	private Date maturityStartDate;
	private Date maturityDeadline;
	private int balanceUsed;
	private int limit;
	private double interestRate;
	private Date balanceUsageDate;
	private double usageInterest;
	private double tax;
	private String interestPeriod;
	
	public List(String personalID, String name, String lastName, String hometown, String branchNo, String accountNo,
			String accountLetter, int accountType, Date accountOpenDate, String fileNo, Date maturityStartDate,
			Date maturityDeadline, int balanceUsed, int limit, double interestRate, Date balanceUsageDate,
			double usageInterest, double tax, String interestPeriod) {
		this.personalID = personalID;
		this.name = name;
		this.lastName = lastName;
		this.hometown = hometown;
		this.branchNo = branchNo;
		this.accountNo = accountNo;
		this.accountLetter = accountLetter;
		this.accountType = accountType;
		this.accountOpenDate = accountOpenDate;
		this.fileNo = fileNo;
		this.maturityStartDate = maturityStartDate;
		this.maturityDeadline = maturityDeadline;
		this.balanceUsed = balanceUsed;
		this.limit = limit;
		this.interestRate = interestRate;
		this.balanceUsageDate = balanceUsageDate;
		this.usageInterest = usageInterest;
		this.tax = tax;
		this.interestPeriod = interestPeriod;
	}

	public String getPersonalID() {
		return personalID;
	}

	public void setPersonalID(String personalID) {
		this.personalID = personalID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public String getBranchNo() {
		return branchNo;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountLetter() {
		return accountLetter;
	}

	public void setAccountLetter(String accountLetter) {
		this.accountLetter = accountLetter;
	}

	public int getAccountType() {
		return accountType;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

	public Date getAccountOpenDate() {
		return accountOpenDate;
	}

	public void setAccountOpenDate(Date accountOpenDate) {
		this.accountOpenDate = accountOpenDate;
	}

	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
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
