package com.wgpdb.transportmanager.mapper;

import com.wgpdb.transportmanager.domain.ExpenseItem;
import com.wgpdb.transportmanager.domain.dto.ExpenseItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExpenseItemMapper {

    ExpenseItemMapper INSTANCE = Mappers.getMapper(ExpenseItemMapper.class);

    ExpenseItemDto mapToExpenseItemDto(ExpenseItem expenseItem);
    ExpenseItem mapToExpenseItem(ExpenseItemDto expenseItemDto);
    List<ExpenseItemDto> mapToExpenseItemDtoList(List<ExpenseItem> expenseItems);
}