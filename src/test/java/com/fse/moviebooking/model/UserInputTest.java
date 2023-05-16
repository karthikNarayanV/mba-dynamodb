package com.fse.moviebooking.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.fse.moviebooking.main.input.UserInput;

 class UserInputTest {
	@Test
	void defaultConstructor() {
		UserInput user = new UserInput();
		user.setFirstName("Karthik");
		user.setLastName("Narayan");
		user.setLoginId("12345");
		user.setEmail("karthikgogul6@gmail.com");
		user.setContactNumber("9677255341");
		user.setPassword("karthik5");
		user.setUserRoles(Set.of("USER"));
		assertEquals("Karthik",user.getFirstName());
		assertEquals("Narayan",user.getLastName());
		assertEquals("12345",user.getLoginId());
		assertEquals("karthikgogul6@gmail.com",user.getEmail());
		assertEquals("9677255341",user.getContactNumber());
		assertEquals("karthik5",user.getPassword());
		assertEquals(1,user.getUserRoles().size());
	}
	
	@Test
	void argsConstructor() {
		String firstName="Karthik";
		String lastName="Narayan";
		String email="karthikgogul6@gmail.com";
		String loginId="12345";
		String password="karthik5";
		String contactNumber="9677255341";
		Set<String> roles=new HashSet<>();
		roles.add("user");
		UserInput user=new UserInput( firstName,  lastName,  email, loginId,  password,  contactNumber,roles);
		assertEquals(firstName,user.getFirstName());
	}
}
