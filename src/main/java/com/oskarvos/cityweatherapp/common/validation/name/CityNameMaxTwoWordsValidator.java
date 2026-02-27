package com.oskarvos.cityweatherapp.common.validation.name;

import com.oskarvos.cityweatherapp.city.dto.response.ValidationError;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Валидатор проверяет, что название города состоит не более чем из двух слов.
 * Слова могут разделяться пробелом или тире.
 * Примеры нарушений: "New York City"
 * Примеры допустимых значений: "London", "New York", "Santa-Maria"
 */
@Component
public class CityNameMaxTwoWordsValidator implements CityNameValidator {

    @Override
    public Optional<ValidationError> validate(String cityName) {
        if (cityName == null) return Optional.empty();

        String[] words = cityName.trim().split("[\\s-]+");
        if (words.length > 2) {
            return Optional.of(new ValidationError(
                    "ERROR_CODE_4",
                    "Максимум 2 слова"));
        }
        return Optional.empty();
    }

}
