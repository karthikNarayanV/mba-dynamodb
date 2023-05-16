package com.fse.moviebooking.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fse.moviebooking.model.UserCredential;



public interface UserCredentialRepository extends MongoRepository<UserCredential,String>{
	UserCredential findByResetPasswordToken(String resetPasswordToken);
}
