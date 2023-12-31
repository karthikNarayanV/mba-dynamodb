package com.fse.moviebooking.repository;

import com.fse.moviebooking.model.User;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface UserRepository extends CrudRepository<User,String>{
	User findByEmail(String email);
}
