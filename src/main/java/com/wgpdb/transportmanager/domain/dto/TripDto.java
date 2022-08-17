package com.wgpdb.transportmanager.domain.dto;

import com.wgpdb.transportmanager.domain.TripRevenue;
import com.wgpdb.transportmanager.domain.WeatherData;
import com.wgpdb.transportmanager.domain.enumerantion.TripStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Builder
public class TripDto {

    private Long id;
    private String origin;
    private String stopover;
    private String destination;
    private LocalDate tripDate;
    private LocalTime departureTime;
    private TripStatus tripStatus;
    private TripRevenue tripRevenue;
    private LocalDateTime created;
    private LocalDateTime edited;
    private List<WeatherData> weatherData;
}