package com.hustcinema.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hustcinema.backend.model.Room;
import com.hustcinema.backend.model.Seat;
import java.util.List;


public interface SeatRepository extends JpaRepository<Seat, String> {
    
    List<Seat> findByRoomId(String roomId);
    Boolean existsByRoomAndSeatName(Room room, String seatName);


}
