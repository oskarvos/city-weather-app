package com.oskarvos.cityweatherapp.city.repository;

import com.oskarvos.cityweatherapp.city.entity.FavoriteCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteCityRepository extends JpaRepository<FavoriteCity, Long> {

    boolean existsByCityId(Long cityId);

}
