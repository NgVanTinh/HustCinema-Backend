package com.hustcinema.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Seat {

    @Id

    @GeneratedValue(strategy = GenerationType.UUID)

    private String id;

    @ManyToOne 
    @JoinColumn(name = "roomId")
    private Room room;
    
    private String seatName;
    
    public Room getRoom() {
        return room;
    }
    public void setRoom(Room room) {
        this.room = room;
    }
    public String getSeatName() {
        return seatName;
    }
    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

}