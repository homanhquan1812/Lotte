package com.example.lotte.projection;

import com.example.lotte.enums.document.Status;

import java.time.LocalDateTime;

public interface DocumentProjection {
    Long getId();
    String getCode();
    String getTitle();
    String getDescription();
    String getCategory();
    Status getStatus();
    LocalDateTime getCreatedAt();
    Long getCreatedBy();
    String getFileName();
}