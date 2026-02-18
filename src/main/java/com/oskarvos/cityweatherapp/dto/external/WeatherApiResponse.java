package com.oskarvos.cityweatherapp.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherApiResponse {

    @JsonProperty("name")
    private String cityName;

    @JsonProperty("main")
    private TemperatureInfo temperatureInfo;

    public String getCityName() {
        return cityName;
    }

    public Double getTemperature() { // парсим JSON, получаем температуру
        return temperatureInfo != null ? temperatureInfo.getTemperature() : null;
    }

}
