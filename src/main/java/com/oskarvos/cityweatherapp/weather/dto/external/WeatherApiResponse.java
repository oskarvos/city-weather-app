package com.oskarvos.cityweatherapp.weather.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherApiResponse {

    @JsonProperty("name")
    private String cityName;

    @JsonProperty("main")
    private TemperatureInfo temperatureInfo;

    public Double getTemperature() { // парсим JSON, получаем температуру
        return temperatureInfo != null ? temperatureInfo.getTemperature() : null;
    }

}
