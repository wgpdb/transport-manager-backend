package com.wgpdb.transportmanager.service.expense;

import com.wgpdb.transportmanager.domain.ExpenseItem;
import com.wgpdb.transportmanager.domain.enumerantion.VatRate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ExpenseCalcService {

    public BigDecimal calculateGrossValue(BigDecimal netValue, VatRate vatRate) {
        BigDecimal vatMultiplier = BigDecimal.valueOf(vatRate.getValue() / 100d + 1d);
        BigDecimal grossValue = vatMultiplier.multiply(netValue);
        return grossValue.setScale(2, RoundingMode.HALF_UP);
    }

    public void calculateListGrossValue(List<ExpenseItem> expenseItemList) {
        for (ExpenseItem expenseItem : expenseItemList) {
            BigDecimal grossValue = calculateGrossValue(expenseItem.getNetValue(), expenseItem.getVatRate());
            expenseItem.setGrossValue(grossValue);
        }
    }

    public BigDecimal sumNetExpenseItems(List<ExpenseItem> expenseItems) {
        return expenseItems.stream()
                .map(ExpenseItem::getNetValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal sumGrossExpenseItems(List<ExpenseItem> expenseItems) {
        return expenseItems.stream()
                .map(ExpenseItem::getGrossValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}