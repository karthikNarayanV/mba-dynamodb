package com.fse.moviebooking.repository;


import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.fse.moviebooking.model.UserCredential;


@EnableScan
public interface UserCredentialRepository extends CrudRepository<UserCredential,String>{
	UserCredential findByResetPasswordToken(String resetPasswordToken);
}
