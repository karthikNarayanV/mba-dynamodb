package com.fse.moviebooking.main.service;

import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import com.fse.moviebooking.main.model.BookedTickets;
import com.fse.moviebooking.main.model.Ticket;
import com.fse.moviebooking.main.model.TicketDetail;
import com.fse.moviebooking.main.repository.TicketRepository;
import com.mongodb.MongoWriteException;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketRepository ticketRepository;
	
	private  static final Logger log = LoggerFactory.getLogger(TicketServiceImpl.class);

	@Override
	public int bookTicket(Ticket ticket)  {
		log.info("Start: Book Tickets");
		int min = 10000, max = 99999;
		Random random1 = new Random();
		while(true) {
			try {
				int ticketId = random1.nextInt(max - min) + min;
				ticket.setTicketId(ticketId);
				Ticket t = ticketRepository.insert(ticket);
				log.info("End: Book Tickets");
				return t.getTicketId();
			} catch (MongoWriteException e) {
				log.error("Fetching new Ticket ID");
			}
		}
		
		
	}

	@KafkaListener(topics = "read-booked", groupId = "mb-group", containerFactory = "replyKafkaListenerConatainerFactory")
	@SendTo("read-booked-redirect")
	public TicketDetail bookedTickets(TicketDetail request) {
			log.info("Start: Get Booked Tickets");
			Optional<AggregationResults<BookedTickets>> result = Optional
					.ofNullable(ticketRepository.findTicketsBooked(request.getMovieName(), request.getTheatreName()));
			if(result.isEmpty()) {
				log.info("End: Get Booked Tickets");
				return request;
			}
				
			int tickets = result.get().getMappedResults().get(0).getBooked();
			request.setBooked(tickets);
			log.info("End: Get Booked Tickets");
			return request;
		

	}

}
