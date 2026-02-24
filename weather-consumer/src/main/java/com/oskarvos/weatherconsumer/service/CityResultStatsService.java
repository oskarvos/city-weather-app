package com.oskarvos.weatherconsumer.service;

import com.oskarvos.weatherconsumer.entity.CityRequestStats;
import com.oskarvos.weatherconsumer.repository.CityRequestStatsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityResultStatsService {

    private static final Logger log = LoggerFactory.getLogger(CityResultStatsService.class);

    private final CityRequestStatsRepository cityRequestStatsRepository;

    public CityResultStatsService(CityRequestStatsRepository cityRequestStatsRepository) {
        this.cityRequestStatsRepository = cityRequestStatsRepository;
    }

    @Scheduled(cron = "0 * * * * *")
    public void createCityResultStatsScheduler() {
        log.info("========== СТАТИСТИКА ЗАПРОСОВ ПО ГОРОДАМ ==========");

        List<CityRequestStats> cityRequestStatsList;

        cityRequestStatsList = cityRequestStatsRepository.findAll();

        cityRequestStatsList.forEach(stat ->
                log.info("Статистика: город - {}, запросов - {}",
                        stat.getCityName(),
                        stat.getRequestCount())
        );
        log.info("==================================================");
    }

}
