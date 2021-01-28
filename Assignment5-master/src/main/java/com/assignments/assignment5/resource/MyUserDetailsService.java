package com.assignments.assignment5.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.assignments.assignment5.models.BankUser;
import com.assignments.assignment5.repository.BankUserRepository;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	BankUserRepository bankUserRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		BankUser u = bankUserRepository.findByUsername(userName);
		return u;
		//return new User("bradmin", "bradmin", new ArrayList<>());
	}
	
	

}
