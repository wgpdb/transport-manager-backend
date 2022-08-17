package com.wgpdb.transportmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wgpdb.transportmanager.domain.enumerantion.Currency;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "trips_revenue")
public class TripRevenue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "revenue_net")
    private BigDecimal revenueNet;

    @Column(name = "commission_net")
    private BigDecimal commissionNet;

    @Column(name = "currency")
    private Currency currency;

    @Column(name = "exchange_rate")
    private Double exchangeRate;

    @Column(name = "echange_rate_date")
    private LocalDate exchangeRateDate;

    @Column(name = "revenue_net_local")
    private BigDecimal revenueNetLocal;

    @Column(name = "commission_net_local")
    private BigDecimal commissionNetLocal;

    @JsonIgnore
    @OneToOne(mappedBy = "tripRevenue")
    private Trip trip;
}