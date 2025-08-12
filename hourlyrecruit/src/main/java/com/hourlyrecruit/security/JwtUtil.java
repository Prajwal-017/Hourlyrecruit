package com.hourlyrecruit.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

import javax.crypto.SecretKey;


@Component
public class JwtUtil {

    // Secret key for signing the token
	
	private final SecretKey SECRET_KEY;
	
	
//    private SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256); // You can change it
	
	
    // 10 hours expiration in milliseconds
    private long EXPIRATION_TIME = 10 * 60 * 60 * 1000;
    
    
 // ✅ Constructor to load key from application.properties
    public JwtUtil(@Value("${jwt.secret}") String Secret) {
    	this.SECRET_KEY = Keys.hmacShaKeyFor(Secret.getBytes());
    }
    
    
    


// Generate Token
    public String generateToken(String email) {

        // Current time
        long currentTimeMillis = System.currentTimeMillis();

        // Expiration time
        long expirationTime = currentTimeMillis + EXPIRATION_TIME;

        // Create token manually using builder pattern
        String token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(new Date(expirationTime))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        return token;
    }

    // =======================
    // 2. Extract email
    // =======================
    public String extractEmail(String token) {
        Claims claims = getAllClaims(token);
        String email = claims.getSubject(); // subject = email
        return email;
    }

    // =======================
    // 3. Validate token
    // =======================
        public boolean isTokenValid(String token, String userEmail) {
    String extractedEmail = extractEmail(token);

    boolean sameEmail = extractedEmail != null && extractedEmail.equals(userEmail);
    boolean notExpired = !isTokenExpired(token);

    return sameEmail && notExpired;

    }

    // =======================
    // 4. Expiration Check
    // =======================
    private boolean isTokenExpired(String token) {
        Claims claims = getAllClaims(token);
        Date expirationDate = claims.getExpiration();

        Date currentDate = new Date();
        return expirationDate.before(currentDate);
    }

    // =======================
    // 5. Extract All Claims
    // =======================
    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    
}

