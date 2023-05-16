package com.fse.moviebooking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.fse.moviebooking.model.User;
import com.fse.moviebooking.repository.UserRepository;
import com.mongodb.MongoWriteException;

@AutoConfigureMockMvc
@SpringBootTest
 class UserServiceImplTest {
	
	@Mock
	UserRepository userRepository;
	
	@InjectMocks
	UserServiceImpl userServiceImpl;
	
	@Test
	void testSaveUserSuccess() throws Exception {
		String firstName="Karthik";
		String lastName="Narayan";
		String email="karthikgogul6@gmail.com";
		String loginId="12345";
		String password="karthik5";
		String contactNumber="9677255341";
		User user=new User( firstName,  lastName,  email, loginId,  password,  contactNumber);
		when(userRepository.insert(any(User.class))).thenReturn(user);
		String result=userServiceImpl.saveUser(user);
		assertEquals(user.toString(), result);
	}
	
	@Test
	void testSaveUserFail() throws Exception {
		String firstName="Karthik";
		String lastName="Narayan";
		String email="karthikgogul6@gmail.com";
		String loginId="12345";
		String password="karthik5";
		String contactNumber="9677255341";
		User user=new User( firstName,  lastName,  email, loginId,  password,  contactNumber);
		when(userRepository.insert(any(User.class))).thenThrow(MongoWriteException.class);
		assertThrows(Exception.class, ()->userServiceImpl.saveUser(user));
	}
	
	@Test
	void testGetUserSuccess() throws Exception {
		String firstName="Karthik";
		String lastName="Narayan";
		String email="karthikgogul6@gmail.com";
		String loginId="12345";
		String password="karthik5";
		String contactNumber="9677255341";
		User user=new User( firstName,  lastName,  email, loginId,  password,  contactNumber);
		when(userRepository.findByEmail(any(String.class))).thenReturn(user);
		User result=userServiceImpl.getUser("karthikgogul6@gmail.com");
		assertEquals(user.toString(), result.toString());
	}
	
	@Test
	void testGetUserFail() throws Exception {
		
		when(userRepository.findByEmail(any(String.class))).thenReturn(null);
		User result=userServiceImpl.getUser("karthikgogul6@gmail.com");
		assertNull(result,"Null");
	}


}
