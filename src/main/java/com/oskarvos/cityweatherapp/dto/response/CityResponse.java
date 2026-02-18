package com.oskarvos.cityweatherapp.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)  // скрыть поля null в ответе (конструктор)
@JsonPropertyOrder({"id", "cityName", "temperature"})
public class CityResponse {

    private Long id;
    private String cityName;
    private Double temperature;
    private LocalDateTime updatedAt;
    private String info;
    private List<ValidationError> validationErrors;

    public CityResponse() {
    }

    public CityResponse(Long id, String cityName, Double temperature, LocalDateTime updatedAt, String info) {
        this.id = id;
        this.cityName = cityName;
        this.temperature = temperature;
        this.updatedAt = updatedAt;
        this.info = info;
    }

    public CityResponse(Long id, String cityName, Double temperature) {
        this.id = id;
        this.cityName = cityName;
        this.temperature = temperature;
    }

    public Long getId() {
        return id;
    }

    public String getCityName() {
        return cityName;
    }

    public Double getTemperature() {
        return temperature;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getInfo() {
        return info;
    }

    public List<ValidationError> getValidationErrors() {
        return validationErrors;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CityResponse response = (CityResponse) o;
        return Objects.equals(id, response.id)
                && Objects.equals(cityName, response.cityName)
                && Objects.equals(temperature, response.temperature)
                && Objects.equals(updatedAt, response.updatedAt) &&
                Objects.equals(info, response.info)
                && Objects.equals(validationErrors, response.validationErrors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cityName, temperature, updatedAt, info, validationErrors);
    }
}
