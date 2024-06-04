package com.hustcinema.backend.dto.request;

public class holdSeatRequest {
    
    private String scheduleId;
    private String seatId;

    public String getScheduleId() {
        return scheduleId;
    }
    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }
    public String getSeatId() {
        return seatId;
    }
    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }
    
}
