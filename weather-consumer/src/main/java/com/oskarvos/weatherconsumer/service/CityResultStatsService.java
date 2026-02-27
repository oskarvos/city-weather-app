package com.oskarvos.weatherconsumer.service;

import com.oskarvos.weatherconsumer.entity.CityRequestStats;
import com.oskarvos.weatherconsumer.repository.CityRequestStatsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CityResultStatsService {

    private final CityRequestStatsRepository cityRequestStatsRepository;

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
