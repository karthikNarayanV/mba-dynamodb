package com.fse.moviebooking.main.repository;

import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.fse.moviebooking.main.model.BookedTickets;
import com.fse.moviebooking.main.model.Ticket;

public interface TicketRepository extends MongoRepository<Ticket,Object>{
	
	@Aggregation(pipeline = {
			"{ $match: { movieName:?0,theatreName:?1 } }",
			"{$group: {_id:null, booked:{$sum:'$noOfTickets'}}}",
			"{$project:{_id:0,booked:1}}"
	})
	AggregationResults<BookedTickets> findTicketsBooked(String movieName,String theatreName);
}
