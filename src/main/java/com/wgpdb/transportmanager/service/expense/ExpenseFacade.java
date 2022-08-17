package com.wgpdb.transportmanager.service.expense;

import com.wgpdb.transportmanager.domain.Expense;
import com.wgpdb.transportmanager.domain.ExpenseItem;
import com.wgpdb.transportmanager.exception.ExpenseItemNotFoundException;
import com.wgpdb.transportmanager.exception.ExpenseNotFoundException;
import com.wgpdb.transportmanager.repository.ExpenseItemRepository;
import com.wgpdb.transportmanager.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ExpenseFacade {

    private final ExpenseCalcService expenseCalcService;
    private final ExpenseRepository expenseRepository;
    private final ExpenseItemRepository expenseItemRepository;

    public Expense saveExpense(final Expense expense) {
        expenseCalcService.calculateListGrossValue(expense.getExpenseItems());
        BigDecimal sumNetExpenseItems = expenseCalcService.sumNetExpenseItems(expense.getExpenseItems());
        BigDecimal sumGrossExpenseItems = expenseCalcService.sumGrossExpenseItems(expense.getExpenseItems());
        expense.setTotalNetValue(sumNetExpenseItems);
        expense.setTotalGrossValue(sumGrossExpenseItems);
        return expenseRepository.save(expense);
    }

    public Expense updateExpense(final Expense expense) throws ExpenseNotFoundException {
        if (expenseRepository.existsById(expense.getId())) {
            expenseCalcService.calculateListGrossValue(expense.getExpenseItems());
            BigDecimal sumNetExpenseItems = expenseCalcService.sumNetExpenseItems(expense.getExpenseItems());
            BigDecimal sumGrossExpenseItems = expenseCalcService.sumGrossExpenseItems(expense.getExpenseItems());
            expense.setTotalNetValue(sumNetExpenseItems);
            expense.setTotalGrossValue(sumGrossExpenseItems);
            return expenseRepository.save(expense);
        } else {
            throw new ExpenseNotFoundException();
        }
    }

    public ExpenseItem saveExpenseItem(final ExpenseItem expenseItem) {
        BigDecimal grossValue = expenseCalcService.calculateGrossValue(
                expenseItem.getNetValue(), expenseItem.getVatRate());
        expenseItem.setGrossValue(grossValue);
        return expenseItemRepository.save(expenseItem);
    }

    public ExpenseItem updateExpenseItem(final ExpenseItem expenseItem) throws ExpenseItemNotFoundException {
        if (expenseItemRepository.existsById(expenseItem.getId())) {
            BigDecimal grossValue = expenseCalcService.calculateGrossValue(
                    expenseItem.getNetValue(), expenseItem.getVatRate());
            expenseItem.setGrossValue(grossValue);
            return expenseItemRepository.save(expenseItem);
        } else {
            throw new ExpenseItemNotFoundException();
        }
    }
}