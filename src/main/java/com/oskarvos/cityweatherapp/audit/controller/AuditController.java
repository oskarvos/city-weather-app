package com.oskarvos.cityweatherapp.audit.controller;

import com.oskarvos.cityweatherapp.audit.entity.AuditLog;
import com.oskarvos.cityweatherapp.audit.repository.AuditLogRepository;
import com.oskarvos.cityweatherapp.audit.service.AuditLoggerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/audit")
@PreAuthorize("hasRole('ADMIN')")
public class AuditController {

    private final AuditLogRepository auditLogRepository;
    private final AuditLoggerService auditLoggerService;

    public AuditController(AuditLogRepository auditLogRepository, AuditLoggerService auditLoggerService) {
        this.auditLogRepository = auditLogRepository;
        this.auditLoggerService = auditLoggerService;
    }

    @GetMapping("/logs/user/{login}")
    public List<AuditLog> getAuditUser(@PathVariable String login) {
        List<AuditLog> logList = auditLogRepository.findByLoginOrderByTimestampDesc(login);
        auditLoggerService.logAuditRecord(login, logList);
        return logList;
    }

}
