package com.wgpdb.transportmanager.controller;

import com.wgpdb.transportmanager.domain.TripRevenue;
import com.wgpdb.transportmanager.domain.dto.TripRevenueDto;
import com.wgpdb.transportmanager.exception.TripRevenueNotFoundException;
import com.wgpdb.transportmanager.mapper.TripRevenueMapper;
import com.wgpdb.transportmanager.service.dbservice.TripRevenueDbService;
import com.wgpdb.transportmanager.service.triprevenue.TripRevenueCalcService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/trips_revenue")
public class TripRevenueController {

    private final TripRevenueDbService tripRevenueDbService;
    private final TripRevenueMapper tripRevenueMapper;
    private final TripRevenueCalcService tripRevenueCalcService;

    @GetMapping(value = "{id}")
    public ResponseEntity<TripRevenueDto> getTripFinancialDetails(@PathVariable Long id)
            throws TripRevenueNotFoundException {
        return ResponseEntity.ok(tripRevenueMapper
                .INSTANCE.mapToTripRevenueDto(tripRevenueDbService.getTripRevenue(id)));
    }

    @GetMapping
    public ResponseEntity<List<TripRevenueDto>> getAllTripRevenue() {
        List<TripRevenue> tripRevenueList = tripRevenueDbService.getAllTripRevenue();
        return ResponseEntity.ok(tripRevenueMapper.INSTANCE.mapToTripRevenueDtoList(tripRevenueList));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addTripRevenue(@RequestBody TripRevenueDto tripRevenueDto) {
        TripRevenue tripRevenue = tripRevenueMapper.INSTANCE.mapToTripRevenue(tripRevenueDto);
        tripRevenueCalcService.exchangeRateRetriever(tripRevenue);
        tripRevenueCalcService.foreignCurrencyConverter(tripRevenue);
        tripRevenueDbService.saveTripRevenue(tripRevenue);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TripRevenueDto> updateTripRevenue(@RequestBody TripRevenueDto tripRevenueDto)
            throws TripRevenueNotFoundException {
        TripRevenue tripRevenue = tripRevenueMapper.INSTANCE.mapToTripRevenue(tripRevenueDto);
        tripRevenueCalcService.exchangeRateRetriever(tripRevenue);
        tripRevenueCalcService.foreignCurrencyConverter(tripRevenue);
        TripRevenue savedTripRevenue = tripRevenueDbService.updateTripRevenue(tripRevenue);
        return ResponseEntity.ok(tripRevenueMapper.INSTANCE.mapToTripRevenueDto(savedTripRevenue));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteTripRevenue(@PathVariable Long id) throws TripRevenueNotFoundException {
        tripRevenueDbService.deleteTripRevenue(id);
        return ResponseEntity.ok().build();
    }
}