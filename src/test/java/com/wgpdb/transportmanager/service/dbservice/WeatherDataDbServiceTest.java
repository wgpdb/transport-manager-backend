package com.wgpdb.transportmanager.service.dbservice;

import com.wgpdb.transportmanager.domain.WeatherData;
import com.wgpdb.transportmanager.exception.WeatherDataNotFoundException;
import com.wgpdb.transportmanager.repository.WeatherDataRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
public class WeatherDataDbServiceTest {

    @Autowired
    private WeatherDataDbService weatherDataDbService;

    @Autowired
    private WeatherDataRepository weatherDataRepository;

    @Container
    private static MySQLContainer container = new MySQLContainer("mysql:latest");

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

    @AfterEach
    void cleanup() {
        weatherDataRepository.deleteAll();
    }

    @Test
    void getWeatherDataTest() throws WeatherDataNotFoundException {
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
        weatherDataDbService.saveWeatherData(weatherData);
        Long id = weatherData.getId();

        //Then
        assertTrue(weatherDataRepository.existsById(id));
        assertEquals("test city", weatherDataDbService.getWeatherData(id).getCity());
        assertEquals("test country", weatherDataDbService.getWeatherData(id).getCountry());
        assertEquals(LocalDateTime.of(2022, 8, 15, 12, 30),
                weatherDataDbService.getWeatherData(id).getDateTime());
        assertEquals(25.5, weatherDataDbService.getWeatherData(id).getAverageTemp());
        assertEquals(27.8, weatherDataDbService.getWeatherData(id).getFeelsLikeTemp());
        assertEquals("test description", weatherDataDbService.getWeatherData(id).getDescription());
    }

    @Test
    void getNonExistentWeatherDataThrowsWeatherDataNotFoundException() {
        //Given
        WeatherData weatherData = WeatherData.builder().build();

        //When
        weatherDataDbService.saveWeatherData(weatherData);
        Long id = weatherData.getId();
        Long nonExistentId = id + 1;

        //When
        assertTrue(weatherDataRepository.existsById(id));
        assertThrows(WeatherDataNotFoundException.class, () -> weatherDataDbService.getWeatherData(nonExistentId));
    }

    @Test
    void getAllWeatherDataTest() {
        //Given
        WeatherData weatherDataOne = WeatherData.builder().build();
        WeatherData weatherDataTwo = WeatherData.builder().build();

        //When
        weatherDataDbService.saveWeatherData(weatherDataOne);
        weatherDataDbService.saveWeatherData(weatherDataTwo);

        //Then
        assertEquals(2, weatherDataDbService.getAllWeatherData().size());
    }

    @Test
    void updateWeatherDataTest() throws WeatherDataNotFoundException {
        //Given
        WeatherData weatherData = WeatherData.builder()
                .city("test city")
                .country("test country")
                .dateTime(LocalDateTime.of(2022, 8, 15, 12, 30))
                .averageTemp(25.5)
                .feelsLikeTemp(27.8)
                .description("test description")
                .build();

        weatherDataDbService.saveWeatherData(weatherData);
        Long id = weatherData.getId();

        //When
        WeatherData updatedWeatherData = WeatherData.builder()
                .id(id)
                .city("updated city")
                .averageTemp(22.8)
                .description("updated description")
                .build();

        weatherDataDbService.updateWeatherData(updatedWeatherData);

        //Then
        assertEquals("updated city", weatherDataDbService.getWeatherData(id).getCity());
        assertEquals(22.8, weatherDataDbService.getWeatherData(id).getAverageTemp());
        assertEquals("updated description", weatherDataDbService.getWeatherData(id).getDescription());
    }

    @Test
    void deleteWeatherDataTest() throws WeatherDataNotFoundException {
        //Given
        WeatherData weatherData = WeatherData.builder()
                .city("test city")
                .country("test country")
                .dateTime(LocalDateTime.of(2022, 8, 15, 12, 30))
                .averageTemp(25.5)
                .feelsLikeTemp(27.8)
                .description("test description")
                .build();

        weatherDataDbService.saveWeatherData(weatherData);
        Long id = weatherData.getId();

        //When
        weatherDataDbService.deleteWeatherData(id);

        //Then
        assertFalse(weatherDataRepository.existsById(id));
    }
}