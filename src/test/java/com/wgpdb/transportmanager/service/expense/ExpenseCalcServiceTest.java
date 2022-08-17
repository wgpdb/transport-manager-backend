package com.wgpdb.transportmanager.service.expense;

import com.wgpdb.transportmanager.domain.ExpenseItem;
import com.wgpdb.transportmanager.domain.enumerantion.VatRate;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseCalcServiceTest {

    @Test
    void calculateGrossValueRoundDownTest() {
        //Given
        BigDecimal netValue = BigDecimal.valueOf(9.7166);
        VatRate vatRate = VatRate.PTU_B;

        //When
        ExpenseCalcService expenseCalcService = new ExpenseCalcService();
        BigDecimal grossValue = expenseCalcService.calculateGrossValue(netValue, vatRate);

        //Then
        assertEquals(BigDecimal.valueOf(10.49), grossValue);
    }

    @Test
    void calculateGrossValueRoundUpTest() {
        //Given
        BigDecimal netValue = BigDecimal.valueOf(9.7176);
        VatRate vatRate = VatRate.PTU_B;

        //When
        ExpenseCalcService expenseCalcService = new ExpenseCalcService();
        BigDecimal grossValue = expenseCalcService.calculateGrossValue(netValue, vatRate);

        //Then
        assertEquals(BigDecimal.valueOf(10.5).setScale(2), grossValue);
    }

    @Test
    void calculateListGrossValueTest() {
        //Given
        BigDecimal netValueOne = BigDecimal.valueOf(10);
        BigDecimal netValueTwo = BigDecimal.valueOf(20);

        VatRate vatRateOne = VatRate.PTU_A;
        VatRate vatRateTwo = VatRate.PTU_B;

        ExpenseItem expenseItemOne = ExpenseItem.builder()
                .netValue(netValueOne)
                .vatRate(vatRateOne)
                .build();

        ExpenseItem expenseItemTwo = ExpenseItem.builder()
                .netValue(netValueTwo)
                .vatRate(vatRateTwo)
                .build();

        List<ExpenseItem> expenseItemList = new ArrayList<>();
        expenseItemList.add(expenseItemOne);
        expenseItemList.add(expenseItemTwo);

        //When
        ExpenseCalcService expenseCalcService = new ExpenseCalcService();
        expenseCalcService.calculateListGrossValue(expenseItemList);

        //Then
        assertEquals(BigDecimal.valueOf(12.30).setScale(2), expenseItemOne.getGrossValue());
        assertEquals(BigDecimal.valueOf(21.60).setScale(2), expenseItemTwo.getGrossValue());
    }

    @Test
    void sumNetExpenseItemsTest() {
        //Given
        BigDecimal valueOne = BigDecimal.valueOf(50);
        BigDecimal valueTwo = BigDecimal.valueOf(50);
        BigDecimal valueThree = BigDecimal.valueOf(100);

        ExpenseItem expenseItemOne = ExpenseItem.builder()
                .netValue(valueOne)
                .build();

        ExpenseItem expenseItemTwo = ExpenseItem.builder()
                .netValue(valueTwo)
                .build();

        ExpenseItem expenseItemThree = ExpenseItem.builder()
                .netValue(valueThree)
                .build();

        List<ExpenseItem> expenseItems = new ArrayList<>();
        expenseItems.add(expenseItemOne);
        expenseItems.add(expenseItemTwo);
        expenseItems.add(expenseItemThree);

        //When
        ExpenseCalcService expenseCalcService = new ExpenseCalcService();
        BigDecimal sum = expenseCalcService.sumNetExpenseItems(expenseItems);

        //Then
        assertEquals(BigDecimal.valueOf(200), sum);
    }

    @Test
    void sumGrossExpenseItemsTest() {
        //Given
        BigDecimal valueOne = BigDecimal.valueOf(50);
        BigDecimal valueTwo = BigDecimal.valueOf(50);
        BigDecimal valueThree = BigDecimal.valueOf(100);

        ExpenseItem expenseItemOne = ExpenseItem.builder()
                .grossValue(valueOne)
                .build();

        ExpenseItem expenseItemTwo = ExpenseItem.builder()
                .grossValue(valueTwo)
                .build();

        ExpenseItem expenseItemThree = ExpenseItem.builder()
                .grossValue(valueThree)
                .build();

        List<ExpenseItem> expenseItems = new ArrayList<>();
        expenseItems.add(expenseItemOne);
        expenseItems.add(expenseItemTwo);
        expenseItems.add(expenseItemThree);

        //When
        ExpenseCalcService expenseCalcService = new ExpenseCalcService();
        BigDecimal sum = expenseCalcService.sumGrossExpenseItems(expenseItems);

        //Then
        assertEquals(BigDecimal.valueOf(200), sum);
    }
}