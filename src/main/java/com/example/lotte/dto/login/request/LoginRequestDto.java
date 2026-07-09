package com.example.lotte.dto.login.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Schema(
        name = "Login Request",
        description = "Payload to log in",
        requiredProperties = {
                "username", "password"
        }
)
@Builder
public record LoginRequestDto(
        @Schema(
                description = "Username",
                example = "user123"
        )
        @NotBlank(message = "Username is required")
        String username,

        @Schema(
                description = "Plain password",
                example = "password123"
        )
        @NotBlank(message = "Password is required")
        String password
) {}