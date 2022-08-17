package com.wgpdb.transportmanager.repository;

import com.wgpdb.transportmanager.domain.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {

    @Override
    List<WeatherData> findAll();

    @Override
    Optional<WeatherData> findById(Long id);
}