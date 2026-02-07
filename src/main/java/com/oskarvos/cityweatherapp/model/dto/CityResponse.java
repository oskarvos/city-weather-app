package com.oskarvos.cityweatherapp.model.dto;

import org.springframework.stereotype.Component;

@Component
public class CityResponse {

    private Long id;
    private String cityName;
    private Double temperature;

    public CityResponse() {
    }

    public CityResponse(Long id, String cityName, Double temperature) {
        this.id = id;
        this.cityName = cityName;
        this.temperature = temperature;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
