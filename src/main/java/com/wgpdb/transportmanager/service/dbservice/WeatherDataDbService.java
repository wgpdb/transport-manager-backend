package com.wgpdb.transportmanager.service.dbservice;

import com.wgpdb.transportmanager.domain.WeatherData;
import com.wgpdb.transportmanager.exception.WeatherDataNotFoundException;
import com.wgpdb.transportmanager.repository.WeatherDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherDataDbService {

    @Autowired
    private final WeatherDataRepository weatherDataRepository;

    public WeatherData getWeatherData(final Long id) throws WeatherDataNotFoundException {
        return weatherDataRepository.findById(id).orElseThrow(WeatherDataNotFoundException::new);
    }

    public List<WeatherData> getAllWeatherData() {
        return weatherDataRepository.findAll();
    }

    public WeatherData saveWeatherData(final WeatherData weatherData) {
        return weatherDataRepository.save(weatherData);
    }

    public WeatherData updateWeatherData(final WeatherData weatherData) throws WeatherDataNotFoundException {
        if (weatherDataRepository.existsById(weatherData.getId())) {
            return weatherDataRepository.save(weatherData);
        } else {
            throw new WeatherDataNotFoundException();
        }
    }

    public void deleteWeatherData(final Long id) throws WeatherDataNotFoundException {
        if (weatherDataRepository.existsById(id)) {
            weatherDataRepository.deleteById(id);
        } else {
            throw new WeatherDataNotFoundException();
        }
    }
}