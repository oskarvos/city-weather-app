package com.oskarvos.cityweatherapp.model.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true) // игнорим остальные данные из Json
public class WeatherMain {

    @JsonProperty("temp")
    private Double temperature;

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

}
