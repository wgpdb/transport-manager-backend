package com.wgpdb.transportmanager.service.triprevenue;

import com.wgpdb.transportmanager.domain.Trip;
import com.wgpdb.transportmanager.domain.TripRevenue;
import com.wgpdb.transportmanager.domain.enumerantion.Currency;
import com.wgpdb.transportmanager.domain.enumerantion.TripStatus;
import com.wgpdb.transportmanager.service.exchangerate.ExchangeRateService;
import com.wgpdb.transportmanager.service.exchangerate.Rate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TripRevenueCalcService {

    private final ExchangeRateService exchangeRateService;

    public void exchangeRateRetriever(TripRevenue tripRevenue) {
        if (tripRevenue.getTrip().getTripStatus().equals(TripStatus.COMPLETED) &&
                tripRevenue.getCurrency() != Currency.PLN) {

            Optional<Rate> rate = Optional.ofNullable(exchangeRateService.retrieveRate(tripRevenue.getCurrency(),
                    tripRevenue.getTrip().getTripDate()));

            if (rate.isPresent()) {
                tripRevenue.setExchangeRate(rate.get().getMid());
                tripRevenue.setExchangeRateDate(rate.get().getEffectiveDate());
            }
        }
    }

    public void exchangeRateRetrieverTrip(Trip trip) {
        if (trip.getTripStatus().equals(TripStatus.COMPLETED) &&
                trip.getTripRevenue().getCurrency() != Currency.PLN) {

            Optional<Rate> rate = Optional.ofNullable(exchangeRateService.retrieveRate(trip.getTripRevenue().getCurrency(),
                    trip.getTripDate()));

            if (rate.isPresent()) {
                trip.getTripRevenue().setExchangeRate(rate.get().getMid());
                trip.getTripRevenue().setExchangeRateDate(rate.get().getEffectiveDate());
            }
        }
    }

    public void foreignCurrencyConverter(TripRevenue tripRevenue) {
        if (tripRevenue.getCurrency() != Currency.PLN && tripRevenue.getExchangeRate() != null) {
            Double rate = tripRevenue.getExchangeRate();

            BigDecimal revenueNetLocal = tripRevenue.getRevenueNet().multiply(BigDecimal.valueOf(rate));
            tripRevenue.setRevenueNetLocal(revenueNetLocal.setScale(2, RoundingMode.HALF_UP));

            BigDecimal commissionNetLocal = tripRevenue.getCommissionNet().multiply(BigDecimal.valueOf(rate));
            tripRevenue.setCommissionNetLocal(commissionNetLocal.setScale(2, RoundingMode.HALF_UP));
        }
    }

    public void foreignCurrencyConverterTrip(Trip trip) {
        if (trip.getTripRevenue().getCurrency() != Currency.PLN && trip.getTripRevenue().getExchangeRate() != null) {
            Double rate = trip.getTripRevenue().getExchangeRate();

            BigDecimal revenueNetLocal = trip.getTripRevenue().getRevenueNet().multiply(BigDecimal.valueOf(rate));
            trip.getTripRevenue().setRevenueNetLocal(revenueNetLocal.setScale(2, RoundingMode.HALF_UP));

            BigDecimal commissionNetLocal = trip.getTripRevenue().getCommissionNet().multiply(BigDecimal.valueOf(rate));
            trip.getTripRevenue().setCommissionNetLocal(commissionNetLocal.setScale(2, RoundingMode.HALF_UP));
        }
    }
}