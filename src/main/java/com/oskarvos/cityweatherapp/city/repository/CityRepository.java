package com.oskarvos.cityweatherapp.city.repository;

import com.oskarvos.cityweatherapp.city.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    City findByCityName(String cityName);

    @Query("SELECT c FROM City c " +
            "WHERE c.favoriteCity IS NOT NULL " +
            "ORDER BY c.updatedAt DESC")
    List<City> findFavoriteCitiesOrderByCreatedDateDesc();

    @Query("SELECT c FROM City c " +
            "WHERE c.favoriteCity IS NULL " +
            "ORDER BY c.updatedAt DESC")
    List<City> findNonFavoriteCitiesOrderByCreatedDateDesc();

}
