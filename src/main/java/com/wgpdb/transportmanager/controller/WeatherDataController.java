package com.wgpdb.transportmanager.controller;

import com.wgpdb.transportmanager.domain.WeatherData;
import com.wgpdb.transportmanager.domain.dto.WeatherDataDto;
import com.wgpdb.transportmanager.exception.WeatherDataNotFoundException;
import com.wgpdb.transportmanager.mapper.WeatherDataMapper;
import com.wgpdb.transportmanager.service.dbservice.WeatherDataDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/trips/weather_data")
public class WeatherDataController {

    private final WeatherDataDbService weatherDataDbService;
    private final WeatherDataMapper weatherDataMapper;

    @GetMapping(value = "{id}")
    public ResponseEntity<WeatherDataDto> getWeatherData(@PathVariable Long id) throws WeatherDataNotFoundException {
        return ResponseEntity.ok(weatherDataMapper.INSTANCE.mapToWeatherDataDto(
                weatherDataDbService.getWeatherData(id)));
    }

    @GetMapping
    public ResponseEntity<List<WeatherDataDto>> getAllWeatherData() {
        List<WeatherData> weatherData = weatherDataDbService.getAllWeatherData();
        return ResponseEntity.ok(weatherDataMapper.INSTANCE.mapToWeatherDataDtoList(weatherData));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addWeatherData(@RequestBody WeatherDataDto weatherDataDto) {
        WeatherData weatherData = weatherDataMapper.INSTANCE.mapToWeatherData(weatherDataDto);
        weatherDataDbService.saveWeatherData(weatherData);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WeatherDataDto> updateWeatherData(@RequestBody WeatherDataDto weatherDataDto)
            throws WeatherDataNotFoundException {
        WeatherData weatherData = weatherDataMapper.INSTANCE.mapToWeatherData(weatherDataDto);
        WeatherData savedWeatherData = weatherDataDbService.updateWeatherData(weatherData);
        return ResponseEntity.ok(weatherDataMapper.INSTANCE.mapToWeatherDataDto(savedWeatherData));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteWeatherData(@PathVariable Long id) throws WeatherDataNotFoundException {
        weatherDataDbService.deleteWeatherData(id);
        return ResponseEntity.ok().build();
    }
}