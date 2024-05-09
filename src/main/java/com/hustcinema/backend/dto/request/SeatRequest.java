package com.hustcinema.backend.dto.request;

public class SeatRequest {
    
    private String seatName;
    private String roomId;
    
    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }
    
}
