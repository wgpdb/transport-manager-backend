package com.wgpdb.transportmanager.mapper;

import com.wgpdb.transportmanager.domain.Trip;
import com.wgpdb.transportmanager.domain.dto.TripDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TripPageMapper {

    private final TripMapper tripMapper;

    private TripDto mapToTripDto(Trip trip) {
        return tripMapper.INSTANCE.mapToTripDto(trip);
    }

    public Page<TripDto> mapToTripDtoPage(Page<Trip> trips) {
        if (trips == null) {
            return null;
        }
        return trips.map(this::mapToTripDto);
    }
}