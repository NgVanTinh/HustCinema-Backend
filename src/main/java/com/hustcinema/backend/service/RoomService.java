package com.hustcinema.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.hustcinema.backend.model.Room;
import com.hustcinema.backend.model.Schedule;
import com.hustcinema.backend.repository.RoomRepository;
import com.hustcinema.backend.repository.ScheduleRepository;

@Service
public class RoomService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private RoomRepository roomRepository;
    

    // @PreAuthorize("hasRole('ADMIN')")
    public Room createNewRoom(Room newRoom){
        return roomRepository.save(newRoom);
    }

    // @PreAuthorize("hasRole('ADMIN')")
    public List<Room> findAllRoom(){
        return roomRepository.findAll();
    }

    // @PreAuthorize("hasRole('ADMIN')")
    public String deleteRoomById(String id) {
        if (!roomRepository.existsById(id)) {
            throw new RuntimeException("Room with id = " + id + " not found!");
        }
        List<Schedule> schedules = scheduleRepository.findByRoomId(id);
        for (Schedule schedule : schedules) {
            scheduleRepository.deleteById(schedule.getId());
        }
        roomRepository.deleteById(id);
        return "Room with id = " + id + " has been deleted successfully! Schedule in this room has been deleted";
    }
}
