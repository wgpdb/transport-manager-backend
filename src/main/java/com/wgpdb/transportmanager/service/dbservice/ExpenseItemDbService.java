package com.wgpdb.transportmanager.service.dbservice;

import com.wgpdb.transportmanager.domain.ExpenseItem;
import com.wgpdb.transportmanager.exception.ExpenseItemNotFoundException;
import com.wgpdb.transportmanager.repository.ExpenseItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseItemDbService {

    @Autowired
    private final ExpenseItemRepository expenseItemRepository;

    public ExpenseItem getExpenseItem(final Long id) throws ExpenseItemNotFoundException {
        return expenseItemRepository.findById(id).orElseThrow(ExpenseItemNotFoundException::new);
    }

    public List<ExpenseItem> getAllExpenseItems() {
        return expenseItemRepository.findAll();
    }

    public ExpenseItem saveExpenseItem(final ExpenseItem expenseItem) {
        return expenseItemRepository.save(expenseItem);
    }

    public ExpenseItem updateExpenseItem(final ExpenseItem expenseItem) throws ExpenseItemNotFoundException {
        if (expenseItemRepository.existsById(expenseItem.getId())) {
            return expenseItemRepository.save(expenseItem);
        } else {
            throw new ExpenseItemNotFoundException();
        }
    }

    public void deleteExpenseItem(final Long id) throws ExpenseItemNotFoundException {
        if (expenseItemRepository.existsById(id)) {
            expenseItemRepository.deleteById(id);
        } else {
            throw new ExpenseItemNotFoundException();
        }
    }
}