package com.wgpdb.transportmanager.controller;

import com.wgpdb.transportmanager.domain.Trip;
import com.wgpdb.transportmanager.domain.enumerantion.TripStatus;
import com.wgpdb.transportmanager.domain.dto.TripDto;
import com.wgpdb.transportmanager.exception.TripNotFoundException;
import com.wgpdb.transportmanager.mapper.TripMapper;
import com.wgpdb.transportmanager.service.dbservice.TripDbService;
import com.wgpdb.transportmanager.service.triprevenue.TripRevenueCalcService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/trips")
public class TripController {

    private final TripDbService tripDbService;
    private final TripMapper tripMapper;
    private final TripRevenueCalcService tripRevenueCalcService;

    @GetMapping(value = "{id}")
    public ResponseEntity<TripDto> getTrip(@PathVariable Long id) throws TripNotFoundException {
        return ResponseEntity.ok(tripMapper.INSTANCE.mapToTripDto(tripDbService.getTrip(id)));
    }

    @GetMapping
    public ResponseEntity<List<TripDto>> getAllTrips() {
        List<Trip> trips = tripDbService.getAllTrips();
        return ResponseEntity.ok(tripMapper.INSTANCE.mapToTripDtoList(trips));
    }

    @GetMapping(value = "/status")
    public ResponseEntity<List<TripDto>> getByTripStatus(@RequestParam("status") TripStatus tripStatus) {
        List<Trip> trips = tripDbService.getByTripStatus(tripStatus);
        return ResponseEntity.ok(tripMapper.INSTANCE.mapToTripDtoList(trips));
    }

    @GetMapping(value = "/status_date")
    public ResponseEntity<List<TripDto>> getByTripStatusAndTripDate
            (@RequestParam("status") TripStatus tripStatus, @RequestParam("date")
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate tripDate) {
        List<Trip> trips = tripDbService.getByTripStatusAndTripDate(tripStatus, tripDate);
        return ResponseEntity.ok(tripMapper.INSTANCE.mapToTripDtoList(trips));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addTrip(@RequestBody TripDto tripDto) {
        Trip trip = tripMapper.INSTANCE.mapToTrip(tripDto);
        tripRevenueCalcService.exchangeRateRetrieverTrip(trip);
        tripRevenueCalcService.foreignCurrencyConverterTrip(trip);
        tripDbService.saveTrip(trip);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TripDto> updateTrip(@RequestBody TripDto tripDto) throws TripNotFoundException {
        Trip trip = tripMapper.INSTANCE.mapToTrip(tripDto);
        tripRevenueCalcService.exchangeRateRetrieverTrip(trip);
        tripRevenueCalcService.foreignCurrencyConverterTrip(trip);
        Trip savedTrip = tripDbService.updateTrip(trip);
        return ResponseEntity.ok(tripMapper.INSTANCE.mapToTripDto(savedTrip));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable Long id) throws TripNotFoundException {
        tripDbService.deleteTrip(id);
        return ResponseEntity.ok().build();
    }
}