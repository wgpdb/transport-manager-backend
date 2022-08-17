package com.wgpdb.transportmanager.service.dbservice;

import com.wgpdb.transportmanager.domain.Trip;
import com.wgpdb.transportmanager.domain.enumerantion.TripStatus;
import com.wgpdb.transportmanager.exception.TripNotFoundException;
import com.wgpdb.transportmanager.repository.TripRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
public class TripDbServiceTest {

    @Autowired
    private TripDbService tripDbService;

    @Autowired
    private TripRepository tripRepository;

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
        tripRepository.deleteAll();
    }

    @Test
    void getTripTest() throws TripNotFoundException {
        //Given
        Trip trip = Trip.builder()
                .origin("test origin")
                .stopover("test stopover")
                .destination("test destination")
                .tripDate(LocalDate.of(2022, 8, 10))
                .departureTime(LocalTime.of(12, 00))
                .tripStatus(TripStatus.UPCOMING)
                .build();

        //When
        tripDbService.saveTrip(trip);
        Long id = trip.getId();

        //Then
        assertTrue(tripRepository.existsById(id));
        assertEquals("test origin", tripDbService.getTrip(id).getOrigin());
        assertEquals("test stopover", tripDbService.getTrip(id).getStopover());
        assertEquals("test destination", tripDbService.getTrip(id).getDestination());
        assertEquals(LocalDate.of(2022, 8, 10),
                tripDbService.getTrip(id).getTripDate());
        assertEquals(LocalTime.of(12, 00), tripDbService.getTrip(id).getDepartureTime());
        assertEquals(TripStatus.UPCOMING, tripDbService.getTrip(id).getTripStatus());
    }

    @Test
    void getNonExistentTripThrowsTripNotFoundExceptionTest() {
        //Given
        Trip trip = Trip.builder().build();

        //When
        tripDbService.saveTrip(trip);
        Long id = trip.getId();
        Long nonExistentId = id + 1;

        //Then
        assertTrue(tripRepository.existsById(id));
        assertThrows(TripNotFoundException.class, () -> tripDbService.getTrip(nonExistentId));
    }

    @Test
    void getAllTripsTest() {
        //Given
        Trip tripOne = Trip.builder().build();
        Trip tripTwo = Trip.builder().build();

        //When
        tripDbService.saveTrip(tripOne);
        tripDbService.saveTrip(tripTwo);

        //Then
        assertEquals(2, tripDbService.getAllTrips().size());
    }

    @Test
    void getByTripStatusTest() {
        //Given
        Trip tripOne = Trip.builder()
                .tripStatus(TripStatus.UPCOMING)
                .build();

        Trip tripTwo = Trip.builder()
                .tripStatus(TripStatus.UPCOMING)
                .build();

        Trip tripThree = Trip.builder()
                .tripStatus(TripStatus.COMPLETED)
                .build();

        //When
        tripDbService.saveTrip(tripOne);
        tripDbService.saveTrip(tripTwo);
        tripDbService.saveTrip(tripThree);

        //Then
        assertEquals(2, tripDbService.getByTripStatus(TripStatus.UPCOMING).size());
        assertEquals(1, tripDbService.getByTripStatus(TripStatus.COMPLETED).size());
    }

    @Test
    void getByTripStatusAndTripDateTest() {
        //Given
        Trip tripOne = Trip.builder()
                .tripDate(LocalDate.of(2022, 8, 14))
                .tripStatus(TripStatus.UPCOMING)
                .build();

        Trip tripTwo = Trip.builder()
                .tripDate(LocalDate.of(2022, 8, 15))
                .tripStatus(TripStatus.UPCOMING)
                .build();

        Trip tripThree = Trip.builder()
                .tripDate(LocalDate.of(2022, 8, 15))
                .tripStatus(TripStatus.UPCOMING)
                .build();

        Trip tripFour = Trip.builder()
                .tripDate(LocalDate.of(2022, 8, 16))
                .tripStatus(TripStatus.UPCOMING)
                .build();

        //When
        tripDbService.saveTrip(tripOne);
        tripDbService.saveTrip(tripTwo);
        tripDbService.saveTrip(tripThree);
        tripDbService.saveTrip(tripFour);

        //Then
        assertEquals(2, tripDbService.getByTripStatusAndTripDate(TripStatus.UPCOMING,
                LocalDate.of(2022, 8, 15)).size());
    }

    @Test
    void updateTripTest() throws TripNotFoundException {
        //Given
        Trip trip = Trip.builder()
                .origin("test origin")
                .stopover("test stopover")
                .destination("test destination")
                .tripDate(LocalDate.of(2022, 8, 10))
                .departureTime(LocalTime.of(12, 00))
                .tripStatus(TripStatus.UPCOMING)
                .build();

        tripDbService.saveTrip(trip);
        Long id = trip.getId();

        //When
        Trip updatedTrip = Trip.builder()
                .id(id)
                .tripStatus(TripStatus.COMPLETED)
                .build();

        tripDbService.updateTrip(updatedTrip);

        //Then
        assertEquals(TripStatus.COMPLETED, tripDbService.getTrip(id).getTripStatus());
    }

    @Test
    void deleteTripTest() throws TripNotFoundException {
        //Given
        Trip trip = Trip.builder()
                .origin("test origin")
                .stopover("test stopover")
                .destination("test destination")
                .tripDate(LocalDate.of(2022, 8, 10))
                .departureTime(LocalTime.of(12, 00))
                .tripStatus(TripStatus.UPCOMING)
                .build();

        tripDbService.saveTrip(trip);
        Long id = trip.getId();

        //When
        tripDbService.deleteTrip(id);

        //Then
        assertFalse(tripRepository.existsById(id));
    }
}