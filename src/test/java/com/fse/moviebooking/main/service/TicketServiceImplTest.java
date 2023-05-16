package com.fse.moviebooking.main.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import com.fse.moviebooking.main.model.BookedTickets;
import com.fse.moviebooking.main.model.Ticket;
import com.fse.moviebooking.main.model.TicketDetail;
import com.fse.moviebooking.main.repository.TicketRepository;

@AutoConfigureMockMvc
@SpringBootTest
 class TicketServiceImplTest {
	
	@Mock
	TicketRepository ticketRepository;
	
	@InjectMocks
	TicketServiceImpl ticketServiceImpl;
	
	@Test
	 void bookTicketSuccess()  {
		Ticket ticket=new Ticket(0,"PS-2","PVR",2,List.of(11, 12));
		when(ticketRepository.insert(any(Ticket.class))).thenReturn(ticket);
		int id=ticketServiceImpl.bookTicket(ticket);
		assertNotEquals(0, id);
	}
	
	
	
	@Test
	 void testBookedTicketsSuccess() throws Exception {
		TicketDetail ticketDetail = new TicketDetail("MI7","PVR",0,0); 
		 BookedTickets bt= new BookedTickets(11);
		 AggregationResults<BookedTickets> result= new AggregationResults<BookedTickets>(List.of(bt),new Document() );
		 when(ticketRepository.findTicketsBooked("MI7","PVR")).thenReturn(result);
		 assertEquals(11,ticketServiceImpl.bookedTickets(ticketDetail).getBooked());
	}
	
	@Test
	 void testBookedTicketsFail() throws Exception {
		TicketDetail ticketDetail = new TicketDetail("MI7","PVR",0,0); 
		BookedTickets bt= new BookedTickets();
		 AggregationResults<BookedTickets> result= new AggregationResults<BookedTickets>(List.of(bt),new Document() );
		 when(ticketRepository.findTicketsBooked("MI7","PVR")).thenReturn(result);
		 assertEquals(0,ticketServiceImpl.bookedTickets(ticketDetail).getBooked());
	}

}
