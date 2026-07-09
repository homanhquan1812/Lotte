package com.example.lotte.service.impl;

import com.example.lotte.dto.login.request.LoginRequestDto;
import com.example.lotte.dto.login.response.LoginResponseDto;
import com.example.lotte.security.authentication.UsernamePasswordAuthentication;
import com.example.lotte.security.jwt.JwtUtil;
import com.example.lotte.security.userDetails.CustomUserDetails;
import com.example.lotte.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final UsernamePasswordAuthentication authentication;

    @Override
    @Transactional
    public LoginResponseDto login(LoginRequestDto requestDto) {
        CustomUserDetails userDetails = authentication.authenticate(requestDto);
        String accessToken = jwtUtil.generateToken(userDetails);

        return LoginResponseDto.builder()
                .id(userDetails.getId())
                .accessToken(accessToken)
                .username(userDetails.getUsername())
                .role(userDetails.getRole())
                .build();
    }
}
