package com.fse.moviebooking.main.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fse.moviebooking.exception.MovieNotFoundException;
import com.fse.moviebooking.main.input.MovieInput;
import com.fse.moviebooking.main.input.TicketInput;
import com.fse.moviebooking.main.kafka.Producer;
import com.fse.moviebooking.main.model.Movie;
import com.fse.moviebooking.main.model.Theatre;
import com.fse.moviebooking.main.service.MovieServiceImpl;
import com.fse.moviebooking.main.service.TicketServiceImpl;
import com.fse.moviebooking.model.LoginReturn;
import com.fse.moviebooking.model.UserCredential;

@WithMockUser
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
 class MovieControllerFailTest {
/*
	@Autowired
	private MockMvc mockMvc;
	
    @MockBean
    MovieServiceImpl movieServiceImpl;
    
    @MockBean
    TicketServiceImpl ticketServiceImpl;
    
    @MockBean
    Producer producer;
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
	  void testAddMovieFail() throws Exception 
	 {
		 when(movieServiceImpl.addMovie(any(Movie.class))).thenThrow(Exception.class);
		
		 List<Theatre> theatres=new ArrayList<>();
		 theatres.add(new Theatre("AGS",150));
		 theatres.add(new Theatre("PVR",170));
		 MvcResult mvcResult= mockMvc.perform( MockMvcRequestBuilders
	 	      .post("/addMovie").header("Authorization", token)
	 	      .content(asJsonString(new MovieInput("MI7",theatres)))
	 	      .contentType(MediaType.APPLICATION_JSON)
	 	      .accept(MediaType.APPLICATION_JSON))
	       .andReturn();
		 String content=mvcResult.getResponse().getContentAsString();
		 assertEquals("Movie Already Exist", content);
	 }
    
    @Test
	  void testGetAllMoviesFail() throws Exception 
	 {
		 when(movieServiceImpl.getAllMovies()).thenReturn(List.of());
		
		 
		 mockMvc.perform( MockMvcRequestBuilders
	 	      .get("/all")
	 	      .accept(MediaType.APPLICATION_JSON))
				 .andExpect(status().isBadRequest())
	       .andReturn();
		
	 }
    
    @Test
	  void testGetMovieByNameFail() throws Exception 
	 {
		String movieName="PS-2";
		 when(movieServiceImpl.getMovie(movieName)).thenThrow(MovieNotFoundException.class);
		 mockMvc.perform( MockMvcRequestBuilders
	 	      .get("/search/"+movieName)
	 	      
	 	      .accept(MediaType.APPLICATION_JSON))
				 .andExpect(status().isBadRequest())
	       .andReturn();
		
	 }
    
    @Test
	  void testBookMovieTicketTheatreNotFoundFail() throws Exception 
	 {
		 List<Theatre> theatres=new ArrayList<>();
		 theatres.add(new Theatre("AGS",150));
		 Movie m1=new Movie("MI7",theatres);
		 when(movieServiceImpl.getMovieByMovieNameAndTheatreName(m1.getMovieName(), "PVR")).thenReturn(m1);
		 TicketInput input=new TicketInput(0, "MI7","PVR" , 2, List.of(11,12));
		 
		 mockMvc.perform( MockMvcRequestBuilders
	 	      .post("/"+m1.getMovieName()+"/add")
	 	      .content(asJsonString(input))
	 	      .contentType(MediaType.APPLICATION_JSON)
	 	      .accept(MediaType.APPLICATION_JSON))
				 .andExpect(status().isBadRequest())
	       .andReturn();
		
	 }
	 
	 @Test
	  void testBookMovieTicketMovieNotFoundFail() throws Exception 
	 {
		 TicketInput input=new TicketInput(0, "MI7","PVR" , 2, List.of(11,12));
		 when(movieServiceImpl.getMovieByMovieNameAndTheatreName(input.getMovieName(), input.getTheatreName())).thenThrow(MovieNotFoundException.class);
		 mockMvc.perform( MockMvcRequestBuilders
	 	      .post("/"+"MI7"+"/add")
	 	      .content(asJsonString(input))
	 	      .contentType(MediaType.APPLICATION_JSON)
	 	      .accept(MediaType.APPLICATION_JSON))
				 .andExpect(status().isBadRequest())
	       .andReturn();	
	 }
	 
	 public static String asJsonString(final Object obj) {
	     try {
	         return new ObjectMapper().writeValueAsString(obj);
	     } catch (Exception e) {
	         throw new RuntimeException(e);
	     }
	 }
	 */
}
