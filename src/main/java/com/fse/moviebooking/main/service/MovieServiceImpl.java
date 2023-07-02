package com.fse.moviebooking.main.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.fse.moviebooking.exception.MovieNotFoundException;
import com.fse.moviebooking.main.model.Movie;
import com.fse.moviebooking.main.model.Theatre;
import com.fse.moviebooking.main.model.TicketDetail;
import com.fse.moviebooking.main.repository.MovieRepository;
import com.fse.moviebooking.model.UserCredential;

@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	private MovieRepository movieRepository;

	private DynamoDBMapper dynamoDBMapper;

	@Autowired
	private AmazonDynamoDB amazonDynamoDB;

	

	private static final Logger log = LoggerFactory.getLogger(MovieServiceImpl.class);

	public String addMovie(Movie movie) throws Exception {
		log.info("Start: Add Movie");
		for (Theatre theatre : movie.getTheatres()) {
			IntStream stream = IntStream.iterate(1, i -> i + 1).limit(theatre.getNoOfTickets());
			List<Integer> seatNumbers = stream.collect(ArrayList<Integer>::new, ArrayList::add, ArrayList::addAll);
			theatre.setSeatNumbers(seatNumbers);
			theatre.setStatus("BOOK ASAP");
		}
		try {
			dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
			CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(Movie.class);
			tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
			TableUtils.createTableIfNotExists(amazonDynamoDB, tableRequest);

			dynamoDBMapper.save(movie);

		} catch (Exception e) {
			log.error("Movie Already Exist");
			log.info("End: Add Movie");
			throw new Exception();
		}
		log.info("End: Add Movie");
		return "Created";

	}

	@Override
	public List<Movie> getAllMovies() {
		return (List<Movie>) movieRepository.findAll();
	}

	@Override
	public Movie getMovie(String movieName) {
		log.info("Start: Get Movie");

		Optional<Movie> result = movieRepository.findById(movieName);
		if (result.isEmpty()) {
			log.error("Movie not found");
			log.info("End: Get Movie");
			throw new MovieNotFoundException("Movie Not Exists");
		}
		Movie movie = result.get();
		log.info("End: Get Movie");
		return movie;
	}

	@Override
	public Movie getMovieByMovieNameAndTheatreName(String movieName, String theatreName) {
		log.info("Start: Get Movie by Name and Theatre");

		Optional<Movie> response = movieRepository.findById(movieName);
		if (response.isEmpty()) {
			log.error("Movie not found");
			log.info("End: Get Movie by Name and Theatre");
			throw new MovieNotFoundException("Movie Not Exists");
		}
		log.info("End: Get Movie by Name and Theatre");
		return response.get();
	}

	@Override
	public String updateMovieTickets(String movieName, String theatreName, int tickets, List<Integer> seatNumbers,
			String status) {
		log.info("Start: Update Movie Details");
		System.out.println(tickets+"" + seatNumbers + status);
		Movie movie = movieRepository.findById(movieName).get();
		Map<Boolean, List<Theatre>> partition = movie.getTheatres().stream()
				.collect(Collectors.partitioningBy(theatre -> theatre.getTheatreName().equals(theatreName)));
		System.out.println("AFTER MAP:"+tickets+"" + seatNumbers + status);
		if (status != null) {
			System.out.println("AFTER CONDITION CHECK:"+tickets+"" + seatNumbers + status);
			List<Theatre> updatedTheatres = partition.get(true).stream()
					.peek(theatre -> {
						theatre.setNoOfTickets(tickets);
						theatre.setSeatNumbers(seatNumbers);
						theatre.setStatus(status);
					}).collect(Collectors.toList());
			System.out.println("Not Null before adding other elements:"+updatedTheatres.get(0).toString());
			updatedTheatres.addAll(partition.get(false));
			System.out.println("Not Null:"+updatedTheatres.get(0).toString());
			movie.setTheatres(updatedTheatres);
			movieRepository.save(movie);

		} else {
			List<Theatre> updatedTheatres = partition.get(true).stream()
					.peek(theatre -> {
						theatre.setNoOfTickets(tickets);
						theatre.setSeatNumbers(seatNumbers);
					}).collect(Collectors.toList());
			System.out.println("Null before adding other elements:"+updatedTheatres.get(0).toString());
			updatedTheatres.addAll(partition.get(false));
			System.out.println("Null:"+updatedTheatres);
			movie.setTheatres(updatedTheatres);
			movieRepository.save(movie);
		}

		log.info("End: Update Movie Details");
		return "Updated";

	}

	public void updateMovieTicketStatus(String movieName, String theatreName, String status) {
		log.info("Start: Update Movie Status");
		Movie movie = movieRepository.findById(movieName).get();
		Map<Boolean, List<Theatre>> partition = movie.getTheatres().stream()
				.collect(Collectors.partitioningBy(theatre -> theatre.getTheatreName().equals(theatreName)));

		List<Theatre> updatedTheates = partition.get(true).stream()
				.peek(theatre -> theatre.setStatus(status)).collect(Collectors.toList());
		updatedTheates.addAll(partition.get(false));
		movie.setTheatres(updatedTheates);
		movieRepository.save(movie);

		log.info("End: Update Movie Status");
	}

	public void deleteMovieById(String movieName) {
		log.info("Start: Delete Movie by Id");
		movieRepository.deleteById(movieName);
		log.info("End: Delete Movie by Id");
	}

	public void deleteMovieByTheatre(String movieName, String theatreName) {
		log.info("Start: Delete Movie by Theatre");
		Movie movie = movieRepository.findById(movieName).get();
		Map<Boolean, List<Theatre>> partition = movie.getTheatres().stream()
				.collect(Collectors.partitioningBy(theatre -> theatre.getTheatreName().equals( theatreName)));

		movie.setTheatres(partition.get(false));
		movieRepository.save(movie);
		log.info("End: Delete Movie by Theatre");
	}

	@KafkaListener(topics = "set-status", groupId = "mba-group", containerFactory = "kafkaListenerContainerFactory")
	public void consume(String message) {
		log.info("Start: Update Movie Status");
		String[] data = message.split("#");
		updateMovieTicketStatus(data[0], data[1], data[2]);
		log.info("End: Update Movie Status");
	}

	@KafkaListener(topics = "read-available-tickets", groupId = "mb-group", containerFactory = "replyKafkaListenerConatainerFactory")

	@SendTo("read-available-tickets-redirect")
	public TicketDetail ticketDetail(TicketDetail request) {
		log.info("Start: Get Available Tickets");
		Optional<Movie> resultMovie = Optional
				.ofNullable(getMovieByMovieNameAndTheatreName(request.getMovieName(), request.getTheatreName()));
		if (resultMovie.isEmpty())
			return request;
		Movie movie = resultMovie.get();
		Optional<Theatre> result = movie.getTheatres().stream()
				.filter(t -> t.getTheatreName().equals(request.getTheatreName()))
				.collect(Collectors.reducing((a, b) -> null));
		if (result.isEmpty()) {
			log.info("End: Get Available Tickets");
			return request;
		}
		request.setAvailable(result.get().getNoOfTickets());
		log.info("End: Get Available Tickets");
		return request;

	}

}
