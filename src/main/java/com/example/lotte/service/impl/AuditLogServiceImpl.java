package com.example.lotte.service.impl;

import com.example.lotte.entity.AuditLog;
import com.example.lotte.enums.auditLog.Action;
import com.example.lotte.enums.auditLog.AuditStatus;
import com.example.lotte.repository.AuditLogRepository;
import com.example.lotte.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void log(Action action, Long targetId, AuditStatus auditStatus, String message) {
        AuditLog audit = new AuditLog();

        audit.setAction(action);
        audit.setTargetId(targetId);
        audit.setAuditStatus(auditStatus);
        audit.setMessage(message);

        auditLogRepository.save(audit);
    }
}
