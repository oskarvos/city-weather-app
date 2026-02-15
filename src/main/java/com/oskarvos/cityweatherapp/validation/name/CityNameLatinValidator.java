package com.oskarvos.cityweatherapp.validation.name;

import com.oskarvos.cityweatherapp.dto.response.ValidationError;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Валидатор проверяет, что название города содержит только латинские буквы,
 * пробелы или тире. Любые другие символы (кириллица, спецсимволы) запрещены.
 * Примеры нарушений: "Москва", "Paris!", "Copenhagen@", "New$York"
 */
@Component
public class CityNameLatinValidator implements CityNameValidator {

    @Override
    public Optional<ValidationError> validate(String cityName) {
        return !cityName.matches("^[A-Za-z\\s-]+$")
                ? Optional.of(new ValidationError(
                "ERROR_CODE_2",
                "Только латиница, пробел или тире"))
                : Optional.empty();
    }

}
