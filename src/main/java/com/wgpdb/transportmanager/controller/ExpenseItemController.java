package com.wgpdb.transportmanager.controller;

import com.wgpdb.transportmanager.domain.ExpenseItem;
import com.wgpdb.transportmanager.domain.dto.ExpenseItemDto;
import com.wgpdb.transportmanager.exception.ExpenseItemNotFoundException;
import com.wgpdb.transportmanager.mapper.ExpenseItemMapper;
import com.wgpdb.transportmanager.service.dbservice.ExpenseItemDbService;
import com.wgpdb.transportmanager.service.expense.ExpenseFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/expenses/items")
public class ExpenseItemController {

    private final ExpenseItemDbService expenseItemDbService;
    private final ExpenseItemMapper expenseItemMapper;
    private final ExpenseFacade expenseFacade;

    @GetMapping(value = "{id}")
    public ResponseEntity<ExpenseItemDto> getExpenseItem(@PathVariable Long id) throws ExpenseItemNotFoundException {
        return ResponseEntity.ok(expenseItemMapper.INSTANCE.mapToExpenseItemDto
                (expenseItemDbService.getExpenseItem(id)));
    }

    @GetMapping
    public ResponseEntity<List<ExpenseItemDto>> getAllExpenseItems() {
        List<ExpenseItem> expenseItems = expenseItemDbService.getAllExpenseItems();
        return ResponseEntity.ok(expenseItemMapper.INSTANCE.mapToExpenseItemDtoList(expenseItems));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addExpenseItem(@RequestBody ExpenseItemDto expenseItemDto) {
        ExpenseItem expenseItem = expenseItemMapper.INSTANCE.mapToExpenseItem(expenseItemDto);
        expenseFacade.saveExpenseItem(expenseItem);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExpenseItemDto> updateExpenseItem(@RequestBody ExpenseItemDto expenseItemDto)
            throws ExpenseItemNotFoundException {
        ExpenseItem expenseItem = expenseItemMapper.INSTANCE.mapToExpenseItem(expenseItemDto);
        ExpenseItem savedExpenseItem = expenseFacade.updateExpenseItem(expenseItem);
        return ResponseEntity.ok(expenseItemMapper.INSTANCE.mapToExpenseItemDto(savedExpenseItem));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteExpenseItem(@PathVariable Long id) throws ExpenseItemNotFoundException {
        expenseItemDbService.deleteExpenseItem(id);
        return ResponseEntity.ok().build();
    }
}