package com.wgpdb.transportmanager.service.exchangerate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ExchangeRateServiceTest {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Test
    void dateValidatorTest() {
        //Given
        LocalDate friday = LocalDate.of(2022, 8, 5);
        LocalDate saturday = LocalDate.of(2022, 8, 6);
        LocalDate sunday = LocalDate.of(2022, 8, 7);
        LocalDate monday = LocalDate.of(2022, 8, 8);

        //When
        LocalDate shouldBeThursday = exchangeRateService.dateValidator(friday);
        LocalDate shouldBeFridayOne = exchangeRateService.dateValidator(saturday);
        LocalDate shouldBeFridayTwo = exchangeRateService.dateValidator(sunday);
        LocalDate shouldBeFridayThree = exchangeRateService.dateValidator(monday);

        //When
        assertEquals(LocalDate.of(2022, 8, 4), shouldBeThursday);
        assertEquals(LocalDate.of(2022, 8, 5), shouldBeFridayOne);
        assertEquals(LocalDate.of(2022, 8, 5), shouldBeFridayTwo);
        assertEquals(LocalDate.of(2022, 8, 5), shouldBeFridayThree);
    }
}