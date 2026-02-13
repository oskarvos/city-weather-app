package com.oskarvos.cityweatherapp.service.mapper;

import com.oskarvos.cityweatherapp.dto.response.CityResponse;
import com.oskarvos.cityweatherapp.dto.response.ValidationError;
import com.oskarvos.cityweatherapp.entity.City;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
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

    public CityResponse buildErrors(List<ValidationError> errors) {
        return new CityResponse(errors);
    }

    public CityResponse buildInfo(String info) {
        return new CityResponse(info);
    }

}
