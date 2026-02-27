package com.oskarvos.cityweatherapp.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class WeatherRequestProducer {

    private static final Logger log = LoggerFactory.getLogger(WeatherRequestProducer.class);

    private final AtomicBoolean kafkaDead = new AtomicBoolean(false);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public WeatherRequestProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

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
