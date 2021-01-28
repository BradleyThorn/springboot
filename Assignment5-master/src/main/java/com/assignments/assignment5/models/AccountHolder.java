package com.assignments.assignment5.models;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "AccountHolder")
public class AccountHolder {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "accountHolder_id")
	int id;
	
	@NotNull(message = "First Name can not be null")
	@NotBlank(message = "First Name can not be blank")
	String firstName;
	String middleName;
	@NotNull(message = "Last Name can not be null")
	@NotBlank(message = "Last Name can not be blank")
	String lastName;
	@NotNull(message = "SSN can not be null")
	@NotBlank(message = "SSN can not be blank")
	String SSN;
	public int numberOfCheckingAccounts;
	int checkingBalance;
	int numberOfSavingsAccounts;
	int savingsBalance;
	int numberOfCDAccounts;
	int cdbalance;
	public double combinedbalance;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "accountHolder_id", referencedColumnName = "accountHolder_id")
	AccountHoldersContactDetails AHCD;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "accountHolder_id", referencedColumnName = "accountHolder_id")
	BankUser bankUser;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "accountHolder", fetch = FetchType.LAZY)
	List<CheckingAccount> checkingAccounts = new ArrayList<CheckingAccount>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "accountHolder", fetch = FetchType.LAZY)
	List<SavingsAccount> savingsAccounts = new ArrayList<SavingsAccount>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "accountHolder", fetch = FetchType.LAZY)
	List<CDAccount> cdAccounts = new ArrayList<CDAccount>();
	
	
	
	public AccountHolder() {
		this.firstName = "";
		this.middleName = "";
		this.lastName = "";
		this.SSN = "";
		this.numberOfCheckingAccounts = 0;
		this.checkingBalance = 0;
		this.numberOfSavingsAccounts = 0;
		this.savingsBalance = 0;
		this.numberOfCDAccounts = 0;
		this.cdbalance = 0;
		this.combinedbalance = 0;
	}
	
	
	public List<CDAccount> getCdAccounts() {
		return cdAccounts;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSSN() {
		return SSN;
	}

	public void setSSN(String SSN) {
		this.SSN = SSN;
	}
	
	public int getNumberOfCheckingAccounts() {
		return numberOfCheckingAccounts;
	}

	public int getCheckingBalance() {
		int cb = 0;
		for(int x = 0; x < checkingAccounts.size(); x++)
		{
			cb += checkingAccounts.get(x).getBalance();
		}
		return cb;
	}

	public int getNumberOfSavingsAccounts() {
		return numberOfSavingsAccounts;
	}

	public int getSavingsBalance() {
		int cb = 0;
		for(int x = 0; x < savingsAccounts.size(); x++)
		{
			cb += savingsAccounts.get(x).getBalance();
		}
		return cb;
	}

	public int getNumberOfCDAccounts() {
		return numberOfCDAccounts;
	}

	public int getCdbalance() {
		return cdbalance;
	}

	public void setCdbalance(int cdbalance) {
		this.cdbalance = cdbalance;
	}

	public double getCombinedBalance() {
		int cb = 0;
		for(int x = 0; x < checkingAccounts.size(); x++)
		{
			cb += checkingAccounts.get(x).getBalance();
		}
		for(int x = 0; x < savingsAccounts.size(); x++)
		{
			cb += savingsAccounts.get(x).getBalance();
		}
		return cb;
	}

	
	public CheckingAccount addCheckingAccount(CheckingAccount checkingAccount) {
		checkingAccounts.add(checkingAccount);
		numberOfCheckingAccounts++;
		return checkingAccount;
	}
	
	public List<CheckingAccount> getCheckingAccounts() {
		return checkingAccounts;		
	}
	
	public SavingsAccount addSavingsAccount(SavingsAccount savingsAccount){
		savingsAccounts.add(savingsAccount);
		numberOfSavingsAccounts++;
		return savingsAccount;
	}
	
	public List<SavingsAccount> getSavingsAccounts(){
		return savingsAccounts;
	}
	
	public CDAccount addCDAccount(CDAccount cdAccount){
		cdAccounts.add(cdAccount);
		numberOfCDAccounts++;
		return cdAccount;
	}
	
	public List<CDAccount> getCDAccounts(){
		return cdAccounts;
	}
}