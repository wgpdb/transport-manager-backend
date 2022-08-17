package com.wgpdb.transportmanager.domain.dto;

import com.wgpdb.transportmanager.domain.enumerantion.VatRate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ExpenseItemDto {

    private Long id;
    private String description;
    private BigDecimal netValue;
    private VatRate vatRate;
    private BigDecimal grossValue;
}