package com.fse.moviebooking.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fse.moviebooking.exception.UserNotFoundException;
import com.fse.moviebooking.model.UserCredential;
import com.fse.moviebooking.repository.UserCredentialRepository;

@Service
public class UserCredentialServiceImpl implements UserDetailsService,UserCredentialService{

	@Autowired
    private UserCredentialRepository userCredentialRepository;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	private  static final Logger log = LoggerFactory.getLogger(UserCredentialServiceImpl.class);

	@Override
	public String forgotPassword(String email) {
		log.info("Start: Forgot Password");
		Optional<UserCredential> result=userCredentialRepository.findById(email);
		if(result.isEmpty()) {
			log.info("End: Forgot Password");
			return "Invalid email";
		}
		UserCredential userCredential=result.get();
		final UserDetails userDetails=new User(userCredential.getEmail(), userCredential.getPassword(), new ArrayList<>());
		userCredential.setResetPasswordToken(jwtUtil.generateToken(userDetails));
		userCredentialRepository.save(userCredential);
		log.info("End: Forgot Password");
		return userCredential.getResetPasswordToken();
	}

	@Override
	public String resetPassword(String token, String password) {
		log.info("Start: Reset Password");
		Optional<UserCredential> result=Optional.ofNullable(userCredentialRepository.findByResetPasswordToken(token));
		if(result.isEmpty()) {
			log.error("Invalid Token");
			log.info("End: Reset Password");
			return "False Invalid Token";
		}
		UserCredential userCredential=result.get();
		userCredential.setPassword(password);
		userCredential.setResetPasswordToken(null);
		userCredentialRepository.save(userCredential);
		log.info("End: Reset Password");
		return "Your Password Saved Successfully";
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("In: Load User by Username");
		UserCredential userCredential=userCredentialRepository.findById(username).orElseThrow(()->new UserNotFoundException("YOU ARE NOT AN AUTHENTICATED USER."));
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        userCredential.getUserRoles()
          .forEach(role -> {
              grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
          });
		return new User(userCredential.getEmail(),userCredential.getPassword(),grantedAuthorities );
	}

	@Override
	public String saveUser(UserCredential userCredential) {
		log.info("Start: Save Credential");
		userCredentialRepository.save(userCredential);
		String result=userCredentialRepository.save(userCredential).toString();
		log.info("End: Save Credential");
		return result;
	}
}
