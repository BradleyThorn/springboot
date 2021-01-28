package com.assignments.assignment5.controllers;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.assignments.assignment5.models.AccountHolder;
import com.assignments.assignment5.models.AccountHoldersContactDetails;
import com.assignments.assignment5.models.AuthenticationRequest;
import com.assignments.assignment5.models.AuthenticationResponse;
import com.assignments.assignment5.models.BankUser;
import com.assignments.assignment5.models.CDAccount;
import com.assignments.assignment5.models.CDOffering;
import com.assignments.assignment5.models.CheckingAccount;
import com.assignments.assignment5.models.SavingsAccount;
import com.assignments.assignment5.repository.AccountHolderRepository;
import com.assignments.assignment5.repository.AccountHoldersContactDetailsRepository;
import com.assignments.assignment5.repository.BankUserRepository;
import com.assignments.assignment5.repository.CDAccountRepository;
import com.assignments.assignment5.repository.CDOfferingRepository;
import com.assignments.assignment5.repository.CheckingAccountRepository;
import com.assignments.assignment5.repository.SavingAccountRepository;
import com.assignments.assignment5.resource.JwtUtil;
import com.assignments.assignment5.resource.MyUserDetailsService;

import Exceptions.AccountNotFoundException;
import Exceptions.NegativeBalanceException;
import Exceptions.ExceedsCombinedBalanceLimitException;
import Exceptions.InterestRateOutOfBoundsException;

@RestController
public class MeritBankController {
	@Autowired
	AccountHolderRepository accountHolderRepository;
	@Autowired
	AccountHoldersContactDetailsRepository accountHoldersContactDetailsRepository;
	@Autowired
	CDOfferingRepository cdOfferingRepository;
	@Autowired
	CDAccountRepository cdAccountRepository;
	@Autowired
	CheckingAccountRepository checkingAccountRepository;
	@Autowired
	SavingAccountRepository savingsAccountRepository;
	@Autowired
	BankUserRepository bankUserRepository;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private MyUserDetailsService userDetailsService;
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
		try {
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
	@PostMapping(value = "/authenticate/createUser")
	public BankUser createBankUser(@RequestBody BankUser bankUser)
	{
		return bankUserRepository.save(bankUser);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/ContactDetails/{id}")
	public AccountHoldersContactDetails postContactDetails(@Valid @RequestBody AccountHoldersContactDetails contactDetails, @PathVariable int id)
	throws AccountNotFoundException{
		
		AccountHolder ah = getAccountHolderById(id);
		contactDetails.setAccountHolder(ah);
		AccountHoldersContactDetails ahcd = accountHoldersContactDetailsRepository.save(contactDetails);
		return ahcd;
		
	}
	
	@GetMapping(value = "/ContactDetails")
	public List<AccountHoldersContactDetails> getAccountHoldersContactDetails(){
		return accountHoldersContactDetailsRepository.findAll();
	}
	
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/AccountHolders")
	public AccountHolder postAccountHolder(@Valid @RequestBody AccountHolder accountHolder) {
		//AccountHolder ah = accountHolderRepository.save(accountHolder);
		return accountHolderRepository.save(accountHolder);
		
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/AccountHolders")
	public List<AccountHolder> getAccountHolders(){
		return accountHolderRepository.findAll();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/AccountHolders/{id}")
	public AccountHolder getAccountHolderById(@PathVariable int id) throws AccountNotFoundException {
		//how to pull info from the db
		AccountHolder ah = accountHolderRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("ID does not exist"));
		return ah;
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/AccountHolders/{id}/CheckingAccounts")
	public CheckingAccount postCheckingAccount(@Valid @RequestBody CheckingAccount checkingAccount, @PathVariable int id) throws NegativeBalanceException, ExceedsCombinedBalanceLimitException, AccountNotFoundException {
		AccountHolder ah = accountHolderRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("ID does not exist"));
		if(ah.getCombinedBalance() + checkingAccount.getBalance() > 250000)
		{
			throw new ExceedsCombinedBalanceLimitException("Balance exceeds limit");
		}
		if(checkingAccount.getBalance() <= 0)
		{
			throw new NegativeBalanceException("Balance must be positive");
		}
		checkingAccount.setAccountHolder(ah);
		ah.addCheckingAccount(checkingAccount);
		CheckingAccount chk = checkingAccountRepository.save(checkingAccount);
		return chk;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/AccountHolders/{id}/CheckingAccounts")
	public List<CheckingAccount> getCheckingAccountsById(@PathVariable int id) throws AccountNotFoundException {
		AccountHolder ah = accountHolderRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("ID does not exist"));
		return ah.getCheckingAccounts();
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/AccountHolders/{id}/SavingsAccounts")
	public SavingsAccount postSavingsAccount(@Valid @RequestBody SavingsAccount savingsAccount, @PathVariable int id) throws NegativeBalanceException, ExceedsCombinedBalanceLimitException, AccountNotFoundException {
		AccountHolder ah = accountHolderRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("ID does not exist"));
		if(ah.getCombinedBalance() + savingsAccount.getBalance() > 250000)
		{
			throw new ExceedsCombinedBalanceLimitException("Balance exceeds limit");
		}
		if(savingsAccount.getBalance() <= 0)
		{
			throw new NegativeBalanceException("Balance must be positive");
		}
		savingsAccount.setAccountHolder(ah);
		ah.addSavingsAccount(savingsAccount);
		SavingsAccount sav = savingsAccountRepository.save(savingsAccount);
		return sav;
	}
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/AccountHolders/{id}/SavingsAccounts")
	public List<SavingsAccount> getSavingsAccountsById(@PathVariable int id) throws AccountNotFoundException{
		AccountHolder ah = accountHolderRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("ID does not exist"));
		return ah.getSavingsAccounts();
				
	}
	
	@PostMapping(value = "/AccountHolders/{id}/CDAccounts")
	public CDAccount postCDAccoutnt(@Valid @RequestBody CDAccount cdAccount, @PathVariable int id) throws NegativeBalanceException, ExceedsCombinedBalanceLimitException, InterestRateOutOfBoundsException, AccountNotFoundException {
		AccountHolder ah = accountHolderRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("ID does not exist"));
		if(ah.getCombinedBalance() + cdAccount.getBalance() > 250000)
		{
			throw new ExceedsCombinedBalanceLimitException("Balance exceeds limit");
		}
		if(cdAccount.getBalance() <= 0)
		{
			throw new NegativeBalanceException("Balance must be positive");
		}
		cdAccount.setAccountHolder(ah);
		ah.addCDAccount(cdAccount);
		CDAccount cda = cdAccountRepository.save(cdAccount);
		return cda;
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@GetMapping(value = "/AccountHolders/{id}/CDAccounts")
	public List<CDAccount> getCDAccountsbyId(@PathVariable int id) throws AccountNotFoundException{
		AccountHolder ah = accountHolderRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("ID does not exist"));
		return ah.getCDAccounts();
	}
	

	@GetMapping(value = "/Me")
	public AccountHolder getMyAccountHolder(Authentication auth)
	{
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) auth;
		BankUser u = (BankUser)usernamePasswordAuthenticationToken.getPrincipal();
		AccountHolder ah = u.getAccountHolder();
		return ah;
	}
	
	@PostMapping (value = "/Me/CheckingAccounts")
	public CheckingAccount postMyCheckingAccount(@RequestBody CheckingAccount checkingAccount, Authentication auth) throws ExceedsCombinedBalanceLimitException, NegativeBalanceException
	{
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) auth;
		BankUser u = (BankUser)usernamePasswordAuthenticationToken.getPrincipal();
		AccountHolder ah = u.getAccountHolder();
		if(ah.getCombinedBalance() + checkingAccount.getBalance() > 250000)
		{
			throw new ExceedsCombinedBalanceLimitException("Balance exceeds limit");
		}
		if(checkingAccount.getBalance() <= 0)
		{
			throw new NegativeBalanceException("Balance must be positive");
		}
		checkingAccount.setAccountHolder(ah);
		ah.addCheckingAccount(checkingAccount);
		CheckingAccount chk = checkingAccountRepository.save(checkingAccount);
		return chk;
		
	}
	
	@GetMapping (value = "/Me/CheckingAccounts")
	public List<CheckingAccount> getMyCheckingAccounts(Authentication auth)
	{
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) auth;
		BankUser u = (BankUser)usernamePasswordAuthenticationToken.getPrincipal();
		AccountHolder ah = u.getAccountHolder();
		return ah.getCheckingAccounts();
	}
	
	@PostMapping (value = "/Me/SavingsAccount")
	public SavingsAccount postMySavingsAccount(@RequestBody SavingsAccount savingsAccount, Authentication auth) throws ExceedsCombinedBalanceLimitException, NegativeBalanceException
	{
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) auth;
		BankUser u = (BankUser)usernamePasswordAuthenticationToken.getPrincipal();
		AccountHolder ah = u.getAccountHolder();
		if(ah.getCombinedBalance() + savingsAccount.getBalance() > 250000)
		{
			throw new ExceedsCombinedBalanceLimitException("Balance exceeds limit");
		}
		if(savingsAccount.getBalance() <= 0)
		{
			throw new NegativeBalanceException("Balance must be positive");
		}
		savingsAccount.setAccountHolder(ah);
		ah.addSavingsAccount(savingsAccount);
		SavingsAccount sav = savingsAccountRepository.save(savingsAccount);
		return sav;
	}
	
	@GetMapping (value = "/Me/SavingsAccount")
	public List<SavingsAccount> getMySavingsAccounts(Authentication auth)
	{
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) auth;
		BankUser u = (BankUser)usernamePasswordAuthenticationToken.getPrincipal();
		AccountHolder ah = u.getAccountHolder();
		return ah.getSavingsAccounts();
	}
	
	@PostMapping (value = "/Me/CDAccounts")
	public CDAccount postMyCDAccount(@RequestBody CDAccount cdAccount, Authentication auth) throws ExceedsCombinedBalanceLimitException, NegativeBalanceException
	{
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) auth;
		BankUser u = (BankUser)usernamePasswordAuthenticationToken.getPrincipal();
		AccountHolder ah = u.getAccountHolder();
		if(ah.getCombinedBalance() + cdAccount.getBalance() > 250000)
		{
			throw new ExceedsCombinedBalanceLimitException("Balance exceeds limit");
		}
		if(cdAccount.getBalance() <= 0)
		{
			throw new NegativeBalanceException("Balance must be positive");
		}
		cdAccount.setAccountHolder(ah);
		ah.addCDAccount(cdAccount);
		CDAccount cda = cdAccountRepository.save(cdAccount);
		return cda;
	}
	
	@GetMapping (value = "/Me/CDAccounts")
	public List<CDAccount> getMyCDAccounts(Authentication auth)
	{
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) auth;
		BankUser u = (BankUser)usernamePasswordAuthenticationToken.getPrincipal();
		AccountHolder ah = u.getAccountHolder();
		return ah.getCdAccounts();
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = " /CDOfferings")
	public CDOffering postCDOffering(@Valid @RequestBody CDOffering cdOffering) {
		CDOffering cdo = cdOfferingRepository.save(cdOffering);
		return cdo;
	}
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/CDOfferings")
	public List<CDOffering> getCDOfferings(){
		return cdOfferingRepository.findAll();
	}
}
