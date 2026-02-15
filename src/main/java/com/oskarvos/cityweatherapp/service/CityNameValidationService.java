package com.oskarvos.cityweatherapp.service;

import com.oskarvos.cityweatherapp.dto.response.ValidationError;
import com.oskarvos.cityweatherapp.exception.CityValidationException;
import com.oskarvos.cityweatherapp.validation.name.CityNameValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityNameValidationService {

    private final List<CityNameValidator> validators;

    public CityNameValidationService(List<CityNameValidator> validators) {
        this.validators = validators;
    }

    public String normalizeAndValidate(String cityName) {
        String normalized = normalize(cityName);
        List<ValidationError> errors = collectErrors(normalized);
        if (!errors.isEmpty()) {
            throw new CityValidationException(errors);
        }
        return normalized;
    }

    private String normalize(String cityName) {
        if (cityName == null) return null;
        return cityName.trim().replaceAll("\\s+", " ");
    }

    private List<ValidationError> collectErrors(String cityName) {
        return validators.stream()
                .map(v -> v.validate(cityName))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

}
