package com.wgpdb.transportmanager.domain.enumerantion;

public enum Currency {
    PLN("PLN"),
    EUR("EUR"),
    USD("USD");

    public final String label;

    Currency(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}