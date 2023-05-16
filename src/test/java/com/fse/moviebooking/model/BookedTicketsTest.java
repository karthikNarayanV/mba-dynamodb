package com.fse.moviebooking.model;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;

import com.fse.moviebooking.main.model.BookedTickets;


 class BookedTicketsTest {

	@Test
	void defaultConstructor() {
		BookedTickets booked=new BookedTickets();
		booked.setBooked(2);
		assertEquals(2, booked.getBooked());
	}
	
	
}
