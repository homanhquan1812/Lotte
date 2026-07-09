package com.example.lotte.dto.document.response;

import com.example.lotte.enums.document.Status;

import java.time.LocalDateTime;

public record DocumentResponseDto(
        Long id,
        String code,
        String title,
        String description,
        String category,
        Status status,
        LocalDateTime createdAt,
        Long createdBy,
        String fileName
) {}