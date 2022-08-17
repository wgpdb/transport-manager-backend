package com.wgpdb.transportmanager.service.weather;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class WeatherServiceConfig {

    @Value("${openweather.api.url}")
    private String openWeatherApiUrl;

    @Value("${openweather.api.key}")
    private String openWeatherApiKey;
}