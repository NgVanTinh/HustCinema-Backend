package com.hustcinema.backend.dto.respond;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TicketRespond {
    
    private String ticketId;
    private String seatName;
    private LocalDate date;
    private LocalTime showTime;
    private String roomName;
    private LocalDateTime createdTime;
    
    public String getTicketId() {
        return ticketId;
    }
    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }
    public String getSeatName() {
        return seatName;
    }
    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public LocalTime getShowTime() {
        return showTime;
    }
    public void setShowTime(LocalTime showTime) {
        this.showTime = showTime;
    }
    public String getRoomName() {
        return roomName;
    }
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
    public LocalDateTime getCreatedTime() {
        return createdTime;
    }
    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
    
}
