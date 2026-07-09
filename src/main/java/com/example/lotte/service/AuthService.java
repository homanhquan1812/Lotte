package com.example.lotte.service;

import com.example.lotte.dto.login.request.LoginRequestDto;
import com.example.lotte.dto.login.response.LoginResponseDto;

public interface AuthService {

    LoginResponseDto login(LoginRequestDto loginRequestDto);
}
