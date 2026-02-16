package com.oskarvos.cityweatherapp.dto.response;

import com.oskarvos.cityweatherapp.entity.City;

import java.util.ArrayList;
import java.util.List;

public class CityListResponse {

    private List<City> favoriteCities;
    private List<City> cities;

    public CityListResponse(List<City> favoriteCities, List<City> cities) {
        this.favoriteCities = favoriteCities != null ? favoriteCities : new ArrayList<>();
        this.cities = cities != null ? cities : new ArrayList<>();
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
