package com.wgpdb.transportmanager.domain.dto;

import com.wgpdb.transportmanager.domain.ExpenseItem;
import com.wgpdb.transportmanager.domain.Vendor;
import com.wgpdb.transportmanager.domain.enumerantion.Currency;
import com.wgpdb.transportmanager.domain.enumerantion.PaymentType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class ExpenseDto {

    private Long id;
    private LocalDate expenseDate;
    private Vendor vendor;
    private List<ExpenseItem> expenseItems;
    private BigDecimal totalNetValue;
    private BigDecimal totalGrossValue;
    private Currency currency;
    private PaymentType paymentType;
}