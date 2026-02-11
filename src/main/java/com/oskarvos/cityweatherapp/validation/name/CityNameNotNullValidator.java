package com.oskarvos.cityweatherapp.validation.name;

import com.oskarvos.cityweatherapp.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class CityNameNotNullValidator implements CityNameValidator {

    @Override
    public void validate(String cityName) {
        if (cityName == null) {
            throw new ValidationException("Название города не может быть пустым");
        }
    }

}
