package com.fse.moviebooking.service;



import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.fse.moviebooking.model.User;
import com.fse.moviebooking.repository.UserRepository;
import com.mongodb.MongoWriteException;

@Service
public class UserServiceImpl implements UserService {
 
	@Autowired
	private UserRepository userRepository;
	
	private  static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public String saveUser(User user) throws Exception {
		log.info("Start: Save User");
		try {
			
			String result=userRepository.insert(user).toString();
			log.info("End: Save User");
			return result;
		}
		catch (MongoWriteException e) {
			log.error("User Email Already exists or Re fetching login ID");
			log.info("End: Save User");
			throw new Exception();
		}
	}

	@Override
	public User getUser(String email) {
		log.info("Start: Get User");
		Optional<User> result=Optional.ofNullable(userRepository.findByEmail(email));
		if(result.isEmpty()) {
			log.error("No user");
			log.info("End: Get User");
			return null;
		}
		log.info("End: Get User");
		return result.get();
	}
	
	
	
	
}
