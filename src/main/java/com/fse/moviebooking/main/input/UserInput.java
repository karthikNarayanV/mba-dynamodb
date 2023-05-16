package com.fse.moviebooking.main.input;

import java.util.Set;



public class UserInput {
	
	private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String contactNumber;
    private String loginId;
    private Set<String> userRoles;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
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
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public UserInput() {
		super();
	}
	public Set<String> getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(Set<String> userRoles) {
		this.userRoles = userRoles;
	}
	public UserInput(String firstName, String lastName, String email, String password, String contactNumber,
			String loginId, Set<String> userRoles) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.contactNumber = contactNumber;
		this.loginId = loginId;
		this.userRoles = userRoles;
	}
	
	
    
	
    

}
