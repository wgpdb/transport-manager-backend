package com.wgpdb.transportmanager.repository;

import com.wgpdb.transportmanager.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {

    @Override
    List<Vendor> findAll();

    @Override
    Optional<Vendor> findById(Long id);
}