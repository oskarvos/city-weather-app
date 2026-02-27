package com.oskarvos.weatherconsumer.consumer;

import com.oskarvos.weatherconsumer.entity.WeatherRequestLog;
import com.oskarvos.weatherconsumer.repository.WeatherRequestLogRepository;
import com.oskarvos.weatherconsumer.service.CityRequestStatsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class WeatherLogConsumer {

    private final WeatherRequestLogRepository repository;
    private final CityRequestStatsService cityRequestStatsService;

    @KafkaListener(topics = "weather-request", groupId = "weather-consumer-group")
    public void consume(Map<String, Object> message) {
        log.info("Topic: получили сообщение из topic");

        String cityName = (String) message.get("cityName");
        Double temperature = (Double) message.get("temperature");

        WeatherRequestLog requestLog = new WeatherRequestLog();
        requestLog.setCityName(cityName);
        requestLog.setTemperature(temperature);
        requestLog.setRequestedAt(LocalDateTime.now());

        log.debug("Topic: начинает сохранение запроса в БД");
        repository.save(requestLog);
        log.debug("Topic: запрос сохранен в БД");

        cityRequestStatsService.createCityRequestStats(requestLog);
    }

}
