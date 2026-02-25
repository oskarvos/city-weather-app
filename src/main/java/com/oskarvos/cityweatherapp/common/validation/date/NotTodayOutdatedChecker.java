package com.oskarvos.cityweatherapp.common.validation.date;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class NotTodayOutdatedChecker implements OutdatedChecker {

    @Override
    public boolean isOutdated(LocalDateTime createdAt) {
        LocalDate dayCreatedAt = createdAt.toLocalDate();
        LocalDate nowLocalDate = LocalDate.now();
        return !dayCreatedAt.equals(nowLocalDate);
    }

}
