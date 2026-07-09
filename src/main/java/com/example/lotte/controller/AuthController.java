package com.example.lotte.controller;

import com.example.lotte.dto.login.request.LoginRequestDto;
import com.example.lotte.dto.login.response.LoginResponseDto;
import com.example.lotte.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "Authentication APIs")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // [POST] /api/v1/auth/login
    @Operation(summary = "User login")
    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody @Valid LoginRequestDto requestDto) {
        return authService.login(requestDto);
    }
}
