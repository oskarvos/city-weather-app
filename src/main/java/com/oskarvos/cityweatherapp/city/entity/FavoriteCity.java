package com.oskarvos.cityweatherapp.city.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "favorite_cities")
public class FavoriteCity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_favorite_city_city")
    )
    private City city;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
