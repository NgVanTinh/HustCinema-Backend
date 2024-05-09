package com.hustcinema.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hustcinema.backend.model.Room;

public interface RoomRepository extends JpaRepository<Room, String> {
    
}
