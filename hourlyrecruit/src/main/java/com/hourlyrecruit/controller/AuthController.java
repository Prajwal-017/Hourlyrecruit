package com.hourlyrecruit.controller;



import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hourlyrecruit.security.Role;
import com.hourlyrecruit.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // 🟢 Register endpoint
    @PostMapping("/register")
    public String registerUser(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String email = body.get("email");
        String password = body.get("password");
        Role role = Role.valueOf(body.get("role").toUpperCase());

        return authService.register(username, email, password, role);
    }

    // 🔵 Login endpoint
    @PostMapping("/login")
    public String loginUser(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        return authService.login(email, password);
    }
}

