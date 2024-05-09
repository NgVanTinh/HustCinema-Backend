package com.hustcinema.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hustcinema.backend.model.Bill;

public interface BillRepository extends JpaRepository<Bill, String> {
    List<Bill> findByUserId(String userId);
}
