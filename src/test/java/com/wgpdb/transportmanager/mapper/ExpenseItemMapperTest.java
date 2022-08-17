package com.wgpdb.transportmanager.mapper;

import com.wgpdb.transportmanager.domain.ExpenseItem;
import com.wgpdb.transportmanager.domain.dto.ExpenseItemDto;
import com.wgpdb.transportmanager.domain.enumerantion.VatRate;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseItemMapperTest {

    @Test
    void mapToExpenseItemDtoTest() {
        //Given
        ExpenseItem expenseItem = ExpenseItem.builder()
                .description("test description")
                .netValue(BigDecimal.valueOf(40.65))
                .vatRate(VatRate.PTU_A)
                .grossValue(BigDecimal.valueOf(50))
                .build();

        //When
        ExpenseItemDto expenseItemDto = ExpenseItemMapper.INSTANCE.mapToExpenseItemDto(expenseItem);

        //Then
        assertNotNull(expenseItemDto);
        assertEquals("test description", expenseItemDto.getDescription());
        assertEquals(BigDecimal.valueOf(40.65), expenseItemDto.getNetValue());
        assertEquals(VatRate.PTU_A, expenseItemDto.getVatRate());
        assertEquals(BigDecimal.valueOf(50), expenseItemDto.getGrossValue());
    }

    @Test
    void mapToExpenseItemTest() {
        //Given
        ExpenseItemDto expenseItemDto = ExpenseItemDto.builder()
                .description("test description")
                .netValue(BigDecimal.valueOf(40.65))
                .vatRate(VatRate.PTU_A)
                .grossValue(BigDecimal.valueOf(50))
                .build();

        //When
        ExpenseItem expenseItem = ExpenseItemMapper.INSTANCE.mapToExpenseItem(expenseItemDto);

        //Then
        assertNotNull(expenseItem);
        assertEquals("test description", expenseItem.getDescription());
        assertEquals(BigDecimal.valueOf(40.65), expenseItem.getNetValue());
        assertEquals(VatRate.PTU_A, expenseItem.getVatRate());
        assertEquals(BigDecimal.valueOf(50), expenseItem.getGrossValue());
    }

    @Test
    void mapToExpenseItemDtoListTest() {
        //Given
        ExpenseItem expenseItem = ExpenseItem.builder()
                .description("test description")
                .netValue(BigDecimal.valueOf(46.30))
                .vatRate(VatRate.PTU_B)
                .grossValue(BigDecimal.valueOf(50))
                .build();

        List<ExpenseItem> expenseItemList = new ArrayList<>();
        expenseItemList.add(expenseItem);

        //When
        List<ExpenseItemDto> expenseItemDtoList = ExpenseItemMapper.INSTANCE.mapToExpenseItemDtoList(expenseItemList);

        //Then
        assertNotNull(expenseItemDtoList);
        assertEquals("test description", expenseItemDtoList.get(0).getDescription());
        assertEquals(BigDecimal.valueOf(46.30), expenseItemDtoList.get(0).getNetValue());
        assertEquals(VatRate.PTU_B, expenseItemDtoList.get(0).getVatRate());
        assertEquals(BigDecimal.valueOf(50), expenseItemDtoList.get(0).getGrossValue());

    }
}