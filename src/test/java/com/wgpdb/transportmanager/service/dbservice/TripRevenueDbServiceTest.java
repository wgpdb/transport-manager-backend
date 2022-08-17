package com.wgpdb.transportmanager.service.dbservice;

import com.wgpdb.transportmanager.domain.TripRevenue;
import com.wgpdb.transportmanager.domain.enumerantion.Currency;
import com.wgpdb.transportmanager.exception.TripRevenueNotFoundException;
import com.wgpdb.transportmanager.repository.TripRevenueRepository;
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
public class TripRevenueDbServiceTest {

    @Autowired
    private TripRevenueDbService tripRevenueDbService;

    @Autowired
    private TripRevenueRepository tripRevenueRepository;

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
        tripRevenueRepository.deleteAll();
    }

    @Test
    void getTripRevenueTest() throws TripRevenueNotFoundException {
        //Given
        TripRevenue tripRevenue = TripRevenue.builder()
                .revenueNet(BigDecimal.valueOf(100))
                .commissionNet(BigDecimal.valueOf(10))
                .currency(Currency.PLN)
                .build();

        //When
        tripRevenueDbService.saveTripRevenue(tripRevenue);
        Long id = tripRevenue.getId();

        //Then
        assertTrue(tripRevenueRepository.existsById(id));
        assertEquals(BigDecimal.valueOf(100).setScale(2), tripRevenueDbService.getTripRevenue(id).getRevenueNet());
        assertEquals(BigDecimal.valueOf(10).setScale(2), tripRevenueDbService.getTripRevenue(id).getCommissionNet());
    }

    @Test
    void getNonExistentTripRevenueThrowsTripNotFoundExceptionTest() {
        //Given
        TripRevenue tripRevenue = TripRevenue.builder().build();

        //When
        tripRevenueDbService.saveTripRevenue(tripRevenue);
        Long id = tripRevenue.getId();
        Long nonExistentId = id + 1;

        //Then
        assertTrue(tripRevenueRepository.existsById(id));
        assertThrows(TripRevenueNotFoundException.class, () -> tripRevenueDbService.getTripRevenue(nonExistentId));
    }

    @Test
    void getAllTripRevenueTest() {
        //Given
        TripRevenue tripRevenueOne = TripRevenue.builder().build();
        TripRevenue tripRevenueTwo = TripRevenue.builder().build();

        //When
        tripRevenueDbService.saveTripRevenue(tripRevenueOne);
        tripRevenueDbService.saveTripRevenue(tripRevenueTwo);

        //Then
        assertEquals(2, tripRevenueDbService.getAllTripRevenue().size());
    }

    @Test
    void updateTripRevenueTest() throws TripRevenueNotFoundException {
        //Given
        TripRevenue tripRevenue = TripRevenue.builder()
                .revenueNet(BigDecimal.valueOf(100))
                .commissionNet(BigDecimal.valueOf(10))
                .currency(Currency.EUR)
                .build();

        tripRevenueDbService.saveTripRevenue(tripRevenue);
        Long id = tripRevenue.getId();

        //When
        TripRevenue updatedTripRevenue = TripRevenue.builder()
                .id(id)
                .currency(Currency.USD)
                .build();

        tripRevenueDbService.updateTripRevenue(updatedTripRevenue);

        //Then
        assertEquals(Currency.USD, tripRevenueDbService.getTripRevenue(id).getCurrency());
    }

    @Test
    void deleteTripRevenueTest() throws TripRevenueNotFoundException {
        //Given
        TripRevenue tripRevenue = TripRevenue.builder()
                .revenueNet(BigDecimal.valueOf(100))
                .commissionNet(BigDecimal.valueOf(10))
                .currency(Currency.PLN)
                .build();

        tripRevenueDbService.saveTripRevenue(tripRevenue);
        Long id = tripRevenue.getId();

        //When
        tripRevenueDbService.deleteTripRevenue(id);

        //Then
        assertFalse(tripRevenueRepository.existsById(id));
    }
}