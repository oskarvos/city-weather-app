package com.oskarvos.cityweatherapp.service;

import com.oskarvos.cityweatherapp.model.dto.CityRequest;
import com.oskarvos.cityweatherapp.model.entity.City;

import java.util.List;
import java.util.Optional;

public interface CityService {

    Optional<City> getCityByName(String cityName);

    List<City> getAllCities();

    City createCity(CityRequest request);

}
