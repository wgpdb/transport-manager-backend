package com.wgpdb.transportmanager.domain.enumerantion;

public enum TripStatus {
    UPCOMING("Upcoming"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");

    public final String label;

    TripStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}