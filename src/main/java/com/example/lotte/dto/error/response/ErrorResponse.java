package com.example.lotte.dto.error.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        Boolean success,
        String message,
        Integer status,
        LocalDateTime timestamp,
        String path,
        Map<String, String> errors
) {
}
