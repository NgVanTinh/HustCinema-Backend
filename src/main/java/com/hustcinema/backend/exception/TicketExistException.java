package com.hustcinema.backend.exception;

import com.hustcinema.backend.dto.respond.ScheduleRespond;

public class TicketExistException extends RuntimeException {
    public TicketExistException(ScheduleRespond scheduleRespond, String seatName) {
        super("the seat " + seatName + " for the show time: " + scheduleRespond.getShowTime() 
                + " at room " + scheduleRespond.getRoomName() + " has been reserved!");
    }    
}
