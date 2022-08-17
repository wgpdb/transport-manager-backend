package com.wgpdb.transportmanager.repository;

import com.wgpdb.transportmanager.domain.TripRevenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripRevenueRepository extends JpaRepository<TripRevenue, Long> {

    @Override
    List<TripRevenue> findAll();

    @Override
    Optional<TripRevenue> findById(Long id);
}