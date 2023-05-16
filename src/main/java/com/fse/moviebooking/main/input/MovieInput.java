package com.fse.moviebooking.main.input;

import java.util.List;

import com.fse.moviebooking.main.model.Theatre;

public class MovieInput {
private String movieName;
	
	private List<Theatre> theatres;

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public List<Theatre> getTheatres() {
		return theatres;
	}

	public void setTheatreId(List<Theatre> theatres) {
		this.theatres = theatres;
	}

	public MovieInput() {
		super();
	}

	public MovieInput(String movieName, List<Theatre> theatres) {
		super();
		this.movieName = movieName;
		this.theatres = theatres;
	}
	
	
}
