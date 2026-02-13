package com.oskarvos.cityweatherapp.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherApiResponse {

    @JsonProperty("name")
    private String cityName;

    @JsonProperty("main")
    private TemperatureInfo temperatureInfo;

    public Double getTemperature() { // парсим JSON, получаем температуру
        return temperatureInfo != null ? temperatureInfo.getTemperature() : null;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public TemperatureInfo getWeatherMain() {
        return temperatureInfo;
    }

    public void setWeatherMain(TemperatureInfo temperatureInfo) {
        this.temperatureInfo = temperatureInfo;
    }

}
