package com.oskarvos.cityweatherapp.service;

import com.oskarvos.cityweatherapp.dto.response.ValidationError;
import com.oskarvos.cityweatherapp.exception.CityValidationException;
import com.oskarvos.cityweatherapp.util.CityNameNormalizer;
import com.oskarvos.cityweatherapp.validation.name.CityNameValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityNameValidationService {

    private final List<CityNameValidator> validators;
    private final CityNameNormalizer cityNameNormalizer;

    public CityNameValidationService(List<CityNameValidator> validators,
                                     CityNameNormalizer cityNameNormalizer) {
        this.validators = validators;
        this.cityNameNormalizer = cityNameNormalizer;
    }

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
