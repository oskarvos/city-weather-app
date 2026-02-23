package com.oskarvos.cityweatherapp.service.city;

import com.oskarvos.cityweatherapp.dto.response.CityResponse;
import com.oskarvos.cityweatherapp.entity.City;
import com.oskarvos.cityweatherapp.service.kafka.WeatherRequestProducer;
import com.oskarvos.cityweatherapp.service.mapper.CityMapper;
import com.oskarvos.cityweatherapp.service.validation.CityNameValidationService;
import com.oskarvos.cityweatherapp.service.weather.WeatherService;
import com.oskarvos.cityweatherapp.validation.date.OutdatedChecker;
import org.springframework.stereotype.Service;

@Service
public class CityQueryService {

    private final WeatherService weatherService;
    private final OutdatedChecker outdatedChecker;
    private final CityMapper cityMapper;
    private final CityNameValidationService cityNameValidationService;
    private final WeatherRequestProducer weatherRequestProducer;

    public CityQueryService(WeatherService weatherService,
                            OutdatedChecker outdatedChecker,
                            CityMapper cityMapper,
                            CityNameValidationService cityNameValidationService,
                            WeatherRequestProducer weatherRequestProducer) {
        this.weatherService = weatherService;
        this.outdatedChecker = outdatedChecker;
        this.cityMapper = cityMapper;
        this.cityNameValidationService = cityNameValidationService;
        this.weatherRequestProducer = weatherRequestProducer;
    }

    public CityResponse getCityByName(String cityName) {
        String normalizeCityName = normalizeCityName(cityName);
        City city = getActualWeather(normalizeCityName);

        weatherRequestProducer.sendWeatherRequest(city.getCityName(), city.getTemperature());

        return outdateChecker(city);
    }

    private String normalizeCityName(String cityName) {
        return cityNameValidationService.normalizeAndValidate(cityName);
    }

    private City getActualWeather(String cityName) {
        return weatherService.getActualWeather(cityName);
    }

    private CityResponse outdateChecker(City city) {
        return outdatedChecker.isOutdated(city.getUpdatedAt())
                ? cityMapper.buildWithMessage(city, "Нет связи с API погоды, температура в городе не актуальна")
                : cityMapper.buildValid(city);
    }

}
