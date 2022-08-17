package com.wgpdb.transportmanager.mapper;

import com.wgpdb.transportmanager.domain.Expense;
import com.wgpdb.transportmanager.domain.dto.ExpenseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    ExpenseMapper INSTANCE = Mappers.getMapper(ExpenseMapper.class);

    ExpenseDto mapToExpenseDto(Expense expense);
    Expense mapToExpense(ExpenseDto expenseDto);
    List<ExpenseDto> mapToExpenseDtoList(List<Expense> expenses);
}