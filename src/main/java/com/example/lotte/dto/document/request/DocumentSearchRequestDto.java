package com.example.lotte.dto.document.request;

import com.example.lotte.enums.document.Status;

public record DocumentSearchRequestDto(
        String code,
        String title,
        Status status,
        String category,
        Long createdBy
) {}
