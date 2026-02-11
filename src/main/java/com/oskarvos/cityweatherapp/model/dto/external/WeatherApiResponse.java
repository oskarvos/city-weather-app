package com.oskarvos.cityweatherapp.model.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherApiResponse {

    @JsonProperty("name")
    private String cityName;

    @JsonProperty("main")
    private WeatherMain weatherMain;

    public Double getTemperature() { // парсим JSON, получаем температуру
        return weatherMain != null ? weatherMain.getTemperature() : null;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public WeatherMain getWeatherMain() {
        return weatherMain;
    }

    public void setWeatherMain(WeatherMain weatherMain) {
        this.weatherMain = weatherMain;
    }

}
