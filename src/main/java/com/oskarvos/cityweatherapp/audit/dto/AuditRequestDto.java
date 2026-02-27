package com.oskarvos.cityweatherapp.audit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AuditRequestDto {

    private String lastName;
    private String firstName;
    private String role;
    private String login;
    private String actionName;
    private String parameters;
    private boolean success;

    @JsonFormat(pattern = "dd.MM.yyyy - HH:mm")
    private LocalDateTime timestamp;

}
