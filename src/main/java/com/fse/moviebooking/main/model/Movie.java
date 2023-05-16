package com.fse.moviebooking.main.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Movie")
public class Movie {
	@Id
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

	public Movie(String movieName, List<Theatre> theatres) {
		super();
		this.movieName = movieName;
		this.theatres = theatres;
	}

	public Movie() {
		super();
	}

	@Override
	public String toString() {
		return "Movie [movieName=" + movieName + ", theatres=" + theatres + "]";
	}
	
	
}
