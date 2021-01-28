package com.assignments.assignment5.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "SavingsAccount")
public class SavingsAccount {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

	@ManyToOne
	@JoinColumn(name = "accountHolder_id") 
	@JsonIgnore
	private AccountHolder accountHolder;
	
	long savingsAccountNumber;
	double balance;
	String dateOpened;
	double interestRate = 0.01;
	static int nextSavingsAccountNumber = 1;
	
	public SavingsAccount() {
		this.balance = 0;
		this.dateOpened = "12/6/2020";
		this.interestRate = 0.01;
		this.savingsAccountNumber = nextSavingsAccountNumber++;
	}

	
	public AccountHolder getAccountHolder() {
		return accountHolder;
	}


	public void setAccountHolder(AccountHolder accountHolder) {
		this.accountHolder = accountHolder;
	}


	public long getAccountNumber() {
		return savingsAccountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.savingsAccountNumber = accountNumber;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getDateOpened() {
		return dateOpened;
	}

	public void setDateOpened(String dateOpened) {
		this.dateOpened = dateOpened;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
}