package com.oskarvos.cityweatherapp.common.validation.name;

import com.oskarvos.cityweatherapp.city.dto.response.ValidationError;

import java.util.Optional;

public interface CityNameValidator {

    Optional<ValidationError> validate(String cityName);

}
