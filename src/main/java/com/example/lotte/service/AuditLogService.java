package com.example.lotte.service;

import com.example.lotte.enums.auditLog.Action;
import com.example.lotte.enums.auditLog.AuditStatus;

public interface AuditLogService {

    void log(Action action, Long targetId, AuditStatus auditStatus, String message);
}
