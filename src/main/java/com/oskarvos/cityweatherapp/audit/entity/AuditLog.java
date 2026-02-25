package com.oskarvos.cityweatherapp.audit.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_log")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "action_name")
    private String actionName;

    @Column(name = "parameters")
    private String parameters;

    @Column(name = "success")
    private boolean success;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;


    public Long getId() {
        return id;
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

}
