package com.oskarvos.cityweatherapp.exception;

public class WeatherApiConnectionException extends RuntimeException {

    public WeatherApiConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

}