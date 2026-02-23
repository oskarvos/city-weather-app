package com.oskarvos.weatherconsumer.consumer;

import com.oskarvos.weatherconsumer.entity.WeatherRequestLog;
import com.oskarvos.weatherconsumer.repository.WeatherRequestLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class WeatherLogConsumer {

    private static final Logger log = LoggerFactory.getLogger(WeatherLogConsumer.class);

    private final WeatherRequestLogRepository repository;

    public WeatherLogConsumer(WeatherRequestLogRepository repository) {
        this.repository = repository;
    }

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
    }

}
