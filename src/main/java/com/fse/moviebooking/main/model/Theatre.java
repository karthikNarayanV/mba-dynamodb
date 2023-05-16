package com.fse.moviebooking.main.model;

import java.util.List;

public class Theatre {
	
	private String theatreName;
	private int noOfTickets;
	private String status;
	private List<Integer> seatNumbers;
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Integer> getSeatNumbers() {
		return seatNumbers;
	}
	public void setSeatNumbers(List<Integer> seatNumbers) {
		this.seatNumbers = seatNumbers;
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
	public Theatre(String theatreName, int noOfTickets) {
		super();
		this.theatreName = theatreName;
		this.noOfTickets = noOfTickets;
	}
	public Theatre() {
		super();
	}
	
	

}
