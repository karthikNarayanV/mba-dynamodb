package com.fse.moviebooking.model;

import java.util.Set;

public class LoginReturn {

	private String token;
	private Set<String> roles;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Set<String> getRoles() {
		return roles;
	}
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	public LoginReturn(String token, Set<String> roles) {
		super();
		this.token = token;
		this.roles = roles;
	}
	public LoginReturn() {
		super();
	}
	
	
}
