package com.furkancantavukcu.bank;

public class Customer {
	private String personalID;
	private String name;
	private String lastName;
	private String hometown;
	
	private Account account;
	private AccountInfo accountInfo;
	
	

	public Customer(String personalID, String name, String lastName, String hometown) {
		this.personalID = personalID;
		this.name = name;
		this.lastName = lastName;
		this.hometown = hometown;
	}

	
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public AccountInfo getAccountInfo() {
		return accountInfo;
	}

	public void setAccountInfo(AccountInfo accountInfo) {
		this.accountInfo = accountInfo;
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
	
	
	
	
	

}
