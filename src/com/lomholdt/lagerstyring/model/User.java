package com.lomholdt.lagerstyring.model;

import java.util.Set;

public class User {
	
	private int id;
	private int companyId;
	private String username;
	private Set<String> roles;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRole(Set<String> roles) {
		this.roles = roles;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
}
