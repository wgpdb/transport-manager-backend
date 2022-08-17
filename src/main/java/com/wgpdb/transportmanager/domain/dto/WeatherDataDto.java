package com.wgpdb.transportmanager.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class WeatherDataDto {

    private Long id;
    private String city;
    private String country;
    private LocalDateTime dateTime;
    private Double averageTemp;
    private Double feelsLikeTemp;
    private String description;
}