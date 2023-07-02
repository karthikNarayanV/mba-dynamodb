package com.fse.moviebooking.main.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.fse.moviebooking.main.model.BookedTickets;
import com.fse.moviebooking.main.model.Movie;
import com.fse.moviebooking.main.model.Ticket;
import com.fse.moviebooking.main.model.TicketDetail;
import com.fse.moviebooking.main.repository.MovieRepository;
import com.fse.moviebooking.main.repository.TicketRepository;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketRepository ticketRepository;
	
	private DynamoDBMapper dynamoDBMapper;

	@Autowired
	private AmazonDynamoDB amazonDynamoDB;
	
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
				dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
		        CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(Ticket.class);
		        tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
		        TableUtils.createTableIfNotExists(amazonDynamoDB, tableRequest);
				Ticket t = ticketRepository.save(ticket);
				log.info("End: Book Tickets");
				return t.getTicketId();
			} catch (Exception e) {
				log.error("Fetching new Ticket ID");
			}
		}
		
		
	}

	@KafkaListener(topics = "read-booked", groupId = "mb-group", containerFactory = "replyKafkaListenerConatainerFactory")
	@SendTo("read-booked-redirect")
	public TicketDetail bookedTickets(TicketDetail request) {
		try {
			log.info("Start: Get Booked Tickets");
			Optional<List<Ticket>> result = Optional
					.ofNullable(ticketRepository.findByMovieNameAndTheatreName(request.getMovieName(), request.getTheatreName()));
			
			if(result.isEmpty()) {
				log.info("End: Get Booked Tickets");
				return request;
			}
			List<Ticket> tickets=result.get();
			
			//int tickets = result.get().getMappedResults().get(0).getBooked();
			int bookedTickets = tickets.stream().mapToInt(ticket->ticket.getNoOfTickets()).sum();
			request.setBooked(bookedTickets);
			log.info("End: Get Booked Tickets");
			return request;
		}catch(IndexOutOfBoundsException e) {
			return request;
		}
		

	}
}
