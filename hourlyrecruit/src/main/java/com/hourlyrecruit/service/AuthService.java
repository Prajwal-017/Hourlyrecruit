package com.hourlyrecruit.service;

import com.hourlyrecruit.security.Role;

public interface AuthService {
	
	String register(String username, String email, String password, Role role);
	
	String login(String email, String password);

}
