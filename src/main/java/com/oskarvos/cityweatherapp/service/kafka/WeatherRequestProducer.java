package com.oskarvos.cityweatherapp.service.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WeatherRequestProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public WeatherRequestProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendWeatherRequest(String cityName, Double temperature) {
        Map<String, Object> message = new HashMap<>();
        message.put("cityName", cityName);
        message.put("temperature", temperature);

        kafkaTemplate.send("weather-request", message);
    }

}
