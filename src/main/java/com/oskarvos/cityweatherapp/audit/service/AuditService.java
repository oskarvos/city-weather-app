package com.oskarvos.cityweatherapp.audit.service;

import com.oskarvos.cityweatherapp.audit.dto.AuditRequestDto;
import com.oskarvos.cityweatherapp.audit.entity.AuditLog;
import com.oskarvos.cityweatherapp.audit.repository.AuditLogRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuditService {

    private static final Logger log = LoggerFactory.getLogger(AuditService.class);

    private final AuditLogRepository auditLogRepository;

    public AuditService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Transactional
    public void saveAuditLog(AuditRequestDto request) {
        log.info("Начинает сохранять аудит в БД");
        try {
            AuditLog auditLog = new AuditLog();

            auditLog.setFirstName(request.getFirstName());
            auditLog.setLastName(request.getLastName());
            auditLog.setRole(request.getRole());
            auditLog.setLogin(request.getLogin());
            auditLog.setActionName(request.getActionName());
            auditLog.setParameters(request.getParameters());
            auditLog.setSuccess(request.isSuccess());
            auditLog.setTimestamp(request.getTimestamp());

            auditLogRepository.save(auditLog);
            log.info("Аудит успешно сохранен в БД");
        } catch (Exception e) {
            log.error("Произошла ошибка на этапе сохранения аудита");
        }
    }

}
