package com.wgpdb.transportmanager.service.weather;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WeatherServiceConfigTest {

    @Autowired
    private WeatherServiceConfig weatherServiceConfig;

    @Test
    void getOpenWeatherApiUrlTest() {
        //Given & When
        String openWeatherApiUrl = weatherServiceConfig.getOpenWeatherApiUrl();

        //Then
        assertNotNull(openWeatherApiUrl);
    }

    @Test
    void getOpenWeatherApiKeyTest() {
        //Given & When
        String openWeatherApiKey = weatherServiceConfig.getOpenWeatherApiKey();

        //Then
        assertNotNull(openWeatherApiKey);
    }
}