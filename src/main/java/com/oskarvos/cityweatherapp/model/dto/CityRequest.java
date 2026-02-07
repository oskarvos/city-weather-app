package com.oskarvos.cityweatherapp.model.dto;

public class CityRequest {

    private String cityName;
    private Double temperature;

    private CityRequest() {
    }

    public CityRequest(String cityName, Double temperature) {
        this.cityName = cityName;
        this.temperature = temperature;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

}
