package com.oskarvos.cityweatherapp.service.mapper;

import com.oskarvos.cityweatherapp.dto.response.CityResponse;
import com.oskarvos.cityweatherapp.entity.City;

import java.time.format.DateTimeFormatter;

public class CityResponseMapper {

    public CityResponse buildValid(City city) {
        return new CityResponse(
                city.getId(),
                city.getCityName(),
                city.getTemperature());
    }

    public CityResponse buildWithWarning(City city) {
        return new CityResponse(
                city.getId(),
                city.getCityName(),
                city.getTemperature(),
                city.getUpdatedAt(),
                String.format("Нет связи с API погоды, температура в городе %s актуальна на %s",
                        city.getCityName(),
                        city.getUpdatedAt().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
    }

    public CityResponse buildDeleteCity(City city) {
        return new CityResponse(
                city.getId(),
                city.getCityName(),
                city.getTemperature(),
                city.getUpdatedAt(),
                "Город из БД удален!");
    }

}
