package com.wgpdb.transportmanager.service.expense;

import com.wgpdb.transportmanager.domain.Expense;
import com.wgpdb.transportmanager.domain.ExpenseItem;
import com.wgpdb.transportmanager.domain.enumerantion.VatRate;
import com.wgpdb.transportmanager.exception.ExpenseItemNotFoundException;
import com.wgpdb.transportmanager.exception.ExpenseNotFoundException;
import com.wgpdb.transportmanager.repository.ExpenseItemRepository;
import com.wgpdb.transportmanager.repository.ExpenseRepository;
import com.wgpdb.transportmanager.service.dbservice.ExpenseDbService;
import com.wgpdb.transportmanager.service.dbservice.ExpenseItemDbService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
class ExpenseFacadeTest {

    @Autowired
    private ExpenseFacade expenseFacade;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseDbService expenseDbService;

    @Autowired
    private ExpenseItemRepository expenseItemRepository;

    @Autowired
    private ExpenseItemDbService expenseItemDbService;

    @Container
    private static MySQLContainer container = new MySQLContainer("mysql:latest");

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

    @AfterEach
    void cleanup() {
        expenseRepository.deleteAll();
        expenseItemRepository.deleteAll();
    }

    @Test
    void saveExpenseTest() throws ExpenseNotFoundException {
        //Given
        ExpenseItem expenseItemOne = ExpenseItem.builder()
                .netValue(BigDecimal.valueOf(50))
                .vatRate(VatRate.PTU_A)
                .build();

        ExpenseItem expenseItemTwo = ExpenseItem.builder()
                .netValue(BigDecimal.valueOf(100))
                .vatRate(VatRate.PTU_B)
                .build();

        List<ExpenseItem> expenseItemList = new ArrayList<>();
        expenseItemList.add(expenseItemOne);
        expenseItemList.add(expenseItemTwo);

        Expense expense = Expense.builder()
                .expenseItems(expenseItemList)
                .build();

        //When
        expenseFacade.saveExpense(expense);
        Long id = expense.getId();

        //When
        assertTrue(expenseRepository.existsById(id));
        assertEquals(BigDecimal.valueOf(150).setScale(2), expenseDbService.getExpense(id).getTotalNetValue());
        assertEquals(BigDecimal.valueOf(169.50).setScale(2), expenseDbService.getExpense(id).getTotalGrossValue());
    }

    @Test
    void updateExpenseTest() throws ExpenseNotFoundException {
        //Given
        ExpenseItem expenseItemOne = ExpenseItem.builder()
                .netValue(BigDecimal.valueOf(50))
                .vatRate(VatRate.PTU_A)
                .build();

        ExpenseItem expenseItemTwo = ExpenseItem.builder()
                .netValue(BigDecimal.valueOf(100))
                .vatRate(VatRate.PTU_B)
                .build();

        List<ExpenseItem> expenseItemList = new ArrayList<>();
        expenseItemList.add(expenseItemOne);
        expenseItemList.add(expenseItemTwo);

        Expense expense = Expense.builder()
                .expenseItems(expenseItemList)
                .build();

        expenseFacade.saveExpense(expense);
        Long id = expense.getId();
        Long expenseItemOneId = expense.getExpenseItems().get(0).getId();
        Long expenseItemTwoId = expense.getExpenseItems().get(1).getId();

        //When
        ExpenseItem updatedExpenseItemOne = ExpenseItem.builder()
                .id(expenseItemOneId)
                .netValue(BigDecimal.valueOf(80))
                .vatRate(VatRate.PTU_B)
                .build();

        ExpenseItem updatedExpenseItemTwo = ExpenseItem.builder()
                .id(expenseItemTwoId)
                .netValue(BigDecimal.valueOf(144))
                .vatRate(VatRate.PTU_A)
                .build();

        List<ExpenseItem> updatedExpenseItemList = new ArrayList<>();
        updatedExpenseItemList.add(updatedExpenseItemOne);
        updatedExpenseItemList.add(updatedExpenseItemTwo);

        Expense updatedExpense = Expense.builder()
                .id(id)
                .expenseItems(updatedExpenseItemList)
                .build();

        expenseFacade.updateExpense(updatedExpense);

        //Then
        assertTrue(expenseRepository.existsById(id));
        assertEquals(BigDecimal.valueOf(224).setScale(2), expenseDbService.getExpense(id).getTotalNetValue());
        assertEquals(BigDecimal.valueOf(263.52).setScale(2), expenseDbService.getExpense(id).getTotalGrossValue());
    }

    @Test
    void saveExpenseItemTest() throws ExpenseItemNotFoundException {
        //Given
        ExpenseItem expenseItem = ExpenseItem.builder()
                .netValue(BigDecimal.valueOf(50))
                .vatRate(VatRate.PTU_A)
                .build();

        //When
        expenseFacade.saveExpenseItem(expenseItem);
        Long id = expenseItem.getId();

        //When
        assertTrue(expenseItemRepository.existsById(id));
        assertEquals(BigDecimal.valueOf(50).setScale(2), expenseItemDbService.getExpenseItem(id).getNetValue());
        assertEquals(BigDecimal.valueOf(61.50).setScale(2), expenseItemDbService.getExpenseItem(id).getGrossValue());
    }

    @Test
    void updateExpenseItemTest() throws ExpenseItemNotFoundException {
        //Given
        ExpenseItem expenseItem = ExpenseItem.builder()
                .netValue(BigDecimal.valueOf(50))
                .vatRate(VatRate.PTU_A)
                .build();

        expenseFacade.saveExpenseItem(expenseItem);
        Long id = expenseItem.getId();

        //When
        ExpenseItem updatedExpenseItem = ExpenseItem.builder()
                .id(id)
                .netValue(BigDecimal.valueOf(65))
                .vatRate(VatRate.PTU_B)
                .build();

        expenseFacade.updateExpenseItem(updatedExpenseItem);

        //Then
        assertTrue(expenseItemRepository.existsById(id));
        assertEquals(BigDecimal.valueOf(65).setScale(2), expenseItemDbService.getExpenseItem(id).getNetValue());
        assertEquals(BigDecimal.valueOf(70.20).setScale(2), expenseItemDbService.getExpenseItem(id).getGrossValue());
    }
}