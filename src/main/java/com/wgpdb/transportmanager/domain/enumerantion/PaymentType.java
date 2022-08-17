package com.wgpdb.transportmanager.domain.enumerantion;

public enum PaymentType {
    CASH("Cash"),
    CARD("Card"),
    TRANSFER("Transfer"),
    DEDUCTION("Deduction");

    public final String label;

    PaymentType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}