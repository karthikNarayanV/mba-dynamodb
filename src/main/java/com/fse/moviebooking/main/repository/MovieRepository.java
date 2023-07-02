package com.fse.moviebooking.main.repository;


import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/*import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;*/

import com.fse.moviebooking.main.model.Movie;

@EnableScan
public interface MovieRepository extends CrudRepository<Movie,String>{

}
