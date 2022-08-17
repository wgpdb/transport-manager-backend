package com.wgpdb.transportmanager.service.dbservice;

import com.wgpdb.transportmanager.domain.ExpenseItem;
import com.wgpdb.transportmanager.domain.enumerantion.VatRate;
import com.wgpdb.transportmanager.exception.ExpenseItemNotFoundException;
import com.wgpdb.transportmanager.repository.ExpenseItemRepository;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
class ExpenseItemDbServiceTest {

    @Autowired
    private ExpenseItemDbService expenseItemDbService;

    @Autowired
    private ExpenseItemRepository expenseItemRepository;

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
        expenseItemRepository.deleteAll();
    }

    @Test
    void getExpenseItemTest() throws ExpenseItemNotFoundException {
        //Given
        ExpenseItem expenseItem = ExpenseItem.builder()
                .description("test description")
                .netValue(BigDecimal.valueOf(250))
                .vatRate(VatRate.PTU_B)
                .build();

        //When
        expenseItemDbService.saveExpenseItem(expenseItem);
        Long id = expenseItem.getId();

        //Then
        assertTrue(expenseItemRepository.existsById(id));
        assertEquals("test description", expenseItemDbService.getExpenseItem(id).getDescription());
        assertEquals(BigDecimal.valueOf(250).setScale(2), expenseItemDbService.getExpenseItem(id).getNetValue());
        assertEquals(VatRate.PTU_B, expenseItemDbService.getExpenseItem(id).getVatRate());
    }

    @Test
    void getNonExistentExpenseItemThrowsExpenseItemNotFoundExceptionTest() {
        //Given
        ExpenseItem expenseItem = ExpenseItem.builder().build();

        //When
        expenseItemDbService.saveExpenseItem(expenseItem);
        Long id = expenseItem.getId();
        Long nonExistentId = id + 1;

        //Then
        assertTrue(expenseItemRepository.existsById(id));
        assertThrows(ExpenseItemNotFoundException.class, () -> expenseItemDbService.getExpenseItem(nonExistentId));
    }

    @Test
    void getAllExpenseItemsTest() {
        //Given
        ExpenseItem expenseItemOne = ExpenseItem.builder().build();
        ExpenseItem expenseItemTwo = ExpenseItem.builder().build();

        //When
        expenseItemDbService.saveExpenseItem(expenseItemOne);
        expenseItemDbService.saveExpenseItem(expenseItemTwo);

        //Then
        assertEquals(2, expenseItemDbService.getAllExpenseItems().size());
    }

    @Test
    void updateExpenseItemTest() throws ExpenseItemNotFoundException {
        //Given
        ExpenseItem expenseItem = ExpenseItem.builder()
                .description("test description")
                .netValue(BigDecimal.valueOf(250))
                .vatRate(VatRate.PTU_B)
                .build();

        expenseItemDbService.saveExpenseItem(expenseItem);
        Long id = expenseItem.getId();

        //When
        ExpenseItem updatedExpenseItem = ExpenseItem.builder()
                .id(id)
                .description("updated description")
                .netValue(BigDecimal.valueOf(150))
                .vatRate(VatRate.PTU_B)
                .build();

        expenseItemDbService.updateExpenseItem(updatedExpenseItem);

        //Then
        assertEquals("updated description", expenseItemDbService.getExpenseItem(id).getDescription());
        assertEquals(BigDecimal.valueOf(150).setScale(2), expenseItemDbService.getExpenseItem(id).getNetValue());
        assertEquals(VatRate.PTU_B, expenseItemDbService.getExpenseItem(id).getVatRate());
    }

    @Test
    void deleteExpenseItemTest() throws ExpenseItemNotFoundException {
        //Given
        ExpenseItem expenseItem = ExpenseItem.builder()
                .description("test description")
                .netValue(BigDecimal.valueOf(250))
                .vatRate(VatRate.PTU_B)
                .build();

        expenseItemDbService.saveExpenseItem(expenseItem);
        Long id = expenseItem.getId();

        //When
        expenseItemDbService.deleteExpenseItem(id);

        //Then
        assertFalse(expenseItemRepository.existsById(id));
    }
}