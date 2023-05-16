package com.fse.moviebooking.main.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import com.fse.moviebooking.main.model.Movie;

public interface MovieRepository extends MongoRepository<Movie,String>{

	@Query("{_id: ?0},{ theatres: {$elemMatch: {theatreName: ?1}}}")
	Movie findByMovieNameAndTheatreName(String movieName,String theatreName);
	
	@Query("{'_id':?0,'theatres':{ '$elemMatch':{'theatreName':?1} }}")
	@Update("{ $set: { 'theatres.$.noOfTickets': ?2}},{upsert:true }")
	void updateNoOfTickets(String movieName,String theatreName,int tickets);
	
	@Query("{'_id':?0,'theatres':{ '$elemMatch':{'theatreName':?1} }}")
	@Update("{ $set: { 'theatres.$.seatNumbers': ?2}},{upsert:true }")
	void updateSeatNumbers(String movieName,String theatreName,List<Integer> seatNumbers);
	
	@Query("{'_id':?0,'theatres':{ '$elemMatch':{'theatreName':?1} }}")
	@Update("{ $set: { 'theatres.$.status': ?2}},{upsert:true }")
	void updateStatus(String movieName,String theatreName,String status);
	
	@Query("{'_id':?0,'theatres':{ '$elemMatch':{'theatreName':?1} }}")
	@Update("{$pull:{'theatres':{'theatreName':?1}}}")
	void deleteMovieByTheatre(String movieName,String theatreName);
}
