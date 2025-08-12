package com.hourlyrecruit.security;


import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hourlyrecruit.entity.User;
import com.hourlyrecruit.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

    // ✅ Step 1: Skip JWT check for login/register endpoints
        String path = request.getServletPath();
        if (path.equals("/api/auth/login") || path.equals("/api/auth/register")) {
            filterChain.doFilter(request, response);
            return;
        }


        // Get the Authorization header from the request
        String authHeader = request.getHeader("Authorization");

                String token = null;
                String email = null;

        // Check if the header is present and starts with "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); // Extract token (remove "Bearer ")
            email = jwtUtil.extractEmail(token); // extract email from token
        }

        // If we have an email and no authentication is yet set
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Load user from DB
            User user = userRepository.findByEmail(email).orElse(null);

            // If token is valid for this user
            if (user != null && jwtUtil.isTokenValid(token, email)) {

                                // Create authentication token manually
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(user,null,Collections.singleton(new SimpleGrantedAuthority("ROLE_" +user.getRole().name())));

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication in security context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Pass the request to the next filter
        filterChain.doFilter(request, response);
    }

}

