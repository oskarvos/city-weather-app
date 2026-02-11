package com.oskarvos.cityweatherapp.model.dto.external;

import com.oskarvos.cityweatherapp.model.dto.CityResponse;
import com.oskarvos.cityweatherapp.model.entity.City;
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
