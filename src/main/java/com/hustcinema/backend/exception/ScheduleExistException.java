package com.hustcinema.backend.exception;

import java.time.LocalDate;
import java.time.LocalTime;

public class ScheduleExistException extends RuntimeException {
    
    public ScheduleExistException(LocalDate date, LocalTime showTime, String roomName){
        super("Showtime schedule on " + date + " at " + showTime + " in room " + roomName + " already exists!");
    }
}
