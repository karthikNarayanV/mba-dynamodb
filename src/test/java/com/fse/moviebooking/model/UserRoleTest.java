package com.fse.moviebooking.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class UserRoleTest {
	 @Test
		void defaultConstructor() {
			UserRole ur=new UserRole();
			Role role=new Role();
			ur.setRole(role);
			assertEquals(role,ur.getRole());
		}
		
		@Test
		void argsConstructor() {
			Role role=new Role("User");
			UserRole ur=new UserRole(role);
			assertEquals("User", ur.getAuthority());
		}
}
