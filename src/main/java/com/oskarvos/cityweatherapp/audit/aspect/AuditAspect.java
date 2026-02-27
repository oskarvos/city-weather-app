package com.oskarvos.cityweatherapp.audit.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oskarvos.cityweatherapp.audit.annotation.Auditable;
import com.oskarvos.cityweatherapp.audit.dto.AuditRequestDto;
import com.oskarvos.cityweatherapp.audit.service.AuditService;
import com.oskarvos.cityweatherapp.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Aspect
@Component
public class AuditAspect {

    private final AuditService auditService;
    private final ObjectMapper objectMapper;

    @Around("@annotation(auditable)")
    public Object audit(ProceedingJoinPoint joinPoint, Auditable auditable) throws Throwable {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();

        String parameters = extractParameters(joinPoint);
        LocalDateTime timestamp = LocalDateTime.now();

        Object result = joinPoint.proceed();

        boolean isSuccess = true;
        if (result instanceof ResponseEntity) {
            ResponseEntity<?> response = (ResponseEntity<?>) result;
            isSuccess = response.getStatusCode().is2xxSuccessful();
        }

        AuditRequestDto request = new AuditRequestDto(
                user.getLastName(),
                user.getFirstName(),
                user.getRole(),
                user.getLogin(),
                auditable.action(),
                parameters,
                isSuccess,
                timestamp
        );

        auditService.saveAuditLog(request);

        return result;
    }

    private String extractParameters(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterName = signature.getParameterNames();
        Object[] parameterValue = joinPoint.getArgs();

        if (parameterName == null || parameterName.length == 0) {
            return "{}";
        }


        Map<String, Object> paramsMap = new HashMap<>();

        for (int i = 0; i < parameterName.length; i++) {
            Object value = parameterValue[i];

            if (value instanceof UserDetails) {
                paramsMap.put(parameterName[i], "omitted");
                continue;
            }

            paramsMap.put(parameterName[i], value != null ? value.toString() : null);
        }

        try {
            return objectMapper.writeValueAsString(paramsMap);
        } catch (Exception e) {
            return "{\"ошибка\":\"ошибка сериализации\"}";
        }
    }

}

