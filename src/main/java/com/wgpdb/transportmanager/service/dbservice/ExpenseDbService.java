package com.wgpdb.transportmanager.service.dbservice;

import com.wgpdb.transportmanager.domain.Expense;
import com.wgpdb.transportmanager.exception.ExpenseNotFoundException;
import com.wgpdb.transportmanager.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseDbService {

    @Autowired
    private final ExpenseRepository expenseRepository;

    public Expense getExpense(final Long id) throws ExpenseNotFoundException {
        return expenseRepository.findById(id).orElseThrow(ExpenseNotFoundException::new);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense saveExpense(final Expense expense) {
        return expenseRepository.save(expense);
    }

    public Expense updateExpense(final Expense expense) throws ExpenseNotFoundException {
        if (expenseRepository.existsById(expense.getId())) {
            return expenseRepository.save(expense);
        } else {
            throw new ExpenseNotFoundException();
        }
    }

    public void deleteExpense(final Long id) throws ExpenseNotFoundException {
        if (expenseRepository.existsById(id)) {
            expenseRepository.deleteById(id);
        } else {
            throw new ExpenseNotFoundException();
        }
    }
}