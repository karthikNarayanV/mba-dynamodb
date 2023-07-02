package com.fse.moviebooking.main.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fse.moviebooking.main.input.MovieInput;
import com.fse.moviebooking.main.input.TicketInput;
import com.fse.moviebooking.main.kafka.Producer;
import com.fse.moviebooking.main.model.Movie;
import com.fse.moviebooking.main.model.Theatre;
import com.fse.moviebooking.main.model.Ticket;
import com.fse.moviebooking.main.model.TicketDetail;
import com.fse.moviebooking.main.service.MovieServiceImpl;
import com.fse.moviebooking.main.service.TicketServiceImpl;
import com.fse.moviebooking.model.LoginReturn;
import com.fse.moviebooking.model.UserCredential;



@WithMockUser
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
 class MovieControllerTest {

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
	  void testAddMovieSuccess() throws Exception 
	 {
		 when(movieServiceImpl.addMovie(any(Movie.class))).thenReturn("Created");
		
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
		 assertEquals("Created", content);
	 }
	 
	
	 
	 @Test
	  void testGetAllMoviesSuccess() throws Exception 
	 {
		 List<Theatre> theatres=new ArrayList<>();
		 theatres.add(new Theatre("AGS",150));
		 theatres.add(new Theatre("PVR",170));
		 Movie m1=new Movie("MI7",theatres);
		 Movie m2=new Movie("FFX",theatres);
		 when(movieServiceImpl.getAllMovies()).thenReturn(List.of(m1,m2));
		
		 
		 mockMvc.perform( MockMvcRequestBuilders
	 	      .get("/all")
	 	      .accept(MediaType.APPLICATION_JSON))
				 .andExpect(status().isOk())
	       .andReturn();
		
	 }
	 
	 
	 @Test
	  void testGetMovieByNameSuccess() throws Exception 
	 {
		 List<Theatre> theatres=new ArrayList<>();
		 theatres.add(new Theatre("AGS",150));
		 theatres.add(new Theatre("PVR",170));
		 Movie m1=new Movie("MI7",theatres);
		 when(movieServiceImpl.getMovie(m1.getMovieName())).thenReturn(m1);
		
		 
		 mockMvc.perform( MockMvcRequestBuilders
	 	      .get("/search/"+m1.getMovieName())
	 	      .accept(MediaType.APPLICATION_JSON))
				 .andExpect(status().isOk())
	       .andReturn();
		
	 }
	 
	 
	 
	 @Test
	  void testBookMovieTicketSuccess() throws Exception 
	 {
		 List<Theatre> theatres=new ArrayList<>();
		 Theatre t=new Theatre("PVR",170);
		 IntStream stream = IntStream.iterate(1, i -> i + 1).limit(t.getNoOfTickets());
		 List<Integer> seatNumbers = stream.collect(ArrayList<Integer>::new, ArrayList::add, ArrayList::addAll);
		 t.setSeatNumbers(seatNumbers);
		 theatres.add(new Theatre("AGS",150));
		 theatres.add(t);
		 Movie m1=new Movie("MI7",theatres);
		 when(movieServiceImpl.getMovieByMovieNameAndTheatreName(m1.getMovieName(), "PVR")).thenReturn(m1);
		 TicketInput input=new TicketInput(0, "MI7","PVR" , 2, List.of(11,12));
		 when(movieServiceImpl.updateMovieTickets(input.getMovieName(), input.getTheatreName(), input.getNoOfTickets(), input.getSeatNumber(), "BOOK ASAP")).thenReturn("Updated");
		 Ticket ticket=new Ticket(0, "MI7","PVR" , 2, List.of(11,12));
		 when(ticketServiceImpl.bookTicket(ticket)).thenReturn(12345);
		 
		 mockMvc.perform( MockMvcRequestBuilders
	 	      .post("/"+m1.getMovieName()+"/add")
	 	      .content(asJsonString(input))
	 	      .contentType(MediaType.APPLICATION_JSON)
	 	      .accept(MediaType.APPLICATION_JSON))
				 .andExpect(status().isOk())
	       .andReturn();
		
	 }
	 
	 
	 
	 @Test
	  void testMovieByIdSucces() throws Exception 
	 {
		 doNothing().when(movieServiceImpl).deleteMovieById(isA(String.class));;
		 mockMvc.perform( MockMvcRequestBuilders
	 	      .delete("/"+"MI7"+"/delete/"+"all").header("Authorization", token)
	 	      .accept(MediaType.APPLICATION_JSON))
				 .andExpect(status().isOk())
	       .andReturn();
		 verify(movieServiceImpl, times(1)).deleteMovieById("MI7");
	 }
	 
	/* @Test
	  void testMovieByIdAndTheatreNameSucces() throws Exception 
	 {
		 doNothing().when(movieServiceImpl).deleteMovieByTheatre(isA(String.class),isA(String.class));;
		 mockMvc.perform( MockMvcRequestBuilders
	 	      .delete("/"+"MI7"+"/delete/"+"PVR").header("Authorization", token)
	 	      .accept(MediaType.APPLICATION_JSON))
				 .andExpect(status().isOk())
	       .andReturn();
		 verify(movieServiceImpl, times(1)).deleteMovieByTheatre("MI7", "PVR");
	 }
	 
	 @Test
	  void testUpdateMovieStatusSuccess() throws Exception 
	 {
		 doNothing().when(producer).sendMessage(isA(String.class), isA(String.class), isA(String.class));;
		 mockMvc.perform( MockMvcRequestBuilders
	 	      .put("/"+"MI7"+"/update/"+"PVR").header("Authorization", token)
	 	      .content("BOOK ASAP")
	 	      .contentType(MediaType.TEXT_PLAIN_VALUE)
	 	      .accept(MediaType.ALL))
				 .andExpect(status().isOk())
	       .andReturn();
		 verify(producer, times(1)).sendMessage("MI7", "PVR", "BOOK ASAP");
	 }
	 
	 @Test
	  void testGetTicketDetailSuccess() throws Exception 
	 {
		 TicketDetail request=new TicketDetail("MI7", "PVR",0,0) ;
		 when(producer.kafkaRequestReply(isA(String.class), isA(TicketDetail.class))).thenReturn(request);
		 mockMvc.perform( MockMvcRequestBuilders
	 	      .get("/"+"MI7"+"/ticket/"+"PVR").header("Authorization", token)
	 	      .accept(MediaType.APPLICATION_JSON))
				 .andExpect(status().isOk())
	       .andReturn();
		 verify(producer, times(2)).kafkaRequestReply(anyString(), any(TicketDetail.class));
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
