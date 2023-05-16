package com.fse.moviebooking.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {

	private String secret="sampletest";
	
	private static final Logger log=LoggerFactory.getLogger(JwtUtil.class);
	
	public String extractUsername(String token) throws ExpiredJwtException{
		log.info("Start: Extract Username");
		log.debug("Token {}",token);
		String extractClaim=extractClaim(token,Claims::getSubject);
		log.debug("Extract Claim {}",extractClaim);
		log.info("End: Extract Username");
		return extractClaim;
	}

	private <T>T extractClaim(String token, Function<Claims,T> claimsResolver) throws ExpiredJwtException{
		log.info("Start: Extract Claim");
		final Claims claims=extractAllClaims(token);
		log.debug("Claims {}",claims);
		T apply=claimsResolver.apply(claims);
		log.debug("Apply {}",apply);
		log.info("End: Extract Claim");
		return apply;
	}

	private Claims extractAllClaims(String token) throws ExpiredJwtException{
		log.info("Start: Extract All Claims");
		Claims claims=Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		log.debug("claims {}",claims);
		log.info("End: Extract All Claims");
		return claims;
	}

	

	public Date extractExpiration(String token) throws ExpiredJwtException{
		log.info("In: Extract Expiration");
		return extractClaim(token, Claims::getExpiration);
	}
	
	private Boolean isTokenExpired(String token) throws ExpiredJwtException{
		log.info("In: is Token Expired ");
		final Date expiration = extractExpiration(token);
		log.debug("Expiration {}", expiration);
		return expiration.before(new Date());
	}

	public String generateToken(UserDetails userDetails) {
		log.info("In: Generate Token Start");
		Map<String, Object> claims = new HashMap<>();
		log.debug("claims {}",claims);
		return createToken(claims, userDetails.getUsername());
	}

	private String createToken(Map<String, Object> claims, String username) {
		log.info("In: Create Token Start");
		return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 60*30 * 1000))
				.signWith(SignatureAlgorithm.HS256, secret).compact();
	}
	
	public String generateToken(Authentication authentication) {
		log.info("In: Generate Token");
		String authorities=authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
		return Jwts.builder().setSubject(authentication.getName()).claim("roles", authorities).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 60*30 * 1000))
				.signWith(SignatureAlgorithm.HS256, secret).compact();

	}

	
	public Boolean validateToken(String token, UserDetails userDetails) throws ExpiredJwtException{
		log.info("In: Validate Token");
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	private <T>T extractClaimForExpiry(String token, Function<Claims,T> claimsResolver) throws ExpiredJwtException{
		log.info("Start: Extract Claim for Expiry");
		final Claims claims=Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		T apply=claimsResolver.apply(claims);
		log.info("End: Extract Claim for Expiry");
		return apply;
	}
	
	public Boolean validateToken(String token) throws ExpiredJwtException{
		log.info("Start: Validate Token");
		Date expiryDate=extractClaimForExpiry(token, Claims::getExpiration);
		boolean isTokenExpired=expiryDate.before(new Date());
		log.info("End: Validate Token");
		return !isTokenExpired;
	}
	
	UsernamePasswordAuthenticationToken getAuthenticationToken(final String token,final Authentication existingAuth,final UserDetails userDetails) {
		log.info("In: Get Authentication Token");
		final JwtParser jwtParser=Jwts.parser().setSigningKey(secret);
		final Jws<Claims> claimJws=jwtParser.parseClaimsJws(token);
		final Claims claims=claimJws.getBody();
		final Collection<? extends GrantedAuthority> authorities=Arrays.stream(claims.get("roles").toString().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		return new UsernamePasswordAuthenticationToken(userDetails, "",authorities);
	}
	
	
}
