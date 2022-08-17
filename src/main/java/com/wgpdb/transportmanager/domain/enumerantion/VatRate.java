package com.wgpdb.transportmanager.domain.enumerantion;

public enum VatRate {

    PTU_A(23),
    PTU_B(8),
    PTU_C(5),
    PTU_D(0);

    public final int value;

    VatRate(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}