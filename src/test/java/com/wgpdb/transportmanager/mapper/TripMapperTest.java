package com.wgpdb.transportmanager.mapper;

import com.wgpdb.transportmanager.domain.Trip;
import com.wgpdb.transportmanager.domain.enumerantion.TripStatus;
import com.wgpdb.transportmanager.domain.dto.TripDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TripMapperTest {

    @Test
    void mapToTripDtoTest() {
        //Given
        Trip trip = Trip.builder()
                .origin("test origin")
                .stopover("test stopover")
                .destination("test destination")
                .tripDate(LocalDate.of(2022, 8, 10))
                .departureTime(LocalTime.of(12, 00))
                .tripStatus(TripStatus.UPCOMING)
                .tripRevenue(null)
                .build();

        //When
        TripDto tripDto = TripMapper.INSTANCE.mapToTripDto(trip);

        //Then
        assertNotNull(tripDto);
        assertEquals("test origin", tripDto.getOrigin());
        assertEquals("test stopover", tripDto.getStopover());
        assertEquals("test destination", tripDto.getDestination());
        assertEquals(LocalDate.of(2022, 8, 10), tripDto.getTripDate());
        assertEquals(LocalTime.of(12, 00), tripDto.getDepartureTime());
        assertEquals(TripStatus.UPCOMING, tripDto.getTripStatus());
        assertNull(tripDto.getTripRevenue());
    }

    @Test
    void mapToTripTest() {
        //Given
        TripDto tripDto = TripDto.builder()
                .origin("test origin")
                .stopover("test stopover")
                .destination("test destination")
                .tripDate(LocalDate.of(2022, 8, 10))
                .departureTime(LocalTime.of(12, 00))
                .tripStatus(TripStatus.COMPLETED)
                .tripRevenue(null)
                .build();

        //When
        Trip trip = TripMapper.INSTANCE.mapToTrip(tripDto);

        //Then
        assertNotNull(tripDto);
        assertEquals("test origin", trip.getOrigin());
        assertEquals("test stopover", trip.getStopover());
        assertEquals("test destination", trip.getDestination());
        assertEquals(LocalDate.of(2022, 8, 10), trip.getTripDate());
        assertEquals(LocalTime.of(12, 00), trip.getDepartureTime());
        assertEquals(TripStatus.COMPLETED, trip.getTripStatus());
        assertNull(trip.getTripRevenue());
    }

    @Test
    void mapToTripDtoListTest() {
        //Given
        Trip trip = Trip.builder()
                .origin("test origin")
                .stopover("test stopover")
                .destination("test destination")
                .tripDate(LocalDate.of(2022, 8, 10))
                .departureTime(LocalTime.of(12, 00))
                .tripStatus(TripStatus.CANCELLED)
                .tripRevenue(null)
                .build();

        List<Trip> tripsList = new ArrayList<>();
        tripsList.add(trip);

        //When
        List<TripDto> tripsDtoList = TripMapper.INSTANCE.mapToTripDtoList(tripsList);

        //Then
        assertNotNull(tripsDtoList);
        assertEquals(1, tripsDtoList.size());
        assertEquals("test origin", tripsDtoList.get(0).getOrigin());
        assertEquals("test stopover", tripsDtoList.get(0).getStopover());
        assertEquals("test destination", tripsDtoList.get(0).getDestination());
        assertEquals(LocalDate.of(2022, 8, 10), tripsDtoList.get(0).getTripDate());
        assertEquals(LocalTime.of(12, 00), tripsDtoList.get(0).getDepartureTime());
        assertEquals(TripStatus.CANCELLED, tripsDtoList.get(0).getTripStatus());
        assertNull(tripsDtoList.get(0).getTripRevenue());
    }
}