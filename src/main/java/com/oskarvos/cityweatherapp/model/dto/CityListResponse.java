package com.oskarvos.cityweatherapp.model.dto;

import com.oskarvos.cityweatherapp.model.entity.City;

import java.util.List;

public class CityListResponse {

    private List<City> cityList;

    public CityListResponse() {
    }

    public CityListResponse(List<City> cityList) {
        this.cityList = cityList;
    }

    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }
}
