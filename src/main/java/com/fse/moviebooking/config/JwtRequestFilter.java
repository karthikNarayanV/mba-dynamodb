package com.fse.moviebooking.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fse.moviebooking.service.JwtUtil;
import com.fse.moviebooking.service.UserCredentialServiceImpl;

@Component
public class JwtRequestFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserCredentialServiceImpl userCredentialService;

	private static final Logger log=Logger.getLogger(JwtRequestFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.log(Level.INFO, "JwtRequestFilter Start");
		log.log(Level.INFO, filterChain);
		log.log(Level.INFO,request);
		log.log(Level.INFO,response);
		final String authHeader=request.getHeader("Authorization");
		log.log(Level.INFO,authHeader);
		String username=null;
		String jwt=null;
		if(authHeader!=null&&authHeader.startsWith("Bearer ")) {
			jwt=authHeader.substring(7);
			log.log(Level.INFO,jwt);
			try {
				username=jwtUtil.extractUsername(jwt);
				log.log(Level.INFO,username);
			}catch(Exception e) {
				log.error(e.getMessage());
			}
		}
		if(username!=null&&SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails=this.userCredentialService.loadUserByUsername(username);
			try {
				boolean valid=jwtUtil.validateToken(jwt, userDetails);
				if(valid) {
					log.log(Level.INFO,"Token is valid");
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
					usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
				
			}catch(Exception e) {
				log.error(e.getMessage());
			}
		}
		filterChain.doFilter(request, response);
		log.log(Level.INFO,"JwtRequestFilter End");
		
	}

}
