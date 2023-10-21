package com.system.kenshinsystem.security.controller;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class RegisterRequest {

	private String username;
	private String password;
	private String role;
	
	RegisterRequest(String username,String password,String role){
		this.username = username;
		this.password = password;
		this.role = role.toUpperCase();
	}
}
