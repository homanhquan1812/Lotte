package com.example.lotte.dto.document.request;

import com.example.lotte.enums.document.Status;
import jakarta.validation.constraints.NotBlank;

public record CreateDocumentRequestDto(
        @NotBlank(message = "code is required")
        String code,

        @NotBlank(message = "title is required")
        String title,

        String description,

        @NotBlank(message = "category is required")
        String category,

        Status status
) {}