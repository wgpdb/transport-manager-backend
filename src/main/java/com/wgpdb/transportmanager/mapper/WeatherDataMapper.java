package com.wgpdb.transportmanager.mapper;

import com.wgpdb.transportmanager.domain.WeatherData;
import com.wgpdb.transportmanager.domain.dto.WeatherDataDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WeatherDataMapper {

    WeatherDataMapper INSTANCE = Mappers.getMapper(WeatherDataMapper.class);

    WeatherDataDto mapToWeatherDataDto(WeatherData weatherData);
    WeatherData mapToWeatherData(WeatherDataDto weatherDataDto);
    List<WeatherDataDto> mapToWeatherDataDtoList(List<WeatherData> weatherDataList);
}