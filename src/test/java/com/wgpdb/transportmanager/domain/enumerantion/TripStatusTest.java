package com.wgpdb.transportmanager.domain.enumerantion;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TripStatusTest {

    @Test
    void getLabel() {
        //Given & When
        TripStatus UPCOMING = TripStatus.UPCOMING;
        TripStatus COMPLETED = TripStatus.COMPLETED;
        TripStatus CANCELLED = TripStatus.CANCELLED;

        //Then
        assertEquals("Upcoming", UPCOMING.getLabel());
        assertEquals("Completed", COMPLETED.getLabel());
        assertEquals("Cancelled", CANCELLED.getLabel());
    }
}