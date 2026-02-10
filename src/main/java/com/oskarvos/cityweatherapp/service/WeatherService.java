package com.oskarvos.cityweatherapp.service;

import com.oskarvos.cityweatherapp.model.entity.City;

public interface WeatherService {

    City getActualWeather(String cityName);

}
