package com.oskarvos.weatherconsumer.service;

import com.oskarvos.weatherconsumer.entity.CityRequestStats;
import com.oskarvos.weatherconsumer.entity.WeatherRequestLog;
import com.oskarvos.weatherconsumer.repository.CityRequestStatsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CityRequestStatsService {

    private final CityRequestStatsRepository cityRequestStatsRepository;

    public void createCityRequestStats(WeatherRequestLog weatherRequestLog) {
        log.info("Начинает поиск города в city_request_stats");
        Optional<CityRequestStats> cityRequestStatsOpt = cityRequestStatsRepository.findByCityName(weatherRequestLog.getCityName());

        if (cityRequestStatsOpt.isPresent()) {
            log.info("Нашел город в city_request_stats");

            CityRequestStats cityRequestStats = cityRequestStatsOpt.get();
            Integer count = cityRequestStats.getRequestCount();
            cityRequestStats.setRequestCount(++count);

            log.debug("Начинает изменять request_count текущего город в city_request_stats");
            cityRequestStatsRepository.save(cityRequestStats);
            log.debug("Изменил request_count в текущем городе");
        } else {
            log.info("Город не найден в city_request_stats, будет сохранять");
            CityRequestStats cityRequestStats = new CityRequestStats();
            cityRequestStats.setCityName(weatherRequestLog.getCityName());
            cityRequestStats.setRequestCount(1);

            cityRequestStatsRepository.save(cityRequestStats);
            log.debug("Город сохранен в city_request_stats с request_count = 1");
        }
    }

}
