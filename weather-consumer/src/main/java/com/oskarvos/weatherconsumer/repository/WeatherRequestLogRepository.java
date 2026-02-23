package com.oskarvos.weatherconsumer.repository;

import com.oskarvos.weatherconsumer.entity.WeatherRequestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRequestLogRepository extends JpaRepository<WeatherRequestLog, Long> {

}
