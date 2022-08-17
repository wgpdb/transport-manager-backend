package com.wgpdb.transportmanager.service.dbservice;

import com.wgpdb.transportmanager.domain.Expense;
import com.wgpdb.transportmanager.domain.enumerantion.Currency;
import com.wgpdb.transportmanager.domain.enumerantion.PaymentType;
import com.wgpdb.transportmanager.exception.ExpenseNotFoundException;
import com.wgpdb.transportmanager.repository.ExpenseRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
class ExpenseDbServiceTest {

    @Autowired
    private ExpenseDbService expenseDbService;

    @Autowired
    private ExpenseRepository expenseRepository;

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
    }

    @Test
    void getExpenseTest() throws ExpenseNotFoundException {
        //Given
        Expense expense = Expense.builder()
                .expenseDate(LocalDate.now())
                .currency(Currency.PLN)
                .paymentType(PaymentType.CARD)
                .build();

        //When
        expenseDbService.saveExpense(expense);
        Long id = expense.getId();

        //Then
        assertTrue(expenseRepository.existsById(id));
        assertEquals(LocalDate.now(), expenseDbService.getExpense(id).getExpenseDate());
        assertEquals(Currency.PLN, expenseDbService.getExpense(id).getCurrency());
        assertEquals(PaymentType.CARD, expenseDbService.getExpense(id).getPaymentType());
    }

    @Test
    void getNonExistentExpenseThrowsExpenseNotFoundExceptionTest() {
        //Given
        Expense expense = Expense.builder().build();

        //When
        expenseDbService.saveExpense(expense);
        Long id = expense.getId();
        Long nonExistentId = id + 1;

        //Then
        assertTrue(expenseRepository.existsById(id));
        assertThrows(ExpenseNotFoundException.class, () -> expenseDbService.getExpense(nonExistentId));
    }

    @Test
    void getAllExpensesTest() {
        //Given
        Expense expenseOne = Expense.builder().build();
        Expense expenseTwo = Expense.builder().build();

        //When
        expenseDbService.saveExpense(expenseOne);
        expenseDbService.saveExpense(expenseTwo);

        //Then
        assertEquals(2, expenseDbService.getAllExpenses().size());
    }

    @Test
    void updateExpenseTest() throws ExpenseNotFoundException {
        //Given
        Expense expense = Expense.builder()
                .expenseDate(LocalDate.now())
                .currency(Currency.EUR)
                .paymentType(PaymentType.TRANSFER)
                .build();

        expenseDbService.saveExpense(expense);
        Long id = expense.getId();

        //When
        Expense updatedExpense = Expense.builder()
                .id(id)
                .currency(Currency.PLN)
                .paymentType(PaymentType.CARD)
                .build();

        expenseDbService.updateExpense(updatedExpense);

        //Then
        assertEquals(Currency.PLN, expenseDbService.getExpense(id).getCurrency());
        assertEquals(PaymentType.CARD, expenseDbService.getExpense(id).getPaymentType());
    }

    @Test
    void deleteExpenseTest() throws ExpenseNotFoundException {
        //Given
        Expense expense = Expense.builder()
                .expenseDate(LocalDate.now())
                .currency(Currency.EUR)
                .paymentType(PaymentType.CASH)
                .build();

        expenseDbService.saveExpense(expense);
        Long id = expense.getId();

        //When
        expenseDbService.deleteExpense(id);

        //Then
        assertFalse(expenseRepository.existsById(id));
    }
}