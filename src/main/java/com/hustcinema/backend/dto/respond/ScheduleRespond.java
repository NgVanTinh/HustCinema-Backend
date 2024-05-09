package com.hustcinema.backend.dto.respond;

import java.time.LocalDate;
import java.time.LocalTime;

public class ScheduleRespond {
    
    private String id;
    private String movieName;
    private String roomName;
    private LocalDate date;
    private LocalTime showTime;
    private Long price;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getMovieName() {
        return movieName;
    }
    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
    public String getRoomName() {
        return roomName;
    }
    public void setRoomName(String roomName) {
        this.roomName = roomName;
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
    public Long getPrice() {
        return price;
    }
    public void setPrice(Long price) {
        this.price = price;
    }
    
}
