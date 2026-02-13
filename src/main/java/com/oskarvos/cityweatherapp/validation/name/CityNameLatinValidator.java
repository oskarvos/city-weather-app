package com.oskarvos.cityweatherapp.validation.name;

import com.oskarvos.cityweatherapp.dto.response.ValidationError;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CityNameLatinValidator implements CityNameValidator {

    @Override
    public Optional<ValidationError> validate(String cityName) {
        return !cityName.matches("^[A-Za-z]+$")
                ? Optional.of(new ValidationError(
                "ERROR_CODE_2",
                "Название города должен быть указано на латинице!"))
                : Optional.empty();
    }

}
