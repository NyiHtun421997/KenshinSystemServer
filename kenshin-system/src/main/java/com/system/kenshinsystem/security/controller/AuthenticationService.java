package com.system.kenshinsystem.security.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.system.kenshinsystem.security.config.JwtService;
import com.system.kenshinsystem.security.users.Role;
import com.system.kenshinsystem.security.users.User;
import com.system.kenshinsystem.security.users.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authManager;
	private final JwtService jwtService;
	
	public String register(RegisterRequest request) {
		
		Role role = Role.USER;
		if(request.getRole().equals("ADMIN"))role = Role.ADMIN;
		if(request.getRole().equals("MANAGER"))role = Role.MANAGER;
		var user = User.builder()
			.username(request.getUsername())
			.password(passwordEncoder.encode(request.getPassword()))
			.role(role)
			.build();
		repository.save(user);
		return "Registered successfully.";
	}
	public String authenticate(AuthenticationRequest request) {
		
		authManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getUsername(),
						request.getPassword()));
		var user = this.repository.findByUsername(request.getUsername())
				.orElseThrow(()->new UsernameNotFoundException(""));
		return jwtService.generateToken(user);
	}
	public String update(RegisterRequest request,String oldUsername) {
		Role role = Role.USER;
		if(request.getRole().equals("ADMIN"))role = Role.ADMIN;
		if(request.getRole().equals("MANAGER"))role = Role.MANAGER;
		var user = this.repository.findByUsername(oldUsername)
						.orElseThrow(()->new UsernameNotFoundException(""));
		user.setUsername(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(role);
		repository.save(user);
		return "Update user successfully.";
	}
	public String getInfo(String username) {
		var user = this.repository.findByUsername(username)
				.orElseThrow(()->new UsernameNotFoundException(""));
		return "UserId: "+user.getId()+"\nUsername: "+user.getUsername()
				+"\nRole: "+user.getRole();
	}
	public String delete(String username) {
		var user = this.repository.findByUsername(username)
				.orElseThrow(()->new UsernameNotFoundException(""));
		repository.delete(user);
		return "User deleted successfully.";
	}
}
