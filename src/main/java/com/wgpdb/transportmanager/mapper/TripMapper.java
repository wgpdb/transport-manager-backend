package com.wgpdb.transportmanager.mapper;

import com.wgpdb.transportmanager.domain.Trip;
import com.wgpdb.transportmanager.domain.dto.TripDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TripMapper {

    TripMapper INSTANCE = Mappers.getMapper(TripMapper.class);

    TripDto mapToTripDto(Trip trip);
    Trip mapToTrip(TripDto tripDto);
    List<TripDto> mapToTripDtoList(List<Trip> trips);
}