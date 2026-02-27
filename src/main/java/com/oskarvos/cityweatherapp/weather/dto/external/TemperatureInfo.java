package com.oskarvos.cityweatherapp.weather.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true) // игнорим остальные данные из Json
public class TemperatureInfo {

    @JsonProperty("temp")
    private Double temperature;

    public Double getTemperature() {
        return temperature;
    }

}
