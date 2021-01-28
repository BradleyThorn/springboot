package com.assignments.assignment5.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CDOffering")
public class CDOffering {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "offering_id")
	Integer id;
	
	int term; 
	int interestRate; 
	int balance; 
	String dateOpened; 
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cdOffering", fetch = FetchType.LAZY)
	private List<CDAccount> cDAccounts;
	
	public CDOffering() {
		this.term = 0; 
		this.interestRate = 0;
		this.balance = 0;
		this.dateOpened = "";
	}

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public int getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(int interestRate) {
		this.interestRate = interestRate;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public String getDateOpened() {
		return dateOpened;
	}

	public void setDateOpened(String dateOpened) {
		this.dateOpened = dateOpened;
	}
}