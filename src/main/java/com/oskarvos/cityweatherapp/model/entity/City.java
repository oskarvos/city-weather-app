package com.oskarvos.cityweatherapp.model.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;

@Entity
@Table(name = "cities")
@JsonPropertyOrder({"id", "cityName", "temperature"}) // Указываем порядок явно
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city_name", nullable = false, unique = true)
    private String cityName;

    @Column(name = "temperature", nullable = false)
    private Double temperature;

    public City() {
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

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

}
