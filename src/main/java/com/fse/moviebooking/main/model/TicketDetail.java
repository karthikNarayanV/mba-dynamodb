package com.fse.moviebooking.main.model;

public class TicketDetail {
	
	private String movieName;
	private String theatreName;
	private int available;
	private int booked;
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
	public int getAvailable() {
		return available;
	}
	public void setAvailable(int available) {
		this.available = available;
	}
	public int getBooked() {
		return booked;
	}
	public void setBooked(int booked) {
		this.booked = booked;
	}
	@Override
	public String toString() {
		return "TicketDetail [movieName=" + movieName + ", theatreName=" + theatreName + ", available=" + available
				+ ", booked=" + booked + "]";
	}
	public TicketDetail(String movieName, String theatreName, int available, int booked) {
		super();
		this.movieName = movieName;
		this.theatreName = theatreName;
		this.available = available;
		this.booked = booked;
	}
	public TicketDetail() {
		super();
	}
	
	

}
