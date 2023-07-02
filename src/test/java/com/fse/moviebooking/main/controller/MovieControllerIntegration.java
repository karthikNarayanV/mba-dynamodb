package com.fse.moviebooking.main.controller;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fse.moviebooking.main.repository.MovieRepository;
import com.fse.moviebooking.main.repository.TicketRepository;
import com.fse.moviebooking.model.LoginReturn;
import com.fse.moviebooking.model.UserCredential;


@WithMockUser
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
 class MovieControllerIntegration {
	/*
	@Autowired
	private MockMvc mockMvc;
	
    @Mock
    MovieRepository movieRepository;
    
    @Mock
    TicketRepository ticketRepository;
    
    public String token;
    
    @BeforeEach
    public void setup() throws Exception {
    	MvcResult result=mockMvc.perform( MockMvcRequestBuilders
		 	      .post("/login")
		 	      .content(asJsonString(new UserCredential( "admin", "admin","",Set.of())))
		 	      .contentType(MediaType.APPLICATION_JSON)
		 	      .accept(MediaType.APPLICATION_JSON))
				 .andExpect(status().isOk())
		       .andReturn();
    	String content=result.getResponse().getContentAsString();
		LoginReturn returnResult=new ObjectMapper().readValue(content, LoginReturn.class);
		token="Bearer "+returnResult.getToken();
    }
	
	 @Test
	  void testUpdateMovieStatusSuccess() throws Exception 
	 {
		 doNothing().when(movieRepository).updateStatus(isA(String.class), isA(String.class), isA(String.class));;
		 mockMvc.perform( MockMvcRequestBuilders
	 	      .put("/"+"MI7"+"/update/"+"PVR").header("Authorization", token)
	 	      .content("BOOK ASAP")
	 	      .contentType(MediaType.TEXT_PLAIN_VALUE)
	 	      .accept(MediaType.ALL))
				 .andExpect(status().isOk())
	       .andReturn();
	 }
	 
	
	 
	 
	 
	 public static String asJsonString(final Object obj) {
	     try {
	         return new ObjectMapper().writeValueAsString(obj);
	     } catch (Exception e) {
	         throw new RuntimeException(e);
	     }
	 }*/

}
