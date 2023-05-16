package com.fse.moviebooking.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fse.moviebooking.main.input.MovieInput;
import com.fse.moviebooking.main.model.Theatre;

class MovieInputTest {

	 @Test
		void defaultConstructor() {
			MovieInput movie = new MovieInput();
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
			MovieInput movie = new MovieInput("MI",theatres);
			assertEquals(2, movie.getTheatres().size());
		}
}
