package com.example.lotte.security.jwt;

import com.example.lotte.security.userDetails.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * - generateToken(): Used by Login Service, generates a token based on information from CustomUserDetails after successful authentication.
 * - validateToken(): Used by JWT Authentication Filter, validate the token and returns Claims for building Authentication object.
 */
@Slf4j
@Component
public class JwtUtil {

    private final SecretKey key;
    private final long expiration;

    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expiration
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
    }

    public String generateToken(CustomUserDetails customUserDetails) {
        Date now = new Date();
        return Jwts.builder()
                .subject(customUserDetails.getUsername())
                .claim("userId", customUserDetails.getId())
                .claim("role", customUserDetails.getRole().name())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expiration))
                .signWith(key)
                .compact();
    }

    public Claims validateToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            log.error("Token has expired: {}", e.getMessage());
            throw new CredentialsExpiredException("Token has expired", e);
        } catch (Exception e) {
            log.error("Invalid token: {}", e.getMessage());
            throw new BadCredentialsException("Invalid token", e);
        }
    }
}