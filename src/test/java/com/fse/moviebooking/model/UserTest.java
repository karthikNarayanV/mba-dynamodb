package com.fse.moviebooking.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fse.moviebooking.main.model.Movie;
import com.fse.moviebooking.main.model.Theatre;

 class UserTest {

	@Test
	void defaultConstructor() {
		User user = new User();
		user.setFirstName("Karthik");
		user.setLastName("Narayan");
		user.setLoginId("12345");
		user.setEmail("karthikgogul6@gmail.com");
		user.setContactNumber("9677255341");
		user.setPassword("karthik5");
		assertEquals("Karthik",user.getFirstName());
		assertEquals("Narayan",user.getLastName());
		assertEquals("12345",user.getLoginId());
		assertEquals("karthikgogul6@gmail.com",user.getEmail());
		assertEquals("9677255341",user.getContactNumber());
		assertEquals("karthik5",user.getPassword());
	}
	
	@Test
	void argsConstructor() {
		String firstName="Karthik";
		String lastName="Narayan";
		String email="karthikgogul6@gmail.com";
		String loginId="12345";
		String password="karthik5";
		String contactNumber="9677255341";
		User user=new User( firstName,  lastName,  email, loginId,  password,  contactNumber);
		assertEquals("User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", loginId='" + loginId + '\'' +
                ", password='" + password + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                '}', user.toString());
	}
}
