package com.wgpdb.transportmanager.domain.enumerantion;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CurrencyTest {

    @Test
    void getLabelTest() {
        //Given & When
        Currency PLN = Currency.PLN;
        Currency EUR = Currency.EUR;
        Currency USD = Currency.USD;

        //Then
        assertEquals("PLN", PLN.getLabel());
        assertEquals("EUR", EUR.getLabel());
        assertEquals("USD", USD.getLabel());
    }
}