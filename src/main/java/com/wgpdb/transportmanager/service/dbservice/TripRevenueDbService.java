package com.wgpdb.transportmanager.service.dbservice;

import com.wgpdb.transportmanager.domain.TripRevenue;
import com.wgpdb.transportmanager.exception.TripRevenueNotFoundException;
import com.wgpdb.transportmanager.repository.TripRevenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripRevenueDbService {

    @Autowired
    private TripRevenueRepository tripRevenueRepository;

    public TripRevenue getTripRevenue(final Long id) throws TripRevenueNotFoundException {
        return tripRevenueRepository.findById(id).orElseThrow(TripRevenueNotFoundException::new);
    }

    public List<TripRevenue> getAllTripRevenue() {
        return tripRevenueRepository.findAll();
    }

    public TripRevenue saveTripRevenue(final TripRevenue tripRevenue) {
        return tripRevenueRepository.save(tripRevenue);
    }

    public TripRevenue updateTripRevenue(final TripRevenue tripRevenue)
            throws TripRevenueNotFoundException {
        if (tripRevenueRepository.existsById(tripRevenue.getId())) {
            return tripRevenueRepository.save(tripRevenue);
        } else {
            throw new TripRevenueNotFoundException();
        }
    }

    public void deleteTripRevenue(final Long id) throws TripRevenueNotFoundException {
        if (tripRevenueRepository.existsById(id)) {
            tripRevenueRepository.deleteById(id);
        } else {
            throw new TripRevenueNotFoundException();
        }
    }
}