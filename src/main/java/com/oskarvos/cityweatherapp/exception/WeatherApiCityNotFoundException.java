package com.oskarvos.cityweatherapp.exception;

public class WeatherApiCityNotFoundException extends RuntimeException {

    public WeatherApiCityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}