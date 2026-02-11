package com.oskarvos.cityweatherapp.dto.response;

import com.oskarvos.cityweatherapp.entity.City;

import java.util.List;

public class CityListResponse {

    private List<City> favoriteCities;
    private List<City> cities;

    public CityListResponse() {
    }

    public CityListResponse(List<City> favoriteCities, List<City> cities) {
        this.favoriteCities = favoriteCities;
        this.cities = cities;
    }

    public List<City> getFavoriteCities() {
        return favoriteCities;
    }

    public void setFavoriteCities(List<City> favoriteCities) {
        this.favoriteCities = favoriteCities;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

}
