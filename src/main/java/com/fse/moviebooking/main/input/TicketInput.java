package com.fse.moviebooking.main.input;

import java.util.List;

public class TicketInput {
	private int ticketId;
	private String movieName;
	private String theatreName;
	private int noOfTickets;
	private List<Integer> seatNumber;
	public int getTicketId() {
		return ticketId;
	}
	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public String getTheatreName() {
		return theatreName;
	}
	public void setTheatreName(String theatreName) {
		this.theatreName = theatreName;
	}
	public int getNoOfTickets() {
		return noOfTickets;
	}
	public void setNoOfTickets(int noOfTickets) {
		this.noOfTickets = noOfTickets;
	}
	public List<Integer> getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(List<Integer> seatNumber) {
		this.seatNumber = seatNumber;
	}
	public TicketInput(int ticketId, String movieName, String theatreName, int noOfTickets, List<Integer> seatNumber) {
		super();
		this.ticketId = ticketId;
		this.movieName = movieName;
		this.theatreName = theatreName;
		this.noOfTickets = noOfTickets;
		this.seatNumber = seatNumber;
	}
	public TicketInput() {
		super();
	}
	
	
}
