package com.oskarvos.cityweatherapp.city.service;

import com.oskarvos.cityweatherapp.weather.dto.external.WeatherApiResponse;
import com.oskarvos.cityweatherapp.city.dto.response.CityResponse;
import com.oskarvos.cityweatherapp.city.entity.City;
import com.oskarvos.cityweatherapp.city.entity.FavoriteCity;
import com.oskarvos.cityweatherapp.city.repository.FavoriteCityRepository;
import com.oskarvos.cityweatherapp.city.service.mapper.CityMapper;
import com.oskarvos.cityweatherapp.city.service.persistence.CityPersistenceService;
import com.oskarvos.cityweatherapp.city.service.validation.CityNameValidationService;
import com.oskarvos.cityweatherapp.weather.service.WeatherClientService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CityFavoriteService {

    private final CityPersistenceService cityPersistenceService;
    private final FavoriteCityRepository favoriteCityRepository;
    private final CityNameValidationService cityNameValidationService;
    private final CityMapper cityMapper;
    private final WeatherClientService weatherClientService;

    public CityFavoriteService(CityPersistenceService cityPersistenceService,
                               FavoriteCityRepository favoriteCityRepository,
                               CityNameValidationService cityNameValidationService,
                               CityMapper cityMapper,
                               WeatherClientService weatherClientService) {
        this.cityPersistenceService = cityPersistenceService;
        this.favoriteCityRepository = favoriteCityRepository;
        this.cityNameValidationService = cityNameValidationService;
        this.cityMapper = cityMapper;
        this.weatherClientService = weatherClientService;
    }

    public CityResponse createFavoriteCity(String cityName) {
        String normalize = normalizeCityName(cityName);
        City city = getCityFromDb(normalize);

        if (city == null) {
            City newCity = getNewCityFromApi(normalize);
            City savedCity = cityPersistenceService.saveCityInDb(newCity.getCityName(), newCity.getTemperature());
            saveFavoriteCityAndSaveDb(savedCity);
            return cityMapper.buildValid(newCity);
        } else if (existsByCityId(city.getId())) {
            return cityMapper.buildWithMessage(city, "Город уже является избранным");
        } else {
            saveFavoriteCityAndSaveDb(city);
            return cityMapper.buildValid(city);
        }
    }

    private String normalizeCityName(String cityName) {
        return cityNameValidationService.normalizeAndValidate(cityName);
    }

    private City getCityFromDb(String cityName) {
        City city = cityPersistenceService.getCityFromDb(cityName);
        if (city == null) return null;
        return city;
    }

    private City getNewCityFromApi(String cityName) {
        WeatherApiResponse weatherApiResponse = weatherClientService.sendRequestWeatherApiClient(cityName);
        return new City(weatherApiResponse.getCityName(), weatherApiResponse.getTemperature());
    }

    private void saveFavoriteCityAndSaveDb(City city) {
        FavoriteCity favoriteCity = buildFavoriteEntity(city);
        favoriteCityRepository.save(favoriteCity);
    }

    private boolean existsByCityId(Long id) {
        return favoriteCityRepository.existsByCityId(id);
    }

    private FavoriteCity buildFavoriteEntity(City city) {
        FavoriteCity favoriteCity = new FavoriteCity();
        favoriteCity.setCity(city);
        favoriteCity.setUpdatedAt(LocalDateTime.now());
        return favoriteCity;
    }

}
