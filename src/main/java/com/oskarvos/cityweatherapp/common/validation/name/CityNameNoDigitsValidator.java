package com.oskarvos.cityweatherapp.common.validation.name;

import com.oskarvos.cityweatherapp.city.dto.response.ValidationError;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Валидатор проверяет, что название города не содержит цифр.
 * Примеры нарушений: "London123", "New York2", "20th Century", "Paris2024"
 */
@Component
public class CityNameNoDigitsValidator implements CityNameValidator {

    @Override
    public Optional<ValidationError> validate(String cityName) {
        if (cityName == null) return Optional.empty();

        if (cityName.matches(".*\\d.*")) {
            return Optional.of(new ValidationError(
                    "ERROR_CODE_3",
                    "Цифры запрещены"));
        }
        return Optional.empty();
    }

}
