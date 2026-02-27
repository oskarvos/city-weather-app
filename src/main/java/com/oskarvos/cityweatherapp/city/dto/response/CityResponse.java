package com.oskarvos.cityweatherapp.city.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "cityName", "temperature"})
public class CityResponse {

    private Long id;
    private String cityName;
    private Double temperature;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime updatedAt;

    private String info;

    public CityResponse(Long id, String cityName, Double temperature) {
        this.id = id;
        this.cityName = cityName;
        this.temperature = temperature;
    }

}
