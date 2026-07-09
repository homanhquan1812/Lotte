package com.example.lotte.projection;

import com.example.lotte.enums.document.Status;

public interface StatusCountProjection {
    Status getStatus();
    Long getCount();
}