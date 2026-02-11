package com.oskarvos.cityweatherapp.service.mapper;

import com.oskarvos.cityweatherapp.dto.response.CityResponse;
import com.oskarvos.cityweatherapp.entity.City;
import org.springframework.stereotype.Component;

@Component
public class BuildCityResponse {

    public CityResponse buildValid(City city) {
        return new CityResponse(city.getId(), city.getCityName(), city.getTemperature());
    }

    public CityResponse buildWithWarning(City city) {
        return new CityResponse(
                city.getId(),
                city.getCityName(),
                city.getTemperature(),
                city.getCreatedAt(),
                "Данные температуры не актуальны!");
    }

    public CityResponse buildDeleteCity(City city) {
        return new CityResponse(
                city.getId(),
                city.getCityName(),
                city.getTemperature(),
                city.getCreatedAt(),
                "Город из БД удален!");
    }

}
