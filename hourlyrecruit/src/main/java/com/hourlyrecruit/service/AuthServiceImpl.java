package com.hourlyrecruit.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hourlyrecruit.entity.User;
import com.hourlyrecruit.repository.UserRepository;
import com.hourlyrecruit.security.JwtUtil;
import com.hourlyrecruit.security.Role;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String register(String username, String email, String password, Role role) {
        if (userRepo.findByEmail(email).isPresent()) {
            return "User already exists with this email";
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);

        userRepo.save(user);
        return "User registered successfully";
    }

    @Override
    public String login(String email, String password) {
        Optional<User> userOpt = userRepo.findByEmail(email);

        if (userOpt.isEmpty()) {
            return "User not found";
        }

        User user = userOpt.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return "Invalid credentials";
        }

        String token = jwtUtil.generateToken(email); // Return JWT on successful login
        
        return "{ \"message\": \"Login Successful\", \"token\": \"" + token + "\" }";
    }
}
