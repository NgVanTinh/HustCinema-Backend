package com.hustcinema.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hustcinema.backend.model.HoldSeat;
import com.hustcinema.backend.model.Schedule;
import com.hustcinema.backend.model.Seat;

public interface HoldSeatRepository extends JpaRepository<HoldSeat, String> {

    Optional<HoldSeat> findBySeatAndSchedule(Seat seat, Schedule schedule);
    
} 
