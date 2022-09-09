package com.wgpdb.transportmanager.repository;

import com.wgpdb.transportmanager.domain.Trip;
import com.wgpdb.transportmanager.domain.enumerantion.TripStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    @Override
    List<Trip> findAll();

    @Override
    Page<Trip> findAll(Pageable pageable);

    @Override
    Optional<Trip> findById(Long id);

    List<Trip> findByTripStatus(TripStatus tripStatus);

    List<Trip> findByTripStatusAndTripDate(TripStatus tripStatus, LocalDate tripDate);
}