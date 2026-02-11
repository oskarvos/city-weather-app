package com.oskarvos.cityweatherapp.service;

import com.oskarvos.cityweatherapp.model.dto.CityListResponse;
import com.oskarvos.cityweatherapp.model.dto.CityResponse;

public interface CityService {

    CityResponse getCityByName(String cityName);

    CityListResponse getAllCities();

    CityResponse deleteCityByName(String cityName);

}
