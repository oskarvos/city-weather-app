package com.oskarvos.cityweatherapp.exception;

public class CityNotFoundException extends RuntimeException {

    public CityNotFoundException(String message) {
        super("Город с название '" + message + "' не найден");
    }

}
