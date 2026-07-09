package com.example.lotte.service;

import com.example.lotte.dto.common.PageResponse;
import com.example.lotte.dto.user.request.CreateUserRequestDto;
import com.example.lotte.dto.user.request.UpdateUserRequestDto;
import com.example.lotte.dto.user.response.UserResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    PageResponse<UserResponseDto> getPage(Pageable pageable);
    UserResponseDto getById(Long userId);
    UserResponseDto create(CreateUserRequestDto requestDto);
    UserResponseDto update(Long userId, UpdateUserRequestDto requestDto);
    void delete(Long userId);
}
