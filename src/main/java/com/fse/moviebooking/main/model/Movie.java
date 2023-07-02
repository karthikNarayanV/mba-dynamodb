package com.fse.moviebooking.main.model;

import java.util.List;

import org.springframework.data.annotation.Id;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperFieldModel;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTyped;

@DynamoDBTable(tableName ="Movie")

public class Movie {
	
	private String movieName;
	
	private List<Theatre> theatres;

	@DynamoDBHashKey(attributeName = "id")
	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	@DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.L)
	@DynamoDBAttribute(attributeName = "theatres")
	public List<Theatre> getTheatres() {
		return theatres;
	}

	public void setTheatres(List<Theatre> theatres) {
		this.theatres = theatres;
	}

	public Movie(String movieName, List<Theatre> theatres) {
		super();
		this.movieName = movieName;
		this.theatres = theatres;
	}

	public Movie() {
		
	}

	@Override
	public String toString() {
		return "Movie [movieName=" + movieName + ", theatres=" + theatres + "]";
	}
	
	
}
