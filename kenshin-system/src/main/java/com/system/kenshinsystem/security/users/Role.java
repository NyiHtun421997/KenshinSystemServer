package com.system.kenshinsystem.security.users;

public enum Role {
	
	USER("USER"),
	MANAGER("MANAGER"),
	ADMIN("ADMIN");
	final String value;
	Role(String value){
		this.value = value;
	}
	@Override
	public String toString() {
		return value;
	}
}
