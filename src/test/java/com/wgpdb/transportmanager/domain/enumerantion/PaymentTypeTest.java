package com.wgpdb.transportmanager.domain.enumerantion;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTypeTest {

    @Test
    void getLabel() {
        //Given & When
        PaymentType CASH = PaymentType.CASH;
        PaymentType CARD = PaymentType.CARD;
        PaymentType TRANSFER = PaymentType.TRANSFER;
        PaymentType DEDUCTION = PaymentType.DEDUCTION;

        //Then
        assertEquals("Cash", CASH.getLabel());
        assertEquals("Card", CARD.getLabel());
        assertEquals("Transfer", TRANSFER.getLabel());
        assertEquals("Deduction", DEDUCTION.getLabel());
    }
}