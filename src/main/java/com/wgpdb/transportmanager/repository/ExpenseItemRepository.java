package com.wgpdb.transportmanager.repository;

import com.wgpdb.transportmanager.domain.ExpenseItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseItemRepository extends JpaRepository<ExpenseItem, Long> {

    @Override
    List<ExpenseItem> findAll();

    @Override
    Optional<ExpenseItem> findById(Long id);
}