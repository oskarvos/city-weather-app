package com.oskarvos.cityweatherapp.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;
import java.util.List;

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
        this.info = null;
    }

    public CityResponse(String info) {
        this.id = null;
        this.cityName = null;
        this.temperature = null;
        this.info = info;
    }

    public CityResponse(List<ValidationError> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<ValidationError> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(List<ValidationError> validationErrors) {
        this.validationErrors = validationErrors;
    }

}
