package com.oskarvos.cityweatherapp.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@RequiredArgsConstructor
@Service
public class WeatherRequestProducer {

    private final AtomicBoolean kafkaDead = new AtomicBoolean(false);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendWeatherRequest(String cityName, Double temperature) {
        if (kafkaDead.get()) return;

        try {
            Map<String, Object> message = new HashMap<>();

            message.put("cityName", cityName);
            message.put("temperature", temperature);

            kafkaTemplate.send("weather-request", message);

            log.info("Отправлено сообщение брокеру Kafka");
        } catch (Exception e) {
            if (kafkaDead.compareAndSet(false, true)) {
                log.info("Нет связи с брокером Kafka - отключаю Kafka до перезапуска приложения", e);
                ((DefaultKafkaProducerFactory<String, Object>) kafkaTemplate.getProducerFactory()).destroy();
            }
        }
    }

}
