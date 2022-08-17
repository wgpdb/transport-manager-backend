package com.wgpdb.transportmanager.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "weather_data")
public class WeatherData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "average_temp")
    private Double averageTemp;

    @Column(name = "feels_like_temp")
    private Double feelsLikeTemp;

    @Column(name = "description")
    private String description;
}