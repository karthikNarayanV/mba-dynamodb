package com.fse.moviebooking.service;

import com.fse.moviebooking.model.UserCredential;

public interface UserCredentialService {
	String forgotPassword(String email);
	String resetPassword(String token,String password);
	String saveUser(UserCredential user);
}
