package com.oskarvos.cityweatherapp.repository;

import com.oskarvos.cityweatherapp.entity.FavoriteCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteCityRepository extends JpaRepository<FavoriteCity, Long> {

    boolean existsByCityId(Long cityId);

}
