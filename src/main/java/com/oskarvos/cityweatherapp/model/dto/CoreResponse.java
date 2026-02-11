package com.oskarvos.cityweatherapp.model.dto;

import com.oskarvos.cityweatherapp.validation.name.CityNameValidator;

import java.util.List;

public class CoreResponse {

    List<CityNameValidator> errors;

    public CoreResponse(List<CityNameValidator> errors) {
        this.errors = errors;
    }

}
