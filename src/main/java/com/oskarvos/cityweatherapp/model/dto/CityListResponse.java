package com.oskarvos.cityweatherapp.model.dto;

import com.oskarvos.cityweatherapp.model.entity.City;

import java.util.List;

public class CityListResponse {

    private List<City> cities;

    public CityListResponse() {
    }

    public CityListResponse(List<City> cities) {
        this.cities = cities;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

}
