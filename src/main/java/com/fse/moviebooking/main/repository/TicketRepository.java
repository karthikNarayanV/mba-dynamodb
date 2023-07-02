package com.fse.moviebooking.main.repository;

import java.util.List;
import java.util.Optional;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.fse.moviebooking.main.model.BookedTickets;
import com.fse.moviebooking.main.model.Ticket;

@EnableScan
public interface TicketRepository extends CrudRepository<Ticket,Object>{
	
	List<Ticket> findByMovieNameAndTheatreName(String movieName,String theatreName);
}
