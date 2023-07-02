package com.fse.moviebooking.main.controller;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.security.RolesAllowed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fse.moviebooking.exception.MovieNotFoundException;
import com.fse.moviebooking.main.input.MovieInput;
import com.fse.moviebooking.main.input.TicketInput;
import com.fse.moviebooking.main.kafka.Producer;
import com.fse.moviebooking.main.model.Movie;
import com.fse.moviebooking.main.model.Theatre;
import com.fse.moviebooking.main.model.Ticket;
import com.fse.moviebooking.main.model.TicketDetail;
import com.fse.moviebooking.main.service.MovieServiceImpl;
import com.fse.moviebooking.main.service.TicketServiceImpl;

@CrossOrigin(allowedHeaders = "*", origins =  "*")
@RestController
public class MovieController {

	@Autowired
	private MovieServiceImpl movieService;
	
	@Autowired 
	private TicketServiceImpl ticketService;
	
	private final Producer producer;

	public MovieController(Producer producer) {
		this.producer = producer;
	}
	
	private static final Logger log=LoggerFactory.getLogger(MovieController.class);

	@RolesAllowed("ROLE_ADMIN")
	@PostMapping(value="/addMovie")
    public ResponseEntity<?> addMovie(@RequestBody MovieInput movieInput){
		log.info("Start: Add Movie");
		Movie movie = new Movie(movieInput.getMovieName(),movieInput.getTheatres());
		try {
			log.debug("Movie Name:{}", movie.getMovieName());
			String message=movieService.addMovie(movie);
			log.info("End: Add Movie");
	        return new ResponseEntity<>(message, HttpStatus.OK);
		}
		catch(Exception e) {
			log.error("Movie Already Exists");
			log.info("End: Add Movie");
			return new ResponseEntity<>("Movie Already Exist", HttpStatus.BAD_REQUEST);
		}
    }
	
	@RolesAllowed({"ROLE_ADMIN","ROLE_USER"})
	@GetMapping(value="/all")
    public ResponseEntity<?> getAllMovie(){
		log.info("Start: Get All Movies");
		List<Movie> movies=movieService.getAllMovies();
	
		if(movies.isEmpty()) {
			
			log.info("End: Get All Movies");
			return new ResponseEntity<>("No Movies exists", HttpStatus.BAD_REQUEST);
		}
		else {
			log.info("End: Get All Movies");
			return new ResponseEntity<>(movies, HttpStatus.OK);
		}
    }
	
	@RolesAllowed({"ROLE_ADMIN","ROLE_USER"})
	@GetMapping(value="/search/{movieName}")
    public ResponseEntity<?> getMovie(@PathVariable String movieName){
		log.info("Start: Get Movie");
		try {
			Movie movie=movieService.getMovie(movieName);
			log.info("End: Get Movie");
			return new ResponseEntity<>(movie, HttpStatus.OK);
		}
		catch(MovieNotFoundException e) {
			log.error("No Movie Found");
			log.info("End: Get Movie");
			return new ResponseEntity<>("No Movies exists", HttpStatus.BAD_REQUEST);
		}
    }

	@RolesAllowed({"ROLE_ADMIN","ROLE_USER"})
	@PostMapping(value="/{movieName}/add")
    public ResponseEntity<?> bookMovie(@PathVariable String movieName,@RequestBody TicketInput ticketInput){
		log.info("Start: Book Movie");
		Ticket ticket = new Ticket(ticketInput.getTicketId(),ticketInput.getMovieName(),ticketInput.getTheatreName(),ticketInput.getNoOfTickets(),ticketInput.getSeatNumber());
		try {
			Movie movie=movieService.getMovieByMovieNameAndTheatreName(movieName, ticket.getTheatreName());
			Optional<Theatre> result=movie.getTheatres().stream().filter(t->t.getTheatreName().equals(ticket.getTheatreName())).collect(Collectors.reducing((a, b) -> null));
			if (result.isEmpty()) {
				log.info("End: Book Movie");
				 return new ResponseEntity<>("Theatre not allocated for this Movie", HttpStatus.BAD_REQUEST);
			}
				
			Theatre theatre=result.get();
			List<Integer> updatedSeatNumbers=theatre.getSeatNumbers();
			updatedSeatNumbers.removeAll(ticket.getSeatNumber());
			int newNoOfTickets=theatre.getNoOfTickets()-ticket.getNoOfTickets();
			String status=null;
			if(newNoOfTickets<=0) status="SOLD OUT";
			
			movieService.updateMovieTickets(movieName, ticket.getTheatreName(), newNoOfTickets,updatedSeatNumbers,status);
			int ticketId=ticketService.bookTicket(ticket);
			log.info("End: Book Movie");
			return new ResponseEntity<>("Ticket Id: "+ticketId, HttpStatus.OK);

		}
		catch(MovieNotFoundException e) {
			log.error("No Movie Found");
			log.info("End: Book Movie");
			return new ResponseEntity<>("No Movies exists", HttpStatus.BAD_REQUEST);
		}
		
    }
	
	@RolesAllowed({"ROLE_ADMIN"})
	@PutMapping(value="/{movieName}/update/{theatreName}")
    public ResponseEntity<?> updateMovieStatus(@PathVariable String movieName,@PathVariable String theatreName, @RequestBody String status){
		log.info("Start: Update Movie Status");
		producer.sendMessage(movieName, theatreName, status);
		log.info("End: Update Movie Status");
		return new ResponseEntity<>("Status Changed", HttpStatus.OK);
    }
	
	@RolesAllowed({"ROLE_ADMIN"})
	@GetMapping(value="/{movieName}/ticket/{theatreName}")
    public ResponseEntity<?> getTicketDetails(@PathVariable String movieName,@PathVariable String theatreName) throws Exception{
		log.info("Start: Get Ticket Details");
		  TicketDetail request=new TicketDetail(movieName,theatreName,0,0) ;
		  request= producer.kafkaRequestReply("read-available-tickets",request);
		  request=producer.kafkaRequestReply("read-booked",request);
		  log.info("End: Get Ticket Details");
		  return new ResponseEntity<>(request, HttpStatus.OK);
    }
	
	@RolesAllowed({"ROLE_ADMIN"})
	@DeleteMapping(value="/{movieName}/delete/{theatreName}")
    public String deleteMovie(@PathVariable String movieName,@PathVariable String theatreName){
		log.info("Start: Delete Movie");
		if(theatreName.equals("all")) {
			log.info("Deleting all movies");
			movieService.deleteMovieById(movieName);
		}
		else 
			movieService.deleteMovieByTheatre(movieName, theatreName);
		log.info("End: Delete Movie");
		return "Deleted";
    }
    
}
