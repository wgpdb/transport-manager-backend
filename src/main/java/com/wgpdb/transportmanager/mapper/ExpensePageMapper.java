package com.wgpdb.transportmanager.mapper;

import com.wgpdb.transportmanager.domain.Expense;
import com.wgpdb.transportmanager.domain.dto.ExpenseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExpensePageMapper {

    private final ExpenseMapper expenseMapper;

    private ExpenseDto mapToExpenseDto(Expense expense) {
        return expenseMapper.INSTANCE.mapToExpenseDto(expense);
    }

    public Page<ExpenseDto> mapToExpenseDtoPage(Page<Expense> expenses) {
        if (expenses == null) {
            return null;
        }
        return expenses.map(this::mapToExpenseDto);
    }
}