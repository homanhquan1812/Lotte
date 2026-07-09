package com.example.lotte.dto.document.request;

import com.example.lotte.enums.document.Status;

public record UpdateDocumentRequestDto(
        String title,
        String description,
        String category,
        Status status
) {
}
