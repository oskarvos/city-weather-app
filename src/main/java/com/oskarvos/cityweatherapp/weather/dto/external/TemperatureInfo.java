package com.oskarvos.cityweatherapp.weather.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true) // игнорим остальные данные из Json
public class TemperatureInfo {

    @JsonProperty("temp")
    private Double temperature;

}
