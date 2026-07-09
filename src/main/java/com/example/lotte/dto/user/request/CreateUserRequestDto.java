package com.example.lotte.dto.user.request;

import com.example.lotte.enums.user.Role;

public record CreateUserRequestDto(
        String username,
        String password,
        Role role
) {
}
