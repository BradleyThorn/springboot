package com.assignments.assignment5.models;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Entity
@Table(name = "BankUser")
public class BankUser implements UserDetails{
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "accountHolder_id")
    private Integer id;
	
	String username;
	String password;
	boolean active;
	String role;
	
	@OneToOne
	@JoinColumn(name = "accountHolder_id", referencedColumnName = "accountHolder_id")
	private AccountHolder accountHolder;
	
	public BankUser()
	{
		this.username = "";
		this.password = "";
		this.active = false;
		this.role = "";
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(this.role));
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return active;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return active;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return active;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return active;
	}

	public AccountHolder getAccountHolder() {
		return accountHolder;
	}

}
