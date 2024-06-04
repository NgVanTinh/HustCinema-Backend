package com.hustcinema.backend.dto.respond;

public class SeatRespond {
    private String seatId;
    private String seatName;
    private String roomId;
    private boolean isOccupied;
    private boolean isHold = false;
    

    public boolean isHold() {
        return isHold;
    }

    public void setHold(boolean isHold) {
        this.isHold = isHold;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public void setIsOccupied(boolean b) {
        this.isOccupied = b;
    }

    public boolean getIsOccupied() {
        return isOccupied;
    }
}
