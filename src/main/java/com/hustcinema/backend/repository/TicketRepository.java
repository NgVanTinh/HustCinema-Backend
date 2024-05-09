package com.hustcinema.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hustcinema.backend.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, String> {
    List<Ticket> findByBillId(String billId);
    
    List<Ticket> findByScheduleIdAndSeatId(String scheduleId, String seatId);

    List<Ticket> findByScheduleId(String scheduleId);

    boolean existsByScheduleIdAndSeatId(String scheduleId, String seatId);

    @Query("SELECT t FROM Ticket t WHERE t.bill.id IN (SELECT b.id FROM Bill b WHERE b.user.id=:userId) ORDER BY t.id DESC")
    List<Ticket> findByUserId(String userId);
}
