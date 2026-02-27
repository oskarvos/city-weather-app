package com.oskarvos.weatherconsumer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherRequestDto {

    private String cityName;
    private Double temperature;

}
