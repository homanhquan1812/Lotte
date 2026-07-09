package com.example.lotte.dto.login.response;

import com.example.lotte.enums.user.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(
        name = "Login Response",
        description = "Payload for login response",
        requiredProperties = {
                "id", "accessToken", "refreshToken", "username", "lastLogin"
        }
)
@Builder
public record LoginResponseDto(
        @Schema(description = "User ID", example = "1")
        Long id,

        @Schema(description = "JWT access token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String accessToken,

        @Schema(description = "Username", example = "user123")
        String username,

        @Schema(description = "Role of user logging in", example = "ADMIN")
        Role role
) {
}
