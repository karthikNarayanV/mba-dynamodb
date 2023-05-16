package com.fse.moviebooking.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fse.moviebooking.main.model.Movie;
import com.fse.moviebooking.main.model.Theatre;

 class MovieTest {

	@Test
	void defaultConstructor() {
		Movie movie = new Movie();
		movie.setMovieName("MI");
		List<Theatre> theatres=new ArrayList<>();
		theatres.add(new Theatre("AGS",150));
		theatres.add(new Theatre("PVR",170));
		movie.setTheatreId(theatres);
		assertEquals("MI", movie.getMovieName());
	}
	
	@Test
	void argsConstructor() {
		List<Theatre> theatres=new ArrayList<>();
		theatres.add(new Theatre("AGS",150));
		theatres.add(new Theatre("PVR",170));
		Movie movie = new Movie("MI",theatres);
		assertEquals("Movie [movieName=MI, theatres=" + theatres + "]", movie.toString());
	}
}
