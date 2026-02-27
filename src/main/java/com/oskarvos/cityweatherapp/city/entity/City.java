package com.oskarvos.cityweatherapp.city.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
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

    public City(String cityName, Double temperature) {
        this.cityName = cityName;
        this.temperature = temperature;
    }

}
