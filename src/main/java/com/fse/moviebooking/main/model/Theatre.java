package com.fse.moviebooking.main.model;

import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperFieldModel;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTyped;

@DynamoDBDocument
public class Theatre {
	
	private String theatreName;
	private int noOfTickets;
	private String status;
	private List<Integer> seatNumbers;
	
	@DynamoDBAttribute(attributeName = "status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	@DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.L)
	@DynamoDBAttribute(attributeName = "seatNumbers")
	public List<Integer> getSeatNumbers() {
		return seatNumbers;
	}
	public void setSeatNumbers(List<Integer> seatNumbers) {
		this.seatNumbers = seatNumbers;
	}
	
	@DynamoDBAttribute(attributeName = "theatreName")
	public String getTheatreName() {
		return theatreName;
	}
	public void setTheatreName(String theatreName) {
		this.theatreName = theatreName;
	}
	
	@DynamoDBAttribute(attributeName = "noOfTickets")
	public int getNoOfTickets() {
		return noOfTickets;
	}
	public void setNoOfTickets(int noOfTickets) {
		this.noOfTickets = noOfTickets;
	}
	
	public Theatre(String theatreName, int noOfTickets, String status, List<Integer> seatNumbers) {
		super();
		this.theatreName = theatreName;
		this.noOfTickets = noOfTickets;
		this.status = status;
		this.seatNumbers = seatNumbers;
	}
	public Theatre() {
		
	}
	@Override
	public String toString() {
		return "Theatre [theatreName=" + theatreName + ", noOfTickets=" + noOfTickets + ", status=" + status
				+ ", seatNumbers=" + seatNumbers + "]";
	}
	
	
	
	

}
