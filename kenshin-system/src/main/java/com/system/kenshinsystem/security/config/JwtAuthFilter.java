package com.system.kenshinsystem.security.config;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.system.kenshinsystem.security.users.User;
import com.system.kenshinsystem.security.users.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

//The class that will filter http requests with JwTokens and
//check and validate those tokens
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter{

	private final JwtService jwtService;
	private final UserRepository repository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//extract authorizatiuon header from tokens of http requests
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String username;
		//if auth header is null, move on to next filter
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		jwt = authHeader.substring(7);
		//this jwt needs to be extracted and validated
		//call the service methods that will deal with tokens
		username = jwtService.extractUsername(jwt);
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			//since username is currently not authenticated already,needs to update security context holder for this username
			Optional<User> user = repository.findByUsername(username);
			if(user.isPresent() && !jwtService.isTokenExpired(jwt)) {
				//SecurityContextHolder requires the following token
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
						user.get(),
						null,
						user.get().getAuthorities());
				authToken.setDetails(
					new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
	}
	

}
