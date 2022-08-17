package com.wgpdb.transportmanager.service.weather;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WeatherServiceTest {

    @Autowired
    private WeatherService weatherService;

    @Test
    void convertLocalDateTimeFromStringTest() {
        //Given
        String dateTime = "2022-08-10 22:15:30";

        //When
        LocalDateTime localDateTime = weatherService.convertFromString(dateTime);

        //Then
        assertEquals(LocalDateTime.of(2022, 8, 10, 22, 15, 30), localDateTime);
    }
}