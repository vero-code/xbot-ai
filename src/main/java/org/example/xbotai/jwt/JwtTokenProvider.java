package org.example.xbotai.jwt;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.JwtException;

@Slf4j
@Component
public class JwtTokenProvider {

    private Key key;

    private final long jwtExpirationMs = 86400000;

    @Value("${jwt.secret}")
    private String secret;

    @PostConstruct
    public void init() {
        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException("Error: Secret key not loaded.");
        }

        byte[] keyBytes = Base64.getDecoder().decode(secret.trim());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Generate JWT token.
     */
    public String generateToken(Authentication authentication) {
        User userPrincipal = (User) authentication.getPrincipal();

        Instant now = Instant.now();
        Instant expiration = now.plus(jwtExpirationMs, ChronoUnit.MILLIS);

        return Jwts.builder()
                .subject(userPrincipal.getUsername())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(key)
                .compact();
    }

    /**
     * Extracts the token from the Authorization header.
     */
    public String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    /**
     * Get username from token.
     */
    public String getUsernameFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (JwtException e) {
            log.error("Error extracting username from token: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Check the validity of the token.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            log.error("JWT validation error: {}", e.getMessage());
        }
        return false;
    }
}