package com.wgpdb.transportmanager.mapper;

import com.wgpdb.transportmanager.domain.WeatherData;
import com.wgpdb.transportmanager.domain.dto.WeatherDataDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WeatherDataMapperTest {

    @Test
    void mapToWeatherDataDtoTest() {
        //Given
        WeatherData weatherData = WeatherData.builder()
                .city("test city")
                .country("test country")
                .dateTime(LocalDateTime.of(2022, 8, 15, 12, 30))
                .averageTemp(25.5)
                .feelsLikeTemp(27.8)
                .description("test description")
                .build();

        //When
        WeatherDataDto weatherDataDto = WeatherDataMapper.INSTANCE.mapToWeatherDataDto(weatherData);

        //Then
        assertNotNull(weatherDataDto);
        assertEquals("test city", weatherDataDto.getCity());
        assertEquals("test country", weatherDataDto.getCountry());
        assertEquals(LocalDateTime.of(2022, 8, 15, 12, 30),
                weatherDataDto.getDateTime());
        assertEquals(25.5, weatherDataDto.getAverageTemp());
        assertEquals(27.8, weatherDataDto.getFeelsLikeTemp());
        assertEquals("test description", weatherDataDto.getDescription());
    }

    @Test
    void mapToWeatherDataTest() {
        //Given
        WeatherDataDto weatherDataDto = WeatherDataDto.builder()
                .city("test city")
                .country("test country")
                .dateTime(LocalDateTime.of(2022, 8, 15, 12, 30))
                .averageTemp(25.5)
                .feelsLikeTemp(27.8)
                .description("test description")
                .build();

        //When
        WeatherData weatherData = WeatherDataMapper.INSTANCE.mapToWeatherData(weatherDataDto);

        //Then
        assertNotNull(weatherData);
        assertEquals("test city", weatherData.getCity());
        assertEquals("test country", weatherData.getCountry());
        assertEquals(LocalDateTime.of(2022, 8, 15, 12, 30),
                weatherData.getDateTime());
        assertEquals(25.5, weatherData.getAverageTemp());
        assertEquals(27.8, weatherData.getFeelsLikeTemp());
        assertEquals("test description", weatherData.getDescription());
    }

    @Test
    void mapToWeatherDataDtoListTest() {
        //Given
        WeatherData weatherData = WeatherData.builder()
                .city("test city")
                .country("test country")
                .dateTime(LocalDateTime.of(2022, 8, 15, 12, 30))
                .averageTemp(25.5)
                .feelsLikeTemp(27.8)
                .description("test description")
                .build();

        List<WeatherData> weatherDataList = new ArrayList<>();
        weatherDataList.add(weatherData);

        //When
        List<WeatherDataDto> weatherDataDtoList = WeatherDataMapper.INSTANCE.mapToWeatherDataDtoList(weatherDataList);

        //Then
        assertNotNull(weatherDataDtoList);
        assertEquals("test city", weatherDataDtoList.get(0).getCity());
        assertEquals("test country", weatherDataDtoList.get(0).getCountry());
        assertEquals(LocalDateTime.of(2022, 8, 15, 12, 30),
                weatherDataDtoList.get(0).getDateTime());
        assertEquals(25.5, weatherDataDtoList.get(0).getAverageTemp());
        assertEquals(27.8, weatherDataDtoList.get(0).getFeelsLikeTemp());
        assertEquals("test description", weatherDataDtoList.get(0).getDescription());

    }
}