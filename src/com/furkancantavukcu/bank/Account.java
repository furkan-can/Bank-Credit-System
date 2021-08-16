package com.furkancantavukcu.bank;

import java.util.Date;

public class Account {
	
	private int accountID;
	private Customer customer;
	private String branchNo;
	private String accountNo;
	private String accountLetter;
	private int accountType;
	private Date accountOpenDate;
	
	private AccountInfo accountInfo;
	
	public Account(int accountID, Customer customer, String branchNo, String accountNo, String accountLetter,
			int accountType, Date accountOpenDate) {
		this(accountID,customer,branchNo,accountNo,accountType,accountOpenDate);
		this.accountLetter = accountLetter;
		customer.setAccount(this);
	}
	
	public Account(int accountID, Customer customer, String branchNo, String accountNo,
			int accountType, Date accountOpenDate) {
		this.accountID = accountID;
		this.customer = customer;
		this.branchNo = branchNo;
		this.accountNo = accountNo;
		this.accountType = accountType;
		this.accountOpenDate = accountOpenDate;
		customer.setAccount(this);
	}

	public AccountInfo getAccountInfo() {
		return accountInfo;
	}

	public void setAccountInfo(AccountInfo accountInfo) {
		this.accountInfo = accountInfo;
	}

	public int getAccountID() {
		return accountID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
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
	
	
	
	
	

}
