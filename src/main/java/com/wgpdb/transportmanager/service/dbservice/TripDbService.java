package com.wgpdb.transportmanager.service.dbservice;

import com.wgpdb.transportmanager.domain.Trip;
import com.wgpdb.transportmanager.domain.enumerantion.TripStatus;
import com.wgpdb.transportmanager.exception.TripNotFoundException;
import com.wgpdb.transportmanager.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripDbService {

    private final TripRepository tripRepository;

    public Trip getTrip(final Long id) throws TripNotFoundException {
        return tripRepository.findById(id).orElseThrow(TripNotFoundException::new);
    }

    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    public Page<Trip> getAllTripsPagedSorted(int page, int size, String sortBy) {
        Pageable paging = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return tripRepository.findAll(paging);
    }

    public List<Trip> getByTripStatus(final TripStatus tripStatus) {
        return tripRepository.findByTripStatus(tripStatus);
    }

    public List<Trip> getByTripStatusAndTripDate(final TripStatus tripStatus, final LocalDate tripDate) {
        return tripRepository.findByTripStatusAndTripDate(tripStatus, tripDate);
    }

    public Trip saveTrip(final Trip trip) {
        return tripRepository.save(trip);
    }

    public Trip updateTrip(final Trip trip) throws TripNotFoundException {
        if (tripRepository.existsById(trip.getId())) {
            return tripRepository.save(trip);
        } else {
            throw new TripNotFoundException();
        }
    }

    public void deleteTrip(final Long id) throws TripNotFoundException {
        if (tripRepository.existsById(id)) {
            tripRepository.deleteById(id);
        } else {
            throw new TripNotFoundException();
        }
    }
}