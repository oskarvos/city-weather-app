package com.oskarvos.cityweatherapp.validation.date;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class NotTodayDateValidator implements DateValidator {

    @Override
    public boolean validate(LocalDateTime createdAt) {
        LocalDate dayCreatedAt = createdAt.toLocalDate();
        LocalDate nowLocalDate = LocalDate.now();
        return !dayCreatedAt.equals(nowLocalDate);
    }

}
