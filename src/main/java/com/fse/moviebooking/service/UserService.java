package com.fse.moviebooking.service;

import com.fse.moviebooking.model.User;

public interface UserService {
	String saveUser(User user) throws Exception;
	User getUser(String email);
}
