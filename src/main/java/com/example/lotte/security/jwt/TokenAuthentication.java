package com.example.lotte.security.jwt;

import com.example.lotte.enums.user.Role;
import com.example.lotte.security.userDetails.CustomUserDetails;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthentication {

    private final JwtUtil jwtUtil;

    public void authenticateToken(String jwt, HttpServletRequest request) {
        Claims claims = jwtUtil.validateToken(jwt);
        CustomUserDetails userDetails = buildUserDetails(claims);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info("Authenticated: {} ({})",
                userDetails.getUsername(),
                userDetails.getRole());
    }

    public CustomUserDetails buildUserDetails(Claims claims) {
        Long userId = claims.get("userId", Long.class);
        String username = claims.getSubject();
        Role role = Role.valueOf(claims.get("role", String.class));

        return CustomUserDetails.builder()
                .id(userId)
                .username(username)
                .role(role)
                .build();
    }
}
