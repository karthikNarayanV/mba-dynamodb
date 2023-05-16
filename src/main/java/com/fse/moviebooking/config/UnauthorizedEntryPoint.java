package com.fse.moviebooking.config;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


@Component
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint, Serializable {
	
	private static final Logger log=LoggerFactory.getLogger(UnauthorizedEntryPoint.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		log.debug("UnauthorizedEntryPoint Start");
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized");
		log.debug("UnauthorizedEntryPoint End");

	}

}
