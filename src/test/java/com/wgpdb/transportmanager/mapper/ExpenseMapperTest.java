package com.wgpdb.transportmanager.mapper;

import com.wgpdb.transportmanager.domain.Expense;
import com.wgpdb.transportmanager.domain.dto.ExpenseDto;
import com.wgpdb.transportmanager.domain.enumerantion.Currency;
import com.wgpdb.transportmanager.domain.enumerantion.PaymentType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseMapperTest {

    @Test
    void mapToExpenseDtoTest() {
        //Given
        Expense expense = Expense.builder()
                .expenseDate(LocalDate.now())
                .vendor(null)
                .expenseItems(null)
                .totalNetValue(BigDecimal.valueOf(100))
                .totalGrossValue(BigDecimal.valueOf(123))
                .currency(Currency.PLN)
                .paymentType(PaymentType.CARD)
                .build();

        //When
        ExpenseDto expenseDto = ExpenseMapper.INSTANCE.mapToExpenseDto(expense);

        //Then
        assertEquals(LocalDate.now(), expenseDto.getExpenseDate());
        assertNull(expenseDto.getVendor());
        assertNull(expenseDto.getExpenseItems());
        assertEquals(BigDecimal.valueOf(100), expenseDto.getTotalNetValue());
        assertEquals(BigDecimal.valueOf(123), expenseDto.getTotalGrossValue());
        assertEquals(Currency.PLN, expenseDto.getCurrency());
        assertEquals(PaymentType.CARD, expenseDto.getPaymentType());
    }

    @Test
    void mapToExpenseTest() {
        //Given
        ExpenseDto expenseDto = ExpenseDto.builder()
                .expenseDate(LocalDate.now())
                .vendor(null)
                .expenseItems(null)
                .totalNetValue(BigDecimal.valueOf(100))
                .totalGrossValue(BigDecimal.valueOf(123))
                .currency(Currency.PLN)
                .paymentType(PaymentType.CARD)
                .build();

        //When
        Expense expense = ExpenseMapper.INSTANCE.mapToExpense(expenseDto);

        //Then
        assertEquals(LocalDate.now(), expense.getExpenseDate());
        assertNull(expense.getVendor());
        assertNull(expense.getExpenseItems());
        assertEquals(BigDecimal.valueOf(100), expense.getTotalNetValue());
        assertEquals(BigDecimal.valueOf(123), expense.getTotalGrossValue());
        assertEquals(Currency.PLN, expense.getCurrency());
        assertEquals(PaymentType.CARD, expense.getPaymentType());
    }

    @Test
    void mapToExpenseDtoListTest() {
        //Given
        Expense expense = Expense.builder()
                .expenseDate(LocalDate.now())
                .vendor(null)
                .expenseItems(null)
                .totalNetValue(BigDecimal.valueOf(100))
                .totalGrossValue(BigDecimal.valueOf(123))
                .currency(Currency.PLN)
                .paymentType(PaymentType.CARD)
                .build();

        List<Expense> expenseList = new ArrayList<>();
        expenseList.add(expense);

        //When
        List<ExpenseDto> expenseDtoList = ExpenseMapper.INSTANCE.mapToExpenseDtoList(expenseList);

        //When
        assertNotNull(expenseDtoList);
        assertEquals(LocalDate.now(), expenseDtoList.get(0).getExpenseDate());
        assertNull(expenseDtoList.get(0).getVendor());
        assertNull(expenseDtoList.get(0).getExpenseItems());
        assertEquals(BigDecimal.valueOf(100), expenseDtoList.get(0).getTotalNetValue());
        assertEquals(BigDecimal.valueOf(123), expenseDtoList.get(0).getTotalGrossValue());
        assertEquals(Currency.PLN, expenseDtoList.get(0).getCurrency());
        assertEquals(PaymentType.CARD, expenseDtoList.get(0).getPaymentType());
    }
}