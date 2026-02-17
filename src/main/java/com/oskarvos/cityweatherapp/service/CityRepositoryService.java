package com.oskarvos.cityweatherapp.service;

import com.oskarvos.cityweatherapp.entity.City;

public interface CityRepositoryService {

    City getCityFromDb(String cityName);

    City saveCityInDb(String cityName, Double temperature);

    City updateCityDb(City city, Double temperature);

}
