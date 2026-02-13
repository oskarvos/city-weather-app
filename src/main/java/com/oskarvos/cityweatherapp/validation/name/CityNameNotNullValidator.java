package com.oskarvos.cityweatherapp.validation.name;

import com.oskarvos.cityweatherapp.dto.response.ValidationError;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CityNameNotNullValidator implements CityNameValidator {

    @Override
    public Optional<ValidationError> validate(String cityName) {
        return (cityName == null || cityName.trim().isEmpty())
                ? Optional.of(new ValidationError(
                "ERROR_CODE_1",
                "Поле пустое, введите город"))
                : Optional.empty();
    }

}
