package com.assignments.assignment5.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "CheckingAccount")
public class CheckingAccount {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

	@ManyToOne
	@JoinColumn(name = "accountHolder_id") 
	@JsonIgnore
	private AccountHolder accountHolder;
	
	long checkingAccountNumber;
	@Min(value = 0, message = "balance must be a positive number")
	double balance;
	double interestRate = .0001;
	String dateOpened;
	static int nextCheckingAccountNumber = 1;
	
	public CheckingAccount() {
		this.checkingAccountNumber = nextCheckingAccountNumber++;
		this.balance = 0;
		this.interestRate = .0001;
		this.dateOpened = "12/6/2020";
	}
	
	
	public AccountHolder getAccountHolder() {
		return accountHolder;
	}


	public void setAccountHolder(AccountHolder accountHolder) {
		this.accountHolder = accountHolder;
	}


	public long getAccountNumber() {
		return checkingAccountNumber;
	}

	public void setAccountNumber(long checkingAccountNumber) {
		this.checkingAccountNumber = checkingAccountNumber;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public double getInterestRate() {
		return interestRate;
	}

	public void setInterest(double interestRate) {
		this.interestRate = interestRate;
	}

	public String getDate() {
		return dateOpened;
	}

	public void setDate(String date) {
		this.dateOpened = date;
	}



}