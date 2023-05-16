package com.fse.moviebooking.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

 class UserCredentialTest {
	
	@Test
	void defaultConstructor() {
		UserCredential user = new UserCredential();
		user.setEmail("karthikgogul6@gmail.com");
		user.setPassword("karthik5");
		user.setResetPasswordToken("");
		user.setUserRoles(Set.of(new Role("User")));
		assertEquals("karthikgogul6@gmail.com",user.getEmail());
		assertEquals("karthik5",user.getPassword());
		assertEquals("",user.getResetPasswordToken());
		assertEquals(1,user.getUserRoles().size());
	}
	
	@Test
	void argsConstructor() {
		String resetPasswordToken="";
		String email="karthikgogul6@gmail.com";
		String password="karthik5";
		Set<Role> userRoles=new HashSet<>();
		UserCredential user=new UserCredential( email,  password,  resetPasswordToken, userRoles);
		assertEquals(email,user.getEmail());
	}

}
