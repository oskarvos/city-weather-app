package com.oskarvos.cityweatherapp.common.exception;

import com.oskarvos.cityweatherapp.city.dto.response.ValidationError;

import java.util.List;

public class CityValidationException extends RuntimeException {

    private final List<ValidationError> errors;

    public CityValidationException(List<ValidationError> errors) {
        super("Ошибки валидации");
        this.errors = errors;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

}
