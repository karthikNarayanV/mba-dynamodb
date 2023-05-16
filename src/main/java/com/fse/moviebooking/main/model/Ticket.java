package com.fse.moviebooking.main.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection="Ticket")
public class Ticket {

	@Id
	private int ticketId;
	private String movieName;
	private String theatreName;
	private int noOfTickets;
	private List<Integer> seatNumber;
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
		public Ticket() {
		super();
	}
		public int getTicketId() {
			return ticketId;
		}
		public void setTicketId(int ticketId) {
			this.ticketId = ticketId;
		}
		public Ticket(int ticketId, String movieName, String theatreName, int noOfTickets, List<Integer> seatNumber) {
			super();
			this.ticketId = ticketId;
			this.movieName = movieName;
			this.theatreName = theatreName;
			this.noOfTickets = noOfTickets;
			this.seatNumber = seatNumber;
		}
		@Override
		public String toString() {
			return "Ticket [ticketId=" + ticketId + ", movieName=" + movieName + ", theatreName=" + theatreName
					+ ", noOfTickets=" + noOfTickets + ", seatNumber=" + seatNumber + "]";
		}
		
	
	
	
}
