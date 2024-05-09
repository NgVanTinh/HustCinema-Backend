package com.hustcinema.backend.exception;

import com.hustcinema.backend.model.Room;

public class SeatExistException extends RuntimeException {
    public SeatExistException(Room room, String seatName) {
        
        super("Seat " + seatName + " already exists in room  " + room.getRoomName());
    }
}
