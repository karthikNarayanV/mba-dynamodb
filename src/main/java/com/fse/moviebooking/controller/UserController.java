package com.fse.moviebooking.controller;

import com.fse.moviebooking.main.input.UserInput;
import com.fse.moviebooking.model.LoginReturn;
import com.fse.moviebooking.model.PasswordCredential;
import com.fse.moviebooking.model.Role;
import com.fse.moviebooking.model.User;
import com.fse.moviebooking.model.UserCredential;

import com.fse.moviebooking.service.JwtUtil;
import com.fse.moviebooking.service.UserCredentialServiceImpl;
import com.fse.moviebooking.service.UserServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
public class UserController {

    @Autowired
    UserServiceImpl userService;
    
    @Autowired
    UserCredentialServiceImpl userCredentialService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private AuthenticationManager authenticationManager; 
    
    private static final Logger log=LoggerFactory.getLogger(UserController.class);

    @PostMapping(value="/register")
    public ResponseEntity<?> register(@RequestBody UserInput userInput){
    	log.info("Start: Registration");
        int min=100000;
        int max=999999;
        Random random1 = new Random();
        User user= new User(userInput.getFirstName(),userInput.getLastName(),userInput.getEmail(),"",userInput.getPassword(),userInput.getContactNumber());
        while(true) {
        	try {
        		int loginId = random1.nextInt(max - min) + min;
                user.setLoginId(String.valueOf(loginId));
                userService.saveUser(user);
                
        	}catch(Exception e) {
        		if(e.getLocalizedMessage().contains("email")) {
        			log.error("Email Already exists");
        			return new ResponseEntity<>("Email Already exists",HttpStatus.BAD_REQUEST);
        		}
        		else {
        			log.error(e.getLocalizedMessage());
        		}
     
        	}
        	Set<Role> roleset=new HashSet<>();
            Iterator<String> iterator=userInput.getUserRoles().iterator();
            while(iterator.hasNext()) {
            	Role role=new Role(iterator.next());
            	roleset.add(role);
            }
            UserCredential userCredential=new UserCredential(user.getEmail(), user.getPassword(), "",roleset);
            userCredentialService.saveUser(userCredential);
            String token;
			try {
				token = authenticate(user.getEmail(), user.getPassword());
				log.debug("User {}", user.getEmail());
				UserDetails userDetails=userCredentialService.loadUserByUsername(user.getEmail());
	    		 Set<String> roles=new HashSet<>();
	    		 for (GrantedAuthority authority : userDetails.getAuthorities()) {
	    		      roles.add(authority.getAuthority());
	    		    }
	    		 LoginReturn result=new LoginReturn(token,roles);
	    		 log.info("End: Login");
	    	    return new ResponseEntity<>(result,HttpStatus.OK);
			} catch (Exception e) {
				log.error(e.getLocalizedMessage());
	    		return new ResponseEntity<>("Not a valid user",HttpStatus.BAD_REQUEST);
			}
            
        }

    }
    
   
    private String authenticate(String email,String password) throws Exception{
    	
    	log.info("Start: Authenticate");
    	log.debug("Username {}",email);
    	try {
    		final Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    		SecurityContextHolder.getContext().setAuthentication(authentication);
    		final String token =jwtUtil.generateToken(authentication);
    		log.info("End: Authenticate");
    		return token;
    	}
    	catch(Exception e) {
    		log.error(e.getMessage());
    		throw new Exception(e);
    	}
    }
    
    @PostMapping(value="/login")
    public ResponseEntity<?> login(@RequestBody UserCredential credential){
    	log.info("Start: Login");
    	
    	log.debug("Username {}",credential.getEmail());
    	String token=null;
    	try {
    		 token=authenticate(credential.getEmail(),credential.getPassword());
    		 UserDetails user=userCredentialService.loadUserByUsername(credential.getEmail());
    		 Set<String> roles=new HashSet<>();
    		 for (GrantedAuthority authority : user.getAuthorities()) {
    		      roles.add(authority.getAuthority());
    		    }
    		 LoginReturn result=new LoginReturn(token,roles);
    		 log.info("End: Login");
    	    return new ResponseEntity<>(result,HttpStatus.OK);
    		 
    	}
    	catch(Exception e) {
    		log.error(e.getLocalizedMessage());
    		return new ResponseEntity<>("Not a valid user",HttpStatus.BAD_REQUEST);
    	}
    	
    }
    
    @GetMapping(value="/validate")
    public ResponseEntity<?> validate(@RequestHeader("Authorization") final String token){
    	log.info("Start: Validate");
    	String newToken=token.substring(7);
    	log.debug("Token {}",newToken);
    	try {
    		if(jwtUtil.validateToken(newToken)) {
    			log.info("End: Validate");	
    			return new ResponseEntity<>(true,HttpStatus.OK);
    		}
    		else {
    			log.error("Token validation is failed");
    			return new ResponseEntity<>(false,HttpStatus.MULTI_STATUS);
    		}
    	}
    	catch(Exception e) {
    		log.error(e.getLocalizedMessage());
    		return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
    	}
    	
    }
    
    @PostMapping(value="/forgot")
    public ResponseEntity<?> forgot(@RequestBody String username){
    	log.info("Start: Forgot Password");
    	log.debug("Username {}",username);
    	String response=userCredentialService.forgotPassword(username);
    	if(!response.startsWith("Invalid")) {
    		response= "true "+response;
    	}
    	else {
    		log.info("End: Forgot Password");
    		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    	}
    	log.info("End: Forgot Password");
    	return new ResponseEntity<>(response,HttpStatus.OK);
    }
    
    @PostMapping(value="/reset")
    public ResponseEntity<?> reset(@RequestBody PasswordCredential credential){
    	log.info("Start: Reset Password");
    	
    	String message=userCredentialService.resetPassword(credential.getToken(), credential.getPassword());
    	if(message.contains("Invalid")) {
    		log.info("End: Forgot Password");
    		return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
    	}
    	log.info("End: Forgot Password");
    	return new ResponseEntity<>(message,HttpStatus.OK);
    }
    
    
    
    

}
