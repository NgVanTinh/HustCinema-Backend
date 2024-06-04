package com.hustcinema.backend.exception;

import java.time.LocalDate;
import java.time.LocalTime;

public class ScheduleExistException extends RuntimeException {
    
    public ScheduleExistException(LocalDate date, LocalTime showTime, String roomName){
        super("Đã tồn tại lịch chiếu ngày " + date + " lúc " + showTime + " tại phòng chiếu " + roomName);
    }
}
