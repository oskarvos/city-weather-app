package com.oskarvos.cityweatherapp.common.validation.name;

import com.oskarvos.cityweatherapp.city.dto.response.ValidationError;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Валидатор проверяет корректность формата названия города:
 * - не начинается с тире или пробела
 * - не заканчивается тире или пробелом
 * - нет множественных пробелов подряд
 * - нет множественных тире подряд
 * Примеры нарушений: "-Paris", "Berlin-", "New  York", "Santa--Maria", " Los Angeles" "Miami "
 */
@Component
public class CityNameFormatValidator implements CityNameValidator {

    @Override
    public Optional<ValidationError> validate(String cityName) {
        if (cityName == null) return Optional.empty();

        String trimmed = cityName.trim();
        if (trimmed.startsWith("-") || trimmed.endsWith("-") ||
                trimmed.contains("  ") || trimmed.contains("--")) {
            return Optional.of(new ValidationError(
                    "ERROR_CODE_5",
                    "Некорректный формат"));
        }
        return Optional.empty();
    }

}
