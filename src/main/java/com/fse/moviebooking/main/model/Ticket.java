package com.fse.moviebooking.main.model;

import java.util.List;

import org.springframework.data.annotation.Id;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Ticket")
public class Ticket {

	
	private int ticketId;
	private String movieName;
	private String theatreName;
	private int noOfTickets;
	private List<Integer> seatNumber;

	@DynamoDBAttribute
	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	@DynamoDBAttribute
	public String getTheatreName() {
		return theatreName;
	}

	public void setTheatreName(String theatreName) {
		this.theatreName = theatreName;
	}

	@DynamoDBAttribute
	public int getNoOfTickets() {
		return noOfTickets;
	}

	public void setNoOfTickets(int noOfTickets) {
		this.noOfTickets = noOfTickets;
	}

	@DynamoDBAttribute
	public List<Integer> getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(List<Integer> seatNumber) {
		this.seatNumber = seatNumber;
	}

	public Ticket() {
		super();
	}

	@DynamoDBHashKey(attributeName = "id")
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
