package com.assignments.assignment5.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.assignments.assignment5.models.BankUser;

public interface BankUserRepository extends JpaRepository<BankUser, Integer>{
	
	public BankUser findByUsername(String username);

}
