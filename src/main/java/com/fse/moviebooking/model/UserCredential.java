package com.fse.moviebooking.model;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "UserCredential")
public class UserCredential {
	@Id
	private String email;
	private String password;
	private String resetPasswordToken;
	private Set<Role> userRoles;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}


	
	public Set<Role> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<Role> userRoles) {
		this.userRoles = userRoles;
	}

	public UserCredential() {

	}

	public UserCredential(String email, String password, String resetPasswordToken, Set<Role> userRoles) {
		super();
		this.email = email;
		this.password = password;
		this.resetPasswordToken = resetPasswordToken;
		this.userRoles = userRoles;
	}
	
	

	
	

	

}
