package com.wgpdb.transportmanager.domain.dto;

import com.wgpdb.transportmanager.domain.Trip;
import com.wgpdb.transportmanager.domain.enumerantion.Currency;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class TripRevenueDto {

    private Long id;
    private BigDecimal revenueNet;
    private BigDecimal commissionNet;
    private Currency currency;
    private Double exchangeRate;
    private LocalDate exchangeRateDate;
    private BigDecimal revenueNetLocal;
    private BigDecimal commissionNetLocal;
    private Trip trip;
}