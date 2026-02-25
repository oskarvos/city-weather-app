package com.oskarvos.cityweatherapp.common.exception;

public class CityNotFoundException extends RuntimeException {

    public CityNotFoundException(String message) {
        super("Город с название '" + message + "' не найден");
    }

}
