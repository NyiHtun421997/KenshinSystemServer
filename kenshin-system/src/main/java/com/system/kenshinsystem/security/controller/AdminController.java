package com.system.kenshinsystem.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "api/kenshin/secure")
@RequiredArgsConstructor
public class AdminController {
	private final AuthenticationService service;
	
	@GetMapping("/admin/getinfo")
	public ResponseEntity<String> enquire(
			@RequestParam(name = "user_name",required = false)String username){
	return ResponseEntity.ok(service.getInfo(username));
	}
	@PostMapping("/admin/signin")
	public ResponseEntity<String> register(
			@RequestBody RegisterRequest request){
	return	ResponseEntity.ok(service.register(request));
	}
	@PutMapping("/admin/update")
	public ResponseEntity<String> update(
			@RequestParam(name = "user_name",required = false)String oldUsername,
			@RequestBody RegisterRequest request){
	return	ResponseEntity.ok(service.update(request,oldUsername));
	}
	@DeleteMapping("/admin/delete")
	public ResponseEntity<String> delete(
			@RequestParam(name = "user_name",required = false)String username){
	return ResponseEntity.ok(service.delete(username));
	}
	@PostMapping("/login")
	public ResponseEntity<String> authenticate(
			@RequestBody AuthenticationRequest request){
	return ResponseEntity.ok(service.authenticate(request));
	}
	

}
