package com.hustcinema.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.hustcinema.backend.model.Room;
import com.hustcinema.backend.service.RoomService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/room")
public class RoomController {
    
    @Autowired
    private RoomService roomService;

    @PostMapping("/")
    public Room newRoom(@RequestBody Room newRoom){
        return roomService.createNewRoom(newRoom);
    }

    @GetMapping("/")
    public List<Room> getAllRoom(){
        return roomService.findAllRoom();
    }

    @DeleteMapping("/{id}")
    public String deleteRoom(@PathVariable String id){
        return roomService.deleteRoomById(id);
    }
    
}
