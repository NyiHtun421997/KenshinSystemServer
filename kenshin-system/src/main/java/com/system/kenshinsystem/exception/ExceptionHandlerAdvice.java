package com.system.kenshinsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
	
	@ExceptionHandler({UsernameNotFoundException.class,BadCredentialsException.class})
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseEntity<?> handleAuthException(){
	return	ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Username or Password is incorrect");
	}
	@ExceptionHandler({NullPointerException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<?> handleNullException(NullPointerException e){
	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Request parameter value is not valid. "+e.getMessage());
	}

}
