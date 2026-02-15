package com.oskarvos.cityweatherapp.service;

import com.oskarvos.cityweatherapp.entity.City;

public interface WeatherService {

    City getActualWeather(String cityName);

}
