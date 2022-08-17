package com.wgpdb.transportmanager.mapper;

import com.wgpdb.transportmanager.domain.TripRevenue;
import com.wgpdb.transportmanager.domain.dto.TripRevenueDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TripRevenueMapper {

    TripRevenueMapper INSTANCE = Mappers.getMapper(TripRevenueMapper.class);

    TripRevenueDto mapToTripRevenueDto(TripRevenue tripRevenue);
    TripRevenue mapToTripRevenue(TripRevenueDto tripRevenueDto);
    List<TripRevenueDto> mapToTripRevenueDtoList(List<TripRevenue> tripRevenueList);
}