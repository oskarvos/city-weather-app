package com.oskarvos.cityweatherapp.audit.service;

import com.oskarvos.cityweatherapp.audit.entity.AuditLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AuditLoggerService {

    public void logAuditRecord(String login, List<AuditLog> logs) {
        log.info("Админ запрашивает аудит для пользователя: {}", login);

        for (AuditLog auditLog : logs) {
            String maskLastName = maskLastName(auditLog.getLastName());
            log.info("Запись: id={}, фамилия={}, имя={}, role={}, login={}, " +
                            "вызов={}, параметры={}, результат={}, время={}",
                    auditLog.getId(), maskLastName, auditLog.getLastName(), auditLog.getRole(), auditLog.getLogin(),
                    auditLog.getActionName(), auditLog.getParameters(), auditLog.isSuccess(), auditLog.getTimestamp());
        }
    }

    private String maskLastName(String lastName) {
        if (lastName == null || lastName.isEmpty()) {
            return lastName;
        }
        return lastName.charAt(0) + "***";
    }

}
