package com.pm.authservice.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class jwtUtil {

    private final Key secretKey;

    public jwtUtil(@Value("${jwt.secret:230a35dbcfdaa3f5a84acb1adcf1b4412aa13697}") String secret) {

        log.info("Initializing JwtUtil");

        if (secret == null) {
            log.error("JWT_SECRET is missing or empty");
            throw new IllegalStateException("JWT_SECRET is missing or empty");
        }

        log.info("JWT_SECRET received (length = {})", secret.length());

        // Convert ANY string directly to bytes
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);

        log.info("Derived key length = {} bytes", keyBytes.length);

        // HS256 requires at least 32 byte

        this.secretKey = Keys.hmacShaKeyFor(keyBytes);

        log.info("JwtUtil initialized successfully");
    }

    public String generateToken(String email, String role) {
        log.info("Generating JWT for email={}", email);

        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 10))
                .signWith(secretKey)
                .compact();
    }

    public void validateToken(String token) {
        try {
            log.info("Validating JWT");

            Jwts.parser()
                    .verifyWith((SecretKey) secretKey)
                    .build()
                    .parseSignedClaims(token);

        } catch (JwtException e) {
            log.error("Invalid JWT", e);
            throw new JwtException("Invalid JWT");
        }
    }
}
