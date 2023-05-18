package com.fse.moviebooking.controller;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fse.moviebooking.model.LoginReturn;
import com.fse.moviebooking.model.PasswordCredential;
import com.fse.moviebooking.model.UserCredential;



@SpringBootTest
@AutoConfigureMockMvc

 class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	
	@Test
	 void testLoginUserSuccess() throws Exception {
		
		 mockMvc.perform( MockMvcRequestBuilders
		 	      .post("/login")
		 	      .content(asJsonString(new UserCredential( "admin", "admin","",Set.of())))
		 	      .contentType(MediaType.APPLICATION_JSON)
		 	      .accept(MediaType.APPLICATION_JSON))
				 .andExpect(status().isOk())
		       .andReturn();
		 
	}
	
	@Test
	 void testLoginUserFail() throws Exception {
		
		 mockMvc.perform( MockMvcRequestBuilders
		 	      .post("/login")
		 	      .content(asJsonString(new UserCredential( "abcdefg", "abcdkh","",Set.of())))
		 	      .contentType(MediaType.APPLICATION_JSON)
		 	      .accept(MediaType.APPLICATION_JSON))
				 .andExpect(status().isBadRequest())
		       .andReturn();
		 
	}
	
	@Test
	 void testValidateSuccess() throws Exception {
		
		MvcResult loginResult=mockMvc.perform( MockMvcRequestBuilders
		 	      .post("/login")
		 	      .content(asJsonString(new UserCredential( "admin", "admin","",Set.of())))
		 	      .contentType(MediaType.APPLICATION_JSON)
		 	      .accept(MediaType.APPLICATION_JSON))
				 .andExpect(status().isOk())
		       .andReturn();
		String content=loginResult.getResponse().getContentAsString();
		LoginReturn returnResult=new ObjectMapper().readValue(content, LoginReturn.class);
		String token="Bearer "+returnResult.getToken();
		mockMvc.perform( MockMvcRequestBuilders
		 	      .get("/validate").header("Authorization", token)
		 	    
		 	      .contentType(MediaType.APPLICATION_JSON)
		 	      .accept(MediaType.APPLICATION_JSON))
				 .andExpect(status().isOk())
		       .andReturn();
		 
	}
	
	@Test
	 void testForgotSuccess() throws Exception {
		
		mockMvc.perform( MockMvcRequestBuilders
		 	      .post("/forgot")
		 	      .content( "admin")
		 	      .contentType(MediaType.APPLICATION_JSON)
		 	      .accept(MediaType.APPLICATION_JSON))
				 .andExpect(status().isOk())
		       .andReturn();
		
		
		 
	}
	
	@Test
	 void testForgotFail() throws Exception {
		
		mockMvc.perform( MockMvcRequestBuilders
		 	      .post("/forgot")
		 	      .content( "bcdeaf")
		 	      .contentType(MediaType.APPLICATION_JSON)
		 	      .accept(MediaType.APPLICATION_JSON))
				 .andExpect(status().isBadRequest())
		       .andReturn();
		
		
		 
	}
	
	@Test
	 void testResetPasswordSuccess() throws Exception {
		
		MvcResult result= mockMvc.perform( MockMvcRequestBuilders
		 	      .post("/forgot")
		 	      .content( "admin")
		 	      .contentType(MediaType.APPLICATION_JSON)
		 	      .accept(MediaType.APPLICATION_JSON))
				 .andExpect(status().isOk())
		       .andReturn();
		String token=result.getResponse().getContentAsString();
		mockMvc.perform( MockMvcRequestBuilders
		 	      .post("/reset")
		 	      .content( asJsonString(new PasswordCredential(token, "admin")))
		 	      .contentType(MediaType.APPLICATION_JSON)
		 	      .accept(MediaType.APPLICATION_JSON))
				 .andExpect(status().isOk())
		       .andReturn();
		
		 
	}
	
	
	
	 public static String asJsonString(final Object obj) {
	     try {
	         return new ObjectMapper().writeValueAsString(obj);
	     } catch (Exception e) {
	         throw new RuntimeException(e);
	     }
	 }
}
