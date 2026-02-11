package com.oskarvos.cityweatherapp.validation.name;

import com.oskarvos.cityweatherapp.exception.ValidationException;

public class CityNameLatinValidator implements CityNameValidator {

    @Override
    public void validate(String cityName) {
        if (!cityName.matches("^[A-Za-z]+$")) {
            throw new ValidationException("Название города должно содержать только латинские буквы");
        }
    }

}
