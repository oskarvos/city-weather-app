package com.oskarvos.cityweatherapp.validation.name;

import com.oskarvos.cityweatherapp.dto.response.ValidationError;

import java.util.Optional;

public interface CityNameValidator {

    Optional<ValidationError> validate(String cityName);

}
