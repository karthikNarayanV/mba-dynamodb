package com.fse.moviebooking.main.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.fse.moviebooking.exception.MovieNotFoundException;
import com.fse.moviebooking.main.model.Movie;
import com.fse.moviebooking.main.model.Theatre;
import com.fse.moviebooking.main.model.TicketDetail;
import com.fse.moviebooking.main.repository.MovieRepository;
//import com.mongodb.MongoWriteException;

@AutoConfigureMockMvc
@SpringBootTest
 class MovieServiceImplTest {
/*
	
	
	@Mock
	MovieRepository movieRepository;
	
	@InjectMocks
	MovieServiceImpl serviceImpl;
	
	@Test
	 void testAddMovieSuccess() throws Exception {
		List<Theatre> theatres=new ArrayList<>();
		 theatres.add(new Theatre("AGS",150));
		 theatres.add(new Theatre("PVR",170));
		 Movie m1=new Movie("MI7",theatres);
		 when(movieRepository.save(any(Movie.class))).thenReturn(m1);
		 String message=serviceImpl.addMovie(m1);
		 assertEquals("Created", message);
	}
	
	
	@Test
	 void testAddMovieFail() throws Exception {
		 List<Theatre> theatres=new ArrayList<>();
		 theatres.add(new Theatre("AGS",150));
		 theatres.add(new Theatre("PVR",170));
		 Movie m1=new Movie("MI7",theatres);
		 when(movieRepository.save(any(Movie.class))).thenThrow(Exception.class);
		 assertThrows(Exception.class, ()->serviceImpl.addMovie(m1));
	}
	
	@Test
	 void testGetAllMoviesSuccess() throws Exception {
		List<Theatre> theatres=new ArrayList<>();
		 theatres.add(new Theatre("AGS",150));
		 theatres.add(new Theatre("PVR",170));
		 Movie m1=new Movie("MI7",theatres);
		 when(movieRepository.findAll()).thenReturn(List.of(m1));
		 assertEquals(1,serviceImpl.getAllMovies().size());
	}
	
	@Test
	 void testGetMovieSuccess() throws Exception {
		List<Theatre> theatres=new ArrayList<>();
		 theatres.add(new Theatre("AGS",150));
		 theatres.add(new Theatre("PVR",170));
		 Movie m1=new Movie("MI7",theatres);
		 when(movieRepository.findById("MI7")).thenReturn(Optional.ofNullable(m1));
		 assertEquals(m1.getMovieName(),serviceImpl.getMovie("MI7").getMovieName());
	}
	
	@Test
	 void testGetMovieFail() throws Exception {
		
		 when(movieRepository.findById("MI7")).thenThrow(MovieNotFoundException.class);
		 assertThrows(MovieNotFoundException.class, ()->serviceImpl.getMovie("MI7"));
	}
	
	@Test
	 void testGetMovieFailException() throws Exception {
		
		 when(movieRepository.findById("MI7")).thenReturn(Optional.ofNullable(null));
		 assertThrows(MovieNotFoundException.class, ()->serviceImpl.getMovie("MI7"));
	}
	
	@Test
	 void testGetMovieByMovienameAndTheatreNameSuccess() throws Exception {
		List<Theatre> theatres=new ArrayList<>();
		 theatres.add(new Theatre("AGS",150));
		 theatres.add(new Theatre("PVR",170));
		 Movie m1=new Movie("MI7",theatres);
		 when(movieRepository.findByMovieNameAndTheatreName("MI7","PVR")).thenReturn(m1);
		 assertEquals(m1.getMovieName(),serviceImpl.getMovieByMovieNameAndTheatreName("MI7","PVR").getMovieName());
	}
	
	@Test
	 void testGetMovieByMovienameAndTheatreNameFail() throws Exception {
		
		 when(movieRepository.findByMovieNameAndTheatreName("MI7","PVR")).thenThrow(MovieNotFoundException.class);
		 assertThrows(MovieNotFoundException.class,()->serviceImpl.getMovieByMovieNameAndTheatreName("MI7","PVR"));
	}
	
	@Test
	 void testGetMovieByMovienameAndTheatreNameFailException() throws Exception {
		
		 when(movieRepository.findByMovieNameAndTheatreName("MI7","PVR")).thenReturn(null);
		 assertThrows(MovieNotFoundException.class,()->serviceImpl.getMovieByMovieNameAndTheatreName("MI7","PVR"));
	}
	
	@Test
	 void testUpdateMovieTicketsSuccess() throws Exception {
		 doNothing().when(movieRepository).updateNoOfTickets(isA(String.class), isA(String.class), isA(Integer.class));
		 doNothing().when(movieRepository).updateSeatNumbers(isA(String.class), isA(String.class), isA(List.class));;
		 doNothing().when(movieRepository).updateStatus(isA(String.class), isA(String.class), isA(String.class));
		 assertEquals("Updated",serviceImpl.updateMovieTickets("MI7","PVR",112,List.of(12,13),"BOOK ASAP"));
		 verify(movieRepository, times(1)).updateNoOfTickets(anyString(), anyString(), anyInt());
		 verify(movieRepository, times(1)).updateSeatNumbers(anyString(), anyString(), anyList());
		 verify(movieRepository, times(1)).updateStatus(anyString(), anyString(), anyString());
	}
	@Test
	 void testUpdateMovieTicketsStatusNullSuccess() throws Exception {
		 doNothing().when(movieRepository).updateNoOfTickets(isA(String.class), isA(String.class), isA(Integer.class));
		 doNothing().when(movieRepository).updateSeatNumbers(isA(String.class), isA(String.class), isA(List.class));;
		 assertEquals("Updated",serviceImpl.updateMovieTickets("MI7","PVR",112,List.of(12,13),null));
		 verify(movieRepository, times(1)).updateNoOfTickets(anyString(), anyString(), anyInt());
		 verify(movieRepository, times(1)).updateSeatNumbers(anyString(), anyString(), anyList());
	}
	@Test
	 void testUpdateMovieStatusSuccess() throws Exception {
		 doNothing().when(movieRepository).updateStatus(isA(String.class), isA(String.class), isA(String.class));
		 serviceImpl.updateMovieTicketStatus("MI7","PVR","BOOK ASAP");
		 verify(movieRepository, times(1)).updateStatus(anyString(), anyString(), anyString());
	}
	@Test
	 void testDeleteMovieByIdSuccess() throws Exception {
		 doNothing().when(movieRepository).deleteById(isA(String.class));
		 serviceImpl.deleteMovieById("MI7");
		 verify(movieRepository, times(1)).deleteById(anyString());
	}
	/*@Test
	 void testDeleteMovieByTheatreSuccess() throws Exception {
		 doNothing().when(movieRepository).deleteMovieByTheatre(isA(String.class), isA(String.class));
		 serviceImpl.deleteMovieByTheatre("MI7","PVR");
		 verify(movieRepository, times(1)).deleteMovieByTheatre(anyString(),anyString());
	}
	
	@Test
	 void testConsumeSuccess() throws Exception {
		 doNothing().when(movieRepository).updateStatus(isA(String.class), isA(String.class), isA(String.class));
		 serviceImpl.consume("MI7:PVR:BOOK ASAP");
		 verify(movieRepository, times(1)).updateStatus(anyString(), anyString(), anyString());
	}
	
	@Test
	 void testTicketDetailSuccess() throws Exception {
		TicketDetail ticketDetail = new TicketDetail("MI7","PVR",0,0); 
		List<Theatre> theatres=new ArrayList<>();
		 theatres.add(new Theatre("AGS",150));
		 theatres.add(new Theatre("PVR",170));
		 Movie m1=new Movie("MI7",theatres);
		 when(movieRepository.findByMovieNameAndTheatreName("MI7","PVR")).thenReturn(m1);
		 assertEquals(170,serviceImpl.ticketDetail(ticketDetail).getAvailable());
	}
	@Test
	 void testTicketDetailNoMovieFoundFail() throws Exception {
		TicketDetail ticketDetail = new TicketDetail("MI7","PVR",0,0); 
		 when(movieRepository.findByMovieNameAndTheatreName("MI7","PVR")).thenReturn(null);
		 assertEquals(0,serviceImpl.ticketDetail(ticketDetail).getAvailable());
	}
	@Test
	 void testTicketDetailNoTheatreFoundSuccess() throws Exception {
		TicketDetail ticketDetail = new TicketDetail("MI7","PVR",0,0); 
		List<Theatre> theatres=new ArrayList<>();
		 theatres.add(new Theatre("AGS",150));
		 theatres.add(new Theatre("PVR",170));
		 Movie m1=new Movie("MI7",theatres);
		 when(movieRepository.findByMovieNameAndTheatreName("MI7","Inox")).thenReturn(m1);
		 assertEquals(0,serviceImpl.ticketDetail(ticketDetail).getAvailable());
	}*/
}
