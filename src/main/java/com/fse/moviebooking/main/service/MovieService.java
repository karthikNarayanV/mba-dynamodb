package com.fse.moviebooking.main.service;

import java.util.List;

import com.fse.moviebooking.main.model.Movie;

public interface MovieService {
	String addMovie(Movie movie) throws Exception;
	List<Movie> getAllMovies();
	Movie getMovie(String movieName);
	Movie getMovieByMovieNameAndTheatreName(String movieName,String theatreName);
	String updateMovieTickets(String movieName,String theatreName,int tickets,List<Integer> seatNumbers,String status);
	void updateMovieTicketStatus(String movieName, String theatreName,String status);
	void deleteMovieById(String movieName);
	void deleteMovieByTheatre(String movieName,String theatreName);
}
