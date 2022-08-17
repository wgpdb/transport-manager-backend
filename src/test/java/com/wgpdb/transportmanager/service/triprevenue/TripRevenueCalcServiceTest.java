package com.wgpdb.transportmanager.service.triprevenue;

import com.wgpdb.transportmanager.domain.Trip;
import com.wgpdb.transportmanager.domain.TripRevenue;
import com.wgpdb.transportmanager.domain.enumerantion.Currency;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TripRevenueCalcServiceTest {

    @Autowired
    private TripRevenueCalcService tripRevenueCalcService;

    @Test
    void foreignCurrencyConverterTest() {
        //Given
        TripRevenue tripRevenue = TripRevenue.builder()
                .revenueNet(BigDecimal.valueOf(100))
                .commissionNet(BigDecimal.valueOf(10))
                .currency(Currency.EUR)
                .exchangeRate(4.6825)
                .build();

        //When
        tripRevenueCalcService.foreignCurrencyConverter(tripRevenue);

        //Then
        assertEquals(BigDecimal.valueOf(468.25), tripRevenue.getRevenueNetLocal());
        assertEquals(BigDecimal.valueOf(46.83), tripRevenue.getCommissionNetLocal());
    }

    @Test
    void foreignCurrencyConverterTripTest() {
        //Given
        TripRevenue tripRevenue = TripRevenue.builder()
                .revenueNet(BigDecimal.valueOf(100))
                .commissionNet(BigDecimal.valueOf(10))
                .currency(Currency.EUR)
                .exchangeRate(4.6825)
                .build();

        Trip trip = Trip.builder()
                .tripRevenue(tripRevenue)
                .build();

        //When
        tripRevenueCalcService.foreignCurrencyConverterTrip(trip);

        //Then
        assertEquals(BigDecimal.valueOf(468.25), trip.getTripRevenue().getRevenueNetLocal());
        assertEquals(BigDecimal.valueOf(46.83), trip.getTripRevenue().getCommissionNetLocal());
    }
}