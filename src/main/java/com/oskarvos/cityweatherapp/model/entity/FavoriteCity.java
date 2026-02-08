package com.oskarvos.cityweatherapp.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "favorite_cities")
public class FavoriteCity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "city_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_favorite_cities_cities")
    )
    private City city;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
