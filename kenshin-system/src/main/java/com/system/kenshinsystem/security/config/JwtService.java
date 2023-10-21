package com.system.kenshinsystem.security.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

//The class that contains methods that will deal with JwTokens
@Service 
public class JwtService {
	
	private static final String SecretKey = "3419307D818FD3B5F382094D0598DC6F83C4E969268F0B412542B01DF3847A46";

	public String extractUsername(String jwt) {
		
		return extractClaim(jwt,Claims::getSubject);
	}
	
	public boolean isTokenExpired(String jwt) {
		
		return extractExpireDate(jwt).before(new Date());
	}
	
	public String generateToken(UserDetails user) {
		Map<String,Object> extraClaims = new HashMap<>();
		return Jwts.builder()
			.setClaims(extraClaims)
			.setSubject(user.getUsername())
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis()+1000*60*180))
			.signWith(getSigningKey(),SignatureAlgorithm.HS256)
			.compact();
	}
	
	//a method to extract all claims from a token
	private Claims extractAllClaims(String jwt) {
		
		return Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(jwt)
				.getBody();		
	}
	
	//a method to extract a single claim with generic arg
	private <T>T extractClaim(String jwt,Function<Claims,T> f){
		//methods's arg expects a jwt and a method ref that will extract info from Claims obj
		final Claims claims = extractAllClaims(jwt);
		return f.apply(claims);
	}
	private Date extractExpireDate(String jwt) {
		
		return extractClaim(jwt,Claims::getExpiration);
	}
	
	private Key getSigningKey() {
		
		byte[] key = Decoders.BASE64.decode(SecretKey);
		return Keys.hmacShaKeyFor(key);
	}

}
