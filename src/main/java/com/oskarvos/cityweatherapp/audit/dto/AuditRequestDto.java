package com.oskarvos.cityweatherapp.audit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Objects;

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

    public AuditRequestDto(String lastName, String firstName, String role, String login, String actionName, String parameters, boolean success, LocalDateTime timestamp) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.role = role;
        this.login = login;
        this.actionName = actionName;
        this.parameters = parameters;
        this.success = success;
        this.timestamp = timestamp;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getRole() {
        return role;
    }

    public String getLogin() {
        return login;
    }

    public String getActionName() {
        return actionName;
    }

    public String getParameters() {
        return parameters;
    }

    public boolean isSuccess() {
        return success;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AuditRequestDto that = (AuditRequestDto) o;
        return success == that.success
                && Objects.equals(lastName, that.lastName)
                && Objects.equals(firstName, that.firstName)
                && Objects.equals(role, that.role)
                && Objects.equals(login, that.login)
                && Objects.equals(actionName, that.actionName)
                && Objects.equals(parameters, that.parameters)
                && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName, firstName, role, login, actionName, parameters, success, timestamp);
    }

}
