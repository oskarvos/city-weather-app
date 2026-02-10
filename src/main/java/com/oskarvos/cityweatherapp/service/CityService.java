package com.oskarvos.cityweatherapp.service;

import com.oskarvos.cityweatherapp.model.dto.CityListResponse;
import com.oskarvos.cityweatherapp.model.dto.CityRequest;
import com.oskarvos.cityweatherapp.model.dto.CityResponse;

public interface CityService {

    CityResponse getCityByName(String cityName);

    CityListResponse getAllCities();

    CityResponse createCity(CityRequest request);

    CityResponse deleteCityByName(String cityName);

}
