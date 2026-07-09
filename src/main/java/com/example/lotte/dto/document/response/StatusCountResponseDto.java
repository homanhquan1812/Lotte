package com.example.lotte.dto.document.response;

import com.example.lotte.enums.document.Status;

public record StatusCountResponseDto(
        Status status,
        Long count
) {
}
