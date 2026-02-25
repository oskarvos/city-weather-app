package com.oskarvos.cityweatherapp.city.service.mapper;

import com.oskarvos.cityweatherapp.city.dto.response.CityResponse;
import com.oskarvos.cityweatherapp.city.entity.City;
import org.springframework.stereotype.Component;

@Component
public class CityMapper {

    public CityResponse buildValid(City city) {
        return new CityResponse(
                city.getId(),
                city.getCityName(),
                city.getTemperature());
    }

    public CityResponse buildWithMessage(City city, String message) {
        return new CityResponse(
                city.getId(),
                city.getCityName(),
                city.getTemperature(),
                city.getUpdatedAt(),
                message);
    }

}
