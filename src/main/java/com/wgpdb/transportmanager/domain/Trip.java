package com.wgpdb.transportmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wgpdb.transportmanager.domain.enumerantion.TripStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "origin")
    private String origin;

    @Column(name = "stopover")
    private String stopover;

    @Column(name = "destination")
    private String destination;

    @Column(name = "trip_date")
    private LocalDate tripDate;

    @Column(name = "departure_time")
    private LocalTime departureTime;

    @Column(name = "trip_status")
    private TripStatus tripStatus;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "trips_revenue_id")
    private TripRevenue tripRevenue;

    @CreationTimestamp
    @Column(name = "created", nullable = false, updatable = false)
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "edited")
    private LocalDateTime edited;

    @JsonIgnore
    @OneToMany(
            targetEntity = WeatherData.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "trip_id", referencedColumnName = "id")
    private List<WeatherData> weatherData;
}