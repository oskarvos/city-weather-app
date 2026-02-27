package com.oskarvos.cityweatherapp.common.exception;

public class WeatherApiCityNotFoundException extends RuntimeException {

    public WeatherApiCityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
