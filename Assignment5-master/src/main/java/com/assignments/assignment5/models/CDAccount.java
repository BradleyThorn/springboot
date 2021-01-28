package com.assignments.assignment5.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "CDAccount")

public class CDAccount {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "accountHolder_id") 
	@JsonIgnore
	AccountHolder accountHolder;
	
	long accountNumber;
	double balance;
	String dateOpened;
	@DecimalMin(value = "0.0", message = "Must be greater than 0")
	@DecimalMax(value = "1.0", message = "Must be less than 1")
	double interestRate = 0.025;
	int term;

	@ManyToOne
	@JoinColumn(name = "offering_id") 
	private CDOffering cdOffering;
	
	public CDAccount() {
		this.accountNumber = 0 ;
		this.balance = 0;
		this.dateOpened = "";
		this.interestRate = 0;
		this.term = 0;
	}
	
	
	
	public AccountHolder getAccountHolder() {
		return accountHolder;
	}



	public void setAccountHolder(AccountHolder accountHolder) {
		this.accountHolder = accountHolder;
	}


	public CDOffering getcDOffering() {
		return cdOffering;
	}



	public void setcDOffering(CDOffering cDOffering) {
		this.cdOffering = cDOffering;
	}



	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
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

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}

}