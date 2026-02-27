package com.oskarvos.cityweatherapp.city.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;

import java.time.LocalDateTime;

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

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @OneToOne(mappedBy = "city")
    @JsonIgnore // поле игнорируется и не попадает в JSON (решаем циклическую зависимость в JSON)
    private FavoriteCity favoriteCity;

    public City() {
    }

    public City(String cityName, Double temperature) {
        this.cityName = cityName;
        this.temperature = temperature;
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

    public FavoriteCity getFavoriteCity() {
        return favoriteCity;
    }

    public void setFavoriteCity(FavoriteCity favoriteCity) {
        this.favoriteCity = favoriteCity;
    }

}
