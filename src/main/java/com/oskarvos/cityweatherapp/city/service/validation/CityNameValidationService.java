package com.oskarvos.cityweatherapp.city.service.validation;

import com.oskarvos.cityweatherapp.city.dto.response.ValidationError;
import com.oskarvos.cityweatherapp.common.exception.CityValidationException;
import com.oskarvos.cityweatherapp.common.util.CityNameNormalizer;
import com.oskarvos.cityweatherapp.common.validation.name.CityNameValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CityNameValidationService {

    private final List<CityNameValidator> validators;
    private final CityNameNormalizer cityNameNormalizer;

    public String normalizeAndValidate(String cityName) {
        String normalized = cityNameNormalizer.normalizer(cityName);
        validateOrThrow(normalized);
        return normalized;
    }

    private void validateOrThrow(String cityName) {
        List<ValidationError> errors = collectErrors(cityName);
        if (!errors.isEmpty()) {
            throw new CityValidationException(errors);
        }
    }

    private List<ValidationError> collectErrors(String cityName) {
        return validators.stream()
                .map(v -> v.validate(cityName))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

}
