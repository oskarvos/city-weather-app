package com.oskarvos.cityweatherapp.validation.date;

import java.time.LocalDateTime;

public interface DateValidator {

    boolean validate(LocalDateTime createdAt);

}
