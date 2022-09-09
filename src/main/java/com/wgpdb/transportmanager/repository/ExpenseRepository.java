package com.wgpdb.transportmanager.repository;

import com.wgpdb.transportmanager.domain.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Override
    List<Expense> findAll();

    @Override
    Page<Expense> findAll(Pageable pageable);

    @Override
    Optional<Expense> findById(Long id);
}