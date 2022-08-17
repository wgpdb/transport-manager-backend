package com.wgpdb.transportmanager.controller;

import com.wgpdb.transportmanager.domain.Expense;
import com.wgpdb.transportmanager.domain.dto.ExpenseDto;
import com.wgpdb.transportmanager.exception.ExpenseNotFoundException;
import com.wgpdb.transportmanager.mapper.ExpenseMapper;
import com.wgpdb.transportmanager.service.dbservice.ExpenseDbService;
import com.wgpdb.transportmanager.service.expense.ExpenseFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseDbService expenseDbService;
    private final ExpenseMapper expenseMapper;
    private final ExpenseFacade expenseFacade;

    @GetMapping(value = "{id}")
    public ResponseEntity<ExpenseDto> getExpense(@PathVariable Long id) throws ExpenseNotFoundException {
        return ResponseEntity.ok(expenseMapper.INSTANCE.mapToExpenseDto(expenseDbService.getExpense(id)));
    }

    @GetMapping
    public ResponseEntity<List<ExpenseDto>> getAllExpenses() {
        List<Expense> expenses = expenseDbService.getAllExpenses();
        return ResponseEntity.ok(expenseMapper.INSTANCE.mapToExpenseDtoList(expenses));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addExpense(@RequestBody ExpenseDto expenseDto) {
        Expense expense = expenseMapper.INSTANCE.mapToExpense(expenseDto);
        expenseFacade.saveExpense(expense);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExpenseDto> updateExpense(@RequestBody ExpenseDto expenseDto)
            throws ExpenseNotFoundException {
        Expense expense = expenseMapper.INSTANCE.mapToExpense(expenseDto);
        Expense savedExpense = expenseFacade.updateExpense(expense);
        return ResponseEntity.ok(expenseMapper.INSTANCE.mapToExpenseDto(savedExpense));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) throws ExpenseNotFoundException {
        expenseDbService.deleteExpense(id);
        return ResponseEntity.ok().build();
    }
}