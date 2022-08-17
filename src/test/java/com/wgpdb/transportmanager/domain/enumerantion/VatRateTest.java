package com.wgpdb.transportmanager.domain.enumerantion;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VatRateTest {

    @Test
    void getValueTest() {
        //Given & When
        VatRate PTU_A = VatRate.PTU_A;
        VatRate PTU_B = VatRate.PTU_B;
        VatRate PTU_C = VatRate.PTU_C;
        VatRate PTU_D = VatRate.PTU_D;

        //Then
        assertEquals(23, PTU_A.getValue());
        assertEquals(8, PTU_B.getValue());
        assertEquals(5, PTU_C.getValue());
        assertEquals(0, PTU_D.getValue());
    }

    @Test
    void valueOfTest() {
        //Given & When
        VatRate PTU_A = VatRate.PTU_A;
        VatRate PTU_B = VatRate.PTU_B;
        VatRate PTU_C = VatRate.PTU_C;
        VatRate PTU_D = VatRate.PTU_D;

        //Then
        assertEquals(23, PTU_A.value);
        assertEquals(8, PTU_B.value);
        assertEquals(5, PTU_C.value);
        assertEquals(0, PTU_D.value);
    }
}