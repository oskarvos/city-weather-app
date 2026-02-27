package com.oskarvos.weatherconsumer.repository;

import com.oskarvos.weatherconsumer.entity.CityRequestStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRequestStatsRepository extends JpaRepository<CityRequestStats, Long> {

    Optional<CityRequestStats> findByCityName(String cityName);

    List<CityRequestStats> findAll();

}
