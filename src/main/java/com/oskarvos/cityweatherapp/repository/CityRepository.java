package com.oskarvos.cityweatherapp.repository;

import com.oskarvos.cityweatherapp.model.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    City findByCityName(String cityName);

    @Query("SELECT c FROM City c ORDER BY c.id DESC")
    List<City> findAllOrderByIdDesc();

}
