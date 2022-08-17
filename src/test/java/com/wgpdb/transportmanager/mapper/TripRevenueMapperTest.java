package com.wgpdb.transportmanager.mapper;

import com.wgpdb.transportmanager.domain.enumerantion.Currency;
import com.wgpdb.transportmanager.domain.TripRevenue;
import com.wgpdb.transportmanager.domain.dto.TripRevenueDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TripRevenueMapperTest {

    @Test
    void mapToTripRevenueDtoTest() {
        //Given
        TripRevenue tripRevenue = TripRevenue.builder()
                .revenueNet(BigDecimal.valueOf(100))
                .commissionNet(BigDecimal.valueOf(10))
                .currency(Currency.EUR)
                .trip(null)
                .build();

        //When
        TripRevenueDto tripRevenueDto = TripRevenueMapper.INSTANCE.mapToTripRevenueDto(tripRevenue);

        //Then
        assertNotNull(tripRevenueDto);
        assertEquals(BigDecimal.valueOf(100), tripRevenueDto.getRevenueNet());
        assertEquals(BigDecimal.valueOf(10), tripRevenueDto.getCommissionNet());
        assertEquals(Currency.EUR, tripRevenueDto.getCurrency());
        assertNull(tripRevenueDto.getTrip());
    }

    @Test
    void mapToTripRevenueTest() {
        //Given
        TripRevenueDto tripRevenueDto = TripRevenueDto.builder()
                .revenueNet(BigDecimal.valueOf(100))
                .commissionNet(BigDecimal.valueOf(10))
                .currency(Currency.PLN)
                .trip(null)
                .build();

        //When
        TripRevenue tripRevenue = TripRevenueMapper.INSTANCE.mapToTripRevenue(tripRevenueDto);

        //Then
        assertNotNull(tripRevenue);
        assertEquals(BigDecimal.valueOf(100), tripRevenue.getRevenueNet());
        assertEquals(BigDecimal.valueOf(10), tripRevenue.getCommissionNet());
        assertEquals(Currency.PLN, tripRevenue.getCurrency());
        assertNull(tripRevenue.getTrip());
    }

    @Test
    void mapToTripRevenueDtoListTest() {
        //Given
        TripRevenue tripRevenue = TripRevenue.builder()
                .revenueNet(BigDecimal.valueOf(100))
                .commissionNet(BigDecimal.valueOf(10))
                .currency(Currency.USD)
                .trip(null)
                .build();

        List<TripRevenue> tripRevenueList = new ArrayList<>();
        tripRevenueList.add(tripRevenue);

        //When
        List<TripRevenueDto> tripRevenueDtoList = TripRevenueMapper.INSTANCE.mapToTripRevenueDtoList(tripRevenueList);

        //Then
        assertNotNull(tripRevenueDtoList);
        assertEquals(1, tripRevenueDtoList.size());
        assertEquals(BigDecimal.valueOf(100), tripRevenueDtoList.get(0).getRevenueNet());
        assertEquals(BigDecimal.valueOf(10), tripRevenueDtoList.get(0).getCommissionNet());
        assertEquals(Currency.USD, tripRevenueDtoList.get(0).getCurrency());
        assertNull(tripRevenueDtoList.get(0).getTrip());
    }
}